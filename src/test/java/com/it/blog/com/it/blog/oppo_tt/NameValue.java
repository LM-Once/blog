package com.it.blog.com.it.blog.oppo_tt;

public class NameValue {

    private String name;

    private String value;

    public NameValue(String name,String value) {
        this.name = name;
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString(){
        return ("name=" + name + ", " + "value=" + value);
    }

}
