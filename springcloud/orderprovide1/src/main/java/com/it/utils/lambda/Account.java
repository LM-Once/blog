package com.it.utils.lambda;

import java.io.Serializable;

public class Account implements Serializable {

    private Long id;

    private String userName;

    private String permission;

    public Account() {
    }

    public Account(Long id, String userName, String permission) {
        this.id = id;
        this.userName = userName;
        this.permission = permission;
    }

    public Long getId() {
        return id;
    }

    public Account setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Account setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPermission() {
        return permission;
    }

    public Account setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public static Account build(){
        return new Account();
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }
}
