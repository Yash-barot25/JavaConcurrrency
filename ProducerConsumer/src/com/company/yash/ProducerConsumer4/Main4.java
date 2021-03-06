package com.company.yash.ProducerConsumer4;
//Using Lock object

import com.company.yash.ThreadColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static com.company.yash.ProducerConsumer4.Main4.EOF;


public class Main4 {
    public static final String EOF = "EOF";

    public static void main(String[] args) {

        List<String> buffer = new ArrayList<>();
        ReentrantLock lock = new ReentrantLock();

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_PURPLE, lock);
        MyCounsumer counsumer1 = new MyCounsumer(buffer, ThreadColor.ANSI_BLUE, lock);
        MyCounsumer counsumer2 = new MyCounsumer(buffer, ThreadColor.ANSI_RED, lock);

        executorService.execute(producer);
        executorService.execute(counsumer1);
        executorService.execute(counsumer2);

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("I'm reporting from future.");
                return ThreadColor.ANSI_GREEN + "Hello i'm returned";
            }
        });

        try{
            System.out.println(future.get());
        }catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }

        executorService.shutdownNow();
//        if (executorService.isShutdown()) {
//            System.out.println("We are closing off");
//        } else {
//            System.out.println("We are still live");
//        }
        //  executorService.shutdownNow();

    }
}

class MyProducer implements Runnable {

    private List<String> buffer;
    private String color;
    private ReentrantLock lock;

    public MyProducer(List<String> buffer, String color, ReentrantLock lock) {
        this.buffer = buffer;
        this.color = color;
        this.lock = lock;
    }

    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for (String num : nums) {
            try {
                System.out.println(color + "Adding..." + num);
                lock.lock();
                try {
                    buffer.add(num);
                } finally {
                    lock.unlock();
                }


                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                System.out.println(color + "Producer was interrupted.");
                // System.exit(0);
            }
        }
        System.out.println(color + "Adding EOF and Exiting...");
        lock.lock();
        try {
            buffer.add("EOF");
        } finally {
            lock.unlock();
        }


    }
}

class MyCounsumer implements Runnable {
    private List<String> buffer;
    private String color;
    private ReentrantLock lock;

    public MyCounsumer(List<String> buffer, String color, ReentrantLock lock) {
        this.buffer = buffer;
        this.color = color;
        this.lock = lock;
    }

    public void run() {
        int counter = 0;

        while (true) {
            if (lock.tryLock()) {
                try {
                    if (buffer.isEmpty()) {
                        continue;
                    }
                    System.out.println(color + "Counter = " + counter);
                    counter = 0;
                    if (buffer.get(0).equals(EOF)) {
                        System.out.println(color + "Exiting");
                        break;
                    } else {
                        System.out.println(color + "Removed " + buffer.remove(0));
                    }
                } finally {
                    lock.unlock();
                }
            } else {
                counter++;
            }


        }

    }
}