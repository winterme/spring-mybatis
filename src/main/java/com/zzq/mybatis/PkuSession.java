package com.zzq.mybatis;

import java.lang.reflect.Proxy;

public class PkuSession {

    public <T> T getMapper(Class<T> clz){
        Class [] classes = new Class[]{clz};
        Object proxyInstance = Proxy.newProxyInstance(PkuSession.class.getClassLoader(), classes, new PkuInvocationHandle());

        return (T)proxyInstance;
    }

}
