package com.zzq.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AccessFlag;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

public class EditorMethod {

    public static void main(String[] args)  throws Exception{
        ClassPool classPool = ClassPool.getDefault();

        CtClass ctClass = classPool.makeClass(new FileInputStream(new File("D:\\githubWorkSpace\\upload\\spring-boot-dfs\\target\\classes\\com\\zzq\\util\\StringUtils.class")));

        CtMethod declaredMethod = ctClass.getDeclaredMethod("isNotBlank", new CtClass[]{classPool.get("java.lang.String")});

//        declaredMethod.insertBefore("System.out.println(\"收到消息：===\");");
//        declaredMethod.setModifiers(AccessFlag.PUBLIC);
        declaredMethod.setBody("{System.out.println(\"收到消息：\"+ $1);return $1 != null && !\"\".equals($1);}");
//        declaredMethod.setBody("{System.out.println(\"收到消息：===\");}");

        // 修改class之后再输出修改后的class文件
        ctClass.writeFile(EditorMethod.class.getResource("/").getPath());

        if(ctClass.isFrozen()){
            ctClass.defrost();
        }

        Class<?> aClass = Class.forName(ctClass.getName());
        Method isNotBlankMethod = aClass.getMethod("isNotBlank", String.class);

        System.out.println(isNotBlankMethod.invoke(aClass.newInstance(), "lcm"));

    }

}
