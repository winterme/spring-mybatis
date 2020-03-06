package com.zzq.proxy;

public interface Animal {

    void sayhi(String msg);

    void eatSomething();

}

class Dog implements Animal {

    @Override
    public void sayhi(String msg) {
        System.out.println("小狗~汪汪汪 ====> " + msg);
    }

    @Override
    public void eatSomething() {
        System.out.println("小狗吃骨头...");
    }
}

class Cat implements Animal {

    @Override
    public void sayhi(String msg) {
        System.out.println("小猫~喵喵喵 ===> " + msg);
    }

    @Override
    public void eatSomething() {
        System.out.println("小猫爱吃🐟....");
    }
}