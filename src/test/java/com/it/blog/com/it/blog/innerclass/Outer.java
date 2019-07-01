package com.it.blog.com.it.blog.innerclass;

public class Outer {

    public void getInner(Inner inner){
        System.out.println("这很奇怪");
    }

    public static void main(String[] args) {

        Outer out = new Outer();
        out.getInner(new Inner() {
            @Override
            public String say() {
                System.out.println("111111");
                return null;
            }
        });

    }
}
