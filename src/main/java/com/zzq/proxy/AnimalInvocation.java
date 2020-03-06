package com.zzq.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AnimalInvocation implements InvocationHandler {

    private Animal animal;

    public AnimalInvocation(Animal animal) {
        this.animal = animal;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("调用之前.....");
        Object result = method.invoke(animal, args);
        System.out.println("调用之后.....");
        return result;
    }
}
