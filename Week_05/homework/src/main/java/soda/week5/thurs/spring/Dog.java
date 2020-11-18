package soda.week5.thurs.spring;

import org.springframework.stereotype.Component;

public class Dog {

    private String dogName;
    private Integer age;

    public Dog() {
    }

    public Dog(Integer age) {
        this.age = age;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "dogName='" + dogName + '\'' +
                ", age=" + age +
                '}';
    }
}
