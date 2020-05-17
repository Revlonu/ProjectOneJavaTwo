package ru.gb.jtwo.alesson.online;

public class Animal {
    int age;
    public String name;

    public Animal(String name) {
        this.name = name;
        age = 10;
    }

    public void walk() {
        System.out.println(name + " walks");
    }
}
