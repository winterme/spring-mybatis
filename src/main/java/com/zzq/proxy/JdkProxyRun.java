package com.zzq.proxy;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class JdkProxyRun {
    public static void main(String[] args) throws IOException {
        Animal cat = new Cat();

        Animal proxy = getProxy(cat);

        proxy.sayhi("喵哈哈哈");

        byte[] bytes = ProxyGenerator.generateProxyClass("Cat", new Class[]{Animal.class});
        FileOutputStream outputStream = new FileOutputStream(new File("D://"+ proxy.getClass().getName() +".class"));
        outputStream.write(bytes);
        outputStream.flush();

    }

    public static <T> T getProxy(Animal animal){
        Class[] classes = animal.getClass().getInterfaces();

        return (T)Proxy.newProxyInstance(Animal.class.getClassLoader() , classes , new AnimalInvocation(animal));
    }

    public static void proxyMethod1(){
        //代理的真实对象
        Animal realAnimal = new Cat();

        /**
         * InvocationHandlerImpl 实现了 InvocationHandler 接口，并能实现方法调用从代理类到委托类的分派转发
         * 其内部通常包含指向委托类实例的引用，用于真正执行分派转发过来的方法调用.
         * 即：要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法
         */
        InvocationHandler handler = new AnimalInvocation(realAnimal);

        ClassLoader loader = realAnimal.getClass().getClassLoader();
        Class[] interfaces = realAnimal.getClass().getInterfaces();
        /**
         * 该方法用于为指定类装载器、一组接口及调用处理器生成动态代理类实例
         */
        Animal animal = (Animal) Proxy.newProxyInstance(loader, interfaces, handler);

        System.out.println("动态代理对象的类型："+animal.getClass().getName());

        animal.sayhi("xxx");
    }

}
