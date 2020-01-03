package com.company.yash.ProducerConsumer5;
//Using Lock object

import com.company.yash.ThreadColor;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.company.yash.ProducerConsumer5.Main5.EOF;


public class Main5 {
    public static final String EOF = "EOF";

    public static void main(String[] args) {

     final   ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<>(6);
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_PURPLE);
        MyCounsumer counsumer1 = new MyCounsumer(buffer, ThreadColor.ANSI_BLUE);
        MyCounsumer counsumer2 = new MyCounsumer(buffer, ThreadColor.ANSI_RED);

        executorService.execute(producer);
        executorService.execute(counsumer1);
        executorService.execute(counsumer2);

        executorService.shutdown();

    }
}

class MyProducer implements Runnable {

    private ArrayBlockingQueue<String> buffer;
    private String color;


    public MyProducer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;

    }

    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for (String num : nums) {
            try {
                System.out.println(color + "Adding..." + num);
                buffer.put(num);
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                System.out.println(color + "Producer was interrupted.");
                // System.exit(0);
            }
        }
        System.out.println(color + "Adding EOF and Exiting...");

        try {
            buffer.put("EOF");
        } catch (InterruptedException ignore) {

        }


    }
}

class MyCounsumer implements Runnable {
    private final ArrayBlockingQueue<String> buffer;
    private String color;


    public MyCounsumer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;

    }

    public void run() {

        while (true) {
            synchronized (buffer) {
                try {
                    if (buffer.isEmpty()) {
                        continue;
                    }
                    if (buffer.peek().equals(EOF)) {
                        System.out.println(color + "Exiting");
                        break;
                    } else {
                        System.out.println(color + "Removed " + buffer.take());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}