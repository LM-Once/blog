package com.it.blog.entity;

import java.util.ArrayList;
import java.util.List;

public class User {

    private Integer id;

    private String userName;

    // 0.女          1.男
    private Integer age;

    private Integer sex;

    public Integer getId() {
        return id;
    }


    public User() {
        super();
    }

    public User(String userName, Integer age, Integer sex) {
        super();
        this.userName = userName;
        this.age = age;
        this.sex = sex;
    }

    public User(Integer id, String userName, Integer age, Integer sex) {
        super();
        this.id = id;
        this.userName = userName;
        this.age = age;
        this.sex = sex;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Integer getAge() {
        return age;
    }


    public void setAge(Integer age) {
        this.age = age;
    }


    public Integer getSex() {
        return sex;
    }


    public void setSex(Integer sex) {
        this.sex = sex;
    }


    public static void main(String[] args) {
        User user1 = new User("dfd", 22, 1);
        List<User> list = new ArrayList<>();
        list.add(user1);
        User[] userArray = list.toArray(new User[list.size()]);
        System.out.println(userArray.length);
    }

}
