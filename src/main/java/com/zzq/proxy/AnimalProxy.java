package com.zzq.proxy;

public class AnimalProxy implements Animal {

    private Animal animal;

    public AnimalProxy(Animal animal) {
        this.animal = animal;
    }

    @Override
    public void sayhi(String msg) {
        this.animal.sayhi(msg);
    }

    @Override
    public void eatSomething() {
        this.animal.eatSomething();
    }
}
