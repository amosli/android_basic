package com.amos.xml.domain;

/**
 * Created by amosli on 14-6-3.
 */

public class Person {
    private Integer id;
    private String name;
    private Integer age;

    public Person() {
    }

    public Person( Integer id,String name, Integer age) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        if (age < 1 || age > 100) {
            this.age = 0;
        } else {
            this.age = age;
        }
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {

        return name;

    }

    public void setName(String name) {
        this.name = name;
    }
}
