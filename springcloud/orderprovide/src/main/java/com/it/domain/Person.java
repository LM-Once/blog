package com.it.domain;

public class Person {

    private Integer id;

    private String personName;

    private String personGender;

    private String description;

    public Integer getId() {
        return id;
    }

    public Person setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getPersonName() {
        return personName;
    }

    public Person setPersonName(String personName) {
        this.personName = personName;
        return this;
    }

    public String getPersonGender() {
        return personGender;
    }

    public Person setPersonGender(String personGender) {
        this.personGender = personGender;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Person setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", personName='" + personName + '\'' +
                ", personGender='" + personGender + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
