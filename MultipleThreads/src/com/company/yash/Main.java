package com.company.yash;

public class Main {

    public static void main(String[] args) {
        CountDown countDown = new CountDown();


        Thread t1 = new CountDownThread(countDown);
        t1.setName("Thread 1");

        Thread t2 = new CountDownThread(countDown);
        t2.setName("Thread 2");

        t1.start();
        t2.start();

    }
}

class CountDown {
    private int i;

    public void doCountDown() {

        String color;

        switch (Thread.currentThread().getName()) {
            case "Thread 1":
                color = ThreadColor.ANSI_RED;
                break;

            case "Thread 2":
                color = ThreadColor.ANSI_PURPLE;
                break;

            default:
                color = ThreadColor.ANSI_BLUE;

        }
        synchronized (this) {
            for (i = 10; i > 0; i--) {
                System.out.println(color + Thread.currentThread().getName() + " : i = " + i);
            }
        }


    }


}

class CountDownThread extends Thread {

    private CountDown countDown;

    public CountDownThread(CountDown countDown) {
        this.countDown = countDown;
    }

    @Override
    public void run() {
        this.countDown.doCountDown();
    }
}
