package com.company.yash;

import static com.company.yash.ThradColor.COLOR1;
import static com.company.yash.ThradColor.COLOR3;

public class Main {

    public static void main(String[] args) {
        System.out.println(COLOR1 + "Hello from main thread");

        Thread anotherThread = new AnotherThread();
        anotherThread.start();

        new Thread(){
            public void run(){
                System.out.println(COLOR3 + "Hello from anonymous class thread");
                try{
                    anotherThread.join();
                }catch (InterruptedException e){
                    System.out.println(COLOR3 + "I get interrupted");
                }
            }
        }.start();

       Thread myThread = new Thread(new MyRunnable()){
        public void run(){
            System.out.println("Hello from runnable");
        }
       };
       myThread.start();

        System.out.println(COLOR1 + "Hello again from main thread");

    }
}
