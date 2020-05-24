package ru.gb.jtwo.clesson;

public class Person {
    String phone;
    String email;


    public Person(String phone, String email){
        this.phone = phone;
        this.email = email;
    }


    String getPersonePhone(){
        return phone;
    }

    String getPersoneEmail(){
        return email;
    }


}
