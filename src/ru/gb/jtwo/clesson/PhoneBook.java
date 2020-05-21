package ru.gb.jtwo.clesson;

import java.util.ArrayList;
import java.util.HashMap;

public class PhoneBook {
    HashMap<String, ArrayList<Person>> hashMapPhoneBook;

    public PhoneBook(){
        HashMap<String, ArrayList<Person>> hashMapPhoneBook = new HashMap<>();
        this.hashMapPhoneBook = hashMapPhoneBook;
    }

    public void addPhoneBook(String u, String p, String e) {
        if(hashMapPhoneBook.containsKey(u)){
            Person person = new Person(p,e);
            ArrayList<Person> arrPerson = hashMapPhoneBook.get(u);
            arrPerson.add(person);
            hashMapPhoneBook.put(u, arrPerson);
        } else {
            Person person = new Person(p, e);
            ArrayList<Person> arrPerson = new ArrayList<>();
            arrPerson.add(person);
            hashMapPhoneBook.put(u, arrPerson);
        }
    }


    ArrayList<String> getPhoneSurname(String surname){
        ArrayList<Person> arrPerson = hashMapPhoneBook.get(surname);
        ArrayList<String> phoneSurname = new ArrayList<>();
        for(int i = 0; i < arrPerson.size(); i++){
            phoneSurname.add(arrPerson.get(i).getPersonePhone());
         }
        return phoneSurname;
    }

    ArrayList<String> getEmailSurname(String surname){
        ArrayList<Person> arrPerson = hashMapPhoneBook.get(surname);
        ArrayList<String> emailSurname = new ArrayList<>();
        for(int i = 0; i < arrPerson.size(); i++){
            emailSurname.add(arrPerson.get(i).getPersoneEmail());
        }
        return emailSurname;
    }


}
