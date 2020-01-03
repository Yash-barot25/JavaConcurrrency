package com.company.yash;

import static com.company.yash.ThreadColor.ANSI_BLUE;

public class AnotherThread extends Thread {

    @Override
    public void run() {
        System.out.println(ANSI_BLUE + "Hello from " + currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(ANSI_BLUE + "I was interrupted");
        }
//
//        System.out.println(ANSI_BLUE + "I'm working again");

    }
}
