package com.zzq.mybatis;

import com.zzq.mybatis.annotation.Mapper;
import com.zzq.mybatis.annotation.MapperScanner;
import javassist.CannotCompileException;
import javassist.ClassPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Component
public class AutoConfigurationImport implements ImportBeanDefinitionRegistrar {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private void getAllMapper(Map<String, Class> mapper, File file, ClassPool classPool) throws IOException, CannotCompileException {
        for (File listFile : file.listFiles()) {
            if (listFile.isFile()) {
                String fileName = listFile.getName().substring(0, 1).toLowerCase() + listFile.getName().substring(1);
                fileName = fileName.substring(0,fileName.indexOf((char)46));
                mapper.put(fileName, classPool.makeClass(new FileInputStream(listFile)).toClass());
            }
            if (listFile.isDirectory()) {
                getAllMapper(mapper, listFile, classPool);
            }
        }
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 解析 MapperScanner 注解里面需要扫描的包路径
        String package_path = null;
        for (String annotationType : importingClassMetadata.getAnnotationTypes()) {
            if(annotationType.equals(MapperScanner.class.getName())){
                package_path  = (String) importingClassMetadata.getAnnotationAttributes(annotationType).get("value");
            }
        }

        File file = new File(this.getClass().getResource("/").getFile() + package_path.replace('.','/'));
        Map<String, Class> classes = new HashMap<>();
        Map<String, Class> rootBeanClass = new HashMap<>();
        ClassPool classPool = ClassPool.getDefault();

        // 获取需要扫描的包下面的所有类
        try {
            getAllMapper(classes, file, classPool);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 开始解析扫描包下的 class对象，获取所有有 Mapper 注解覆盖的类
        classes.keySet().forEach(beanName -> {
            if (classes.get(beanName).isAnnotationPresent(Mapper.class)) {
                rootBeanClass.put(beanName, PkuSession.getPkuSession().getMapper(classes.get(beanName)).getClass());
            }
        });

        PkuInvocationHandle pkuInvocationHandle = new PkuInvocationHandle();
        // 开始注入
        rootBeanClass.keySet().forEach(beanName -> {
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();

            // set beanClass => 此处填写的是jdl动态代理之后返回的class对象
            rootBeanDefinition.setBeanClass(rootBeanClass.get(beanName));
            rootBeanDefinition.setScope(SCOPE_SINGLETON);

            // 添加构造方法的参数，因为jdk动态代理生成的类需要 InvocationHandle 这个参数
            ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
            constructorArgumentValues.addGenericArgumentValue(pkuInvocationHandle);

            rootBeanDefinition.setConstructorArgumentValues(
                    constructorArgumentValues
            );

            registry.registerBeanDefinition(beanName, rootBeanDefinition);
            log.info(String.format("%s bean is autowired", beanName) );
        });

    }
}
