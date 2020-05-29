package ru.gb.jtwo.elesson;

public class ArrayFilingThread extends Thread {
    float[] arr;
    int idArr; // используйте idArr 1 - для вычесления первой половины массива и idArr 2 - для вычесления второй половины массива
    int h;

    ArrayFilingThread(String name, float[] arr, int idArr , int h){
        super(name);
        this.arr = arr;
        this.idArr =idArr;
        this.h = h;
        start();
    }

    @Override
    public void run() {
        if(idArr == 1){
            ArrayFilingThreadOne(arr);
        } else if(idArr == 2){
            ArrayFilingThreadTwo(arr);
        } else {
            System.out.println("У потока: " + ArrayFilingThread.this.getName() + " неверный idArr: " + idArr + "\nИспользуйте idArr:1 - для вычесления первой половины массива\nИли idArr:2 - для вычесления второй половины массива" );
        }

    }

    public float[] ArrayFilingThreadOne(float[] arr){
        for(int i=0; i <arr.length; i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("a1 завершил расчёты");
        return arr;
    }

    public float[] ArrayFilingThreadTwo(float[] arr){
        for(int i=0; i <arr.length; i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + (h+i) / 5) * Math.cos(0.2f + (h+i) / 5) * Math.cos(0.4f + (h+i) / 2));
        }
        System.out.println("a2 завершил расчёты");
        return arr;
    }
}
