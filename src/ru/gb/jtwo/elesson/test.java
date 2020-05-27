package ru.gb.jtwo.elesson;

public class test {

    private static class MyThread extends Thread {
        MyThread(String name) {
            super(name);
            start();
        }

        @Override
        public void run() {
            System.out.println(getName() + " started");
//            while(!isInterrupted());
            for (long i = 0; i < 10_000_000_000L; i++) {
                long a = i * 432;
            }
            System.out.println(getName() + " stopped");
        }
    }

    private static int a = 0;
    private static int b = 0;
    private static int c = 0;

    private static Object monitor = new Object();

    private static void increment() {
        synchronized (monitor) {
            for (int i = 0; i < 1_000_000; i++) {
                a = a + 1;
                b = b + 1;
                c = c + 1;
            }
            System.out.printf("a=%d, b=%d, c=%d\n", a, b, c);
        }
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                increment();
            }
        };

        new Thread(r, "Thread#1").start();
        new Thread(r, "Thread#2").start();
        new Thread(r, "Thread#3").start();
    }




}
