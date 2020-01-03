package com.company.yash;

import static com.company.yash.ThreadColor.ANSI_RED;

public class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(ANSI_RED +  "Hello from myRunnable");
    }
}
