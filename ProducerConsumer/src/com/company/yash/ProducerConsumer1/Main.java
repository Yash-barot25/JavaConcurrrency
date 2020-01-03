package com.company.yash.ProducerConsumer1;

import com.company.yash.ThreadColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.company.yash.ProducerConsumer1.Main.EOF;


public class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {

        List<String> buffer = new ArrayList<>();

        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_PURPLE);
        MyCounsumer counsumer1 = new MyCounsumer(buffer, ThreadColor.ANSI_BLUE);
        MyCounsumer counsumer2 = new MyCounsumer(buffer, ThreadColor.ANSI_RED);

        new Thread(producer).start();
        new Thread(counsumer1).start();
        new Thread(counsumer2).start();

    }
}

class MyProducer implements Runnable {

    private List<String> buffer;
    private String color;


    public MyProducer(List<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for (String num : nums) {
            try {
                System.out.println(color + "Adding..." + num);
                synchronized (buffer) {
                    buffer.add(num);
                }

                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                System.out.println(color + "Producer was interrupted.");
            }
        }
        System.out.println(color + "Adding EOF and Exiting...");
        synchronized (buffer) {
            buffer.add("EOF");
        }

    }
}

class MyCounsumer implements Runnable {
    private List<String> buffer;
    private String color;

    public MyCounsumer(List<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    public void run() {
        while (true) {
            synchronized (buffer) {
                if (buffer.isEmpty()) {
                    continue;
                }
                if (buffer.get(0).equals(EOF)) {
                    System.out.println(color + "Exiting");
                    break;
                } else {
                    System.out.println(color + "Removed " + buffer.remove(0));
                }
            }

        }

    }
}