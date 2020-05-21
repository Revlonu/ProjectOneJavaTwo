package ru.gb.jtwo.clesson;

import java.util.HashMap;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        String[] glossary = new String[] {"Вызов", "Запись", "Удаление", "Вызов", "Сообщение", "Входящий", "Отправить", "Входящий", "Исходящий", "Отправить", "Вызов", "Вложить", "Вызов", "Запись", "Удаление", "Сообщение", "Входящий", "Исходящий", "Отправить", "Вложить",};
        System.out.println(glossary.length);
        outputContentWords(glossary);
        countWord(glossary);


        PhoneBook onePhoneBook = new PhoneBook();
        onePhoneBook.addPhoneBook("Петров", "89996663322", "petrov1@mail.ru");
        onePhoneBook.addPhoneBook("Петров", "89996663321", "petrov@mail.ru");
        onePhoneBook.addPhoneBook("Иванов", "89996664422", "ivanov@mail.ru");


        System.out.println(onePhoneBook.getPhoneSurname("Петров"));
        System.out.println(onePhoneBook.getEmailSurname("Петров"));

    }


    static void outputContentWords(String[] s){
        HashSet <String> listGlossary = new HashSet<>();
        for(int i = 0; i < s.length; i++){
            listGlossary.add(s[i]);
        }
        System.out.println(listGlossary);
    }

    static void countWord(String[] s){
        HashMap<String, Integer > glossaryMap = new HashMap<>();
        for(int i = 0; i < s.length; i++){
            if(glossaryMap.containsKey(s[i])){
                glossaryMap.put(s[i], glossaryMap.get(s[i]) + 1);
            } else {
                glossaryMap.put(s[i], 1);
            }
        }
        for (String key : glossaryMap.keySet()) {
            System.out.printf("Слово=%s, кол-во=%d \n", key, glossaryMap.get(key));
        }
    }


}
