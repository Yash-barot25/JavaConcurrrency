package com.company.yash;

import java.util.concurrent.TimeUnit;

public class Main {

    static final Object lock1 = new Object();
    static final Object lock2 = new Object();

    public static void main(String[] args) {

        new Thread1().start();
        new Thread2().start();

    }

    private static class Thread1 extends Thread {
        @Override
        public void run() {
            synchronized (lock1) {
                System.out.println("Thread 1: Got lock 1");
                try{
//                    Thread.sleep(100);
                    TimeUnit.MILLISECONDS.sleep(100);
                }catch (InterruptedException ignore){

                }
                System.out.println("Thread 1: Needs lock 2");
                synchronized (lock2) {
                    System.out.println("Thread 1: has both lock 1 and lock 2");
                }
                System.out.println("Thread 1: Releasing lock 2");
            }
            System.out.println("Thread 1: Has Released lock 1");
        }
    }

    private static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (lock1) {
                System.out.println("Thread 2: Got lock 1");
                try{
                    Thread.sleep(100);
                }catch (InterruptedException ignore){

                }
                System.out.println("Thread 2: Needs lock 2");
                synchronized (lock2) {
                    System.out.println("Thread 2: has both lock 1 and lock 2");
                }
                System.out.println("Thread 2: Releasing lock 2");
            }
            System.out.println("Thread 2: Has Released lock 1");
        }
    }
}

