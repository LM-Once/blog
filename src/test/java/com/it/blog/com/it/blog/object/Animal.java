package com.it.blog.com.it.blog.object;

public abstract class Animal {

    public static void main(String[] args) {
        Animal animal = new Cat();
        animal.eat();
    }

    public abstract void eat();

}
