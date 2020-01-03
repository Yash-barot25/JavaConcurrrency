package com.company.yash;

import static com.company.yash.ThradColor.COLOR4;

public class MyRunnable  implements Runnable{

    public void run(){
        System.out.println(COLOR4 + "Hello from runnable class thread.");
    }
}
