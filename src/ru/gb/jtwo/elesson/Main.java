package ru.gb.jtwo.elesson;

public class Main {
    static final int size = 10000000;
    static final int h = size / 2;
    static float[] arr = new float[size];

    public static void main(String[] args) {
        arrayFiling(arr);
        splitArrayFiling(arr);

    }

    static void arrayFiling(float[] arr){
        long a = System.currentTimeMillis();
        System.out.println("Время до заполнения единицами: " + a);
        for(int i=0; i <arr.length; i++){
            arr[i] = 1;
        }
        long b = System.currentTimeMillis();
        System.out.println("Время после заполнения единицами: " + b);
        System.out.println("Метод 1 выполнялся: " + (b-a) + " миллисекунд");

        long c = System.currentTimeMillis();
        System.out.println("Время до заполнения новыми значениями: " + c);
        for(int i=0; i <arr.length; i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long d = System.currentTimeMillis();
        System.out.println("Время после заполнения новыми значениями: " + d);
        System.out.println("Метод 2 выполнялся: " + (d-c) + " миллисекунд");
    }

    static void splitArrayFiling(float[] arr){
        long a = System.currentTimeMillis();
        System.out.println("Время до деления массива: " + a);
        float[] a1 = new float [h];
        float[] a2 = new float [h];
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        long b = System.currentTimeMillis();
        System.out.println("Время после деления массива: " + b);
        ArrayFilingThread a1Thread = new ArrayFilingThread("a1", a1, 1, h);
        ArrayFilingThread a2Thread = new ArrayFilingThread("a2", a2, 2, h);
        try {
            a1Thread.join();
            a2Thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long c = System.currentTimeMillis();
        System.out.println("Время после заполнения новыми значениями: " + c);
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        long d = System.currentTimeMillis();
        System.out.println("Время после склейки массива: " + d);
        System.out.println("Метод 3 выполняется: " + (d-a) + " миллисекунд");
    }


}
