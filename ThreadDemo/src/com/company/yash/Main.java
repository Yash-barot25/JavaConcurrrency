package com.company.yash;

import java.util.concurrent.TimeUnit;

import static com.company.yash.ThreadColor.ANSI_PURPLE;

public class Main {

    public static void main(String[] args) {
        System.out.println( ANSI_PURPLE +
                 "Hello from main thread");

        Thread anotherThread = new AnotherThread();
        anotherThread.setName("===== Another Thread =====");
        anotherThread.start();

       // System.out.println(ANSI_PURPLE + "Hello again from Main thread");

      //  new Thread(new MyRunnable()).start();

     Thread t1=  new Thread(){
            @Override
            public void run() {
                System.out.println("I got up");

                try{
                    anotherThread.join();
                }catch (InterruptedException ignored){
                    System.out.println("I awake");
                }

            }
        };
     t1.start();


//        anotherThread = new AnotherThread();
//        anotherThread.start();


    }
}
