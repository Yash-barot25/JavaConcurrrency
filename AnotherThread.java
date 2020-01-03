package com.company.yash;

import static com.company.yash.ThradColor.COLOR2;

public class AnotherThread extends Thread{

    @Override
    public void run() {
        System.out.println(COLOR2 + "Hello from another thread");

        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            System.out.println(COLOR2 + "Another thread wake me up");
            return;
        }

        System.out.println(COLOR2 + "Hello again from another thread");
    }
}
