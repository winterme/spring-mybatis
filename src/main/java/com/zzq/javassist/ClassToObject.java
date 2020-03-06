package com.zzq.javassist;

import javassist.ClassPool;
import javassist.CtClass;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

public class ClassToObject {
    public static void main(String[] args) throws Exception {
        ClassPool classPool = ClassPool.getDefault();

        CtClass ctClass = classPool.makeClass(new FileInputStream(new File("D:\\githubWorkSpace\\upload\\spring-boot-dfs\\target\\classes\\com\\zzq\\util\\StringUtils.class")));


        ctClass.writeFile(ProxyRun.class.getResource("/").getPath());
        Class<?> stringUtils = Class.forName(ctClass.getName());

        for (Method method : stringUtils.getMethods()) {
            System.out.println(method);
        }

        Method isNotBlank = stringUtils.getMethod("isNotBlank", String.class);

        System.out.println(isNotBlank.invoke(stringUtils.newInstance(), "xxx"));
        System.out.println(isNotBlank.invoke(stringUtils.newInstance(), ""));


    }
}
