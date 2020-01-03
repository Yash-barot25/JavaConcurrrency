package com.company.yash;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Message message = new Message();
        (new Thread(new Writer(message))).start();
        (new Thread(new Reader(message))).start();

    }
}

class Message {
    private String message;
    private boolean empty = true;

    public synchronized String read() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        empty = true;
        notifyAll();
        return message;
    }

    public synchronized void write(String message) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        empty = false;
        this.message = message;
        notifyAll();

    }
}

class Writer implements Runnable {
    private Message message;

    public Writer(Message message) {
        this.message = message;
    }

    public void run() {
        String[] messages = {
                "Twinkle, twinkle, little star",
                "How I wonder what you are!",
                "Up above the world so high",
                "Like a diamond in the sky",
                "When this blazing sun is gone",
                "When he nothing shines upon",
                "Then you show your little light",
                "Twinkle, twinkle, through the night",
                "Then the traveller in the dark",
                "Thanks you for your tiny spark",
                "He could not see where to go",
                "If you did not twinkle so",
                "In the dark blue sky you keep",
                "And often through my curtains peep",
                "For you never shut your eye",
                "Till the sun is in the sky",
                "As your bright and tiny spark",
                "Lights the traveller in the dark",
                "Though I know not what you are",
                "Twinkle, twinkle, little star."
        };

        Random random = new Random();

        for (String s : messages) {
            message.write(s);
            try {
                Thread.sleep(random.nextInt(4000));
            } catch (InterruptedException ignore) {

            }
        }
        message.write("Finished");
    }
}

class Reader implements Runnable {
    private Message message;

    public Reader(Message message) {
        this.message = message;
    }

    public void run() {
        Random random = new Random();
        for (String latestMessage = message.read(); !latestMessage.equals("Finished"); latestMessage = message.read()) {
            System.out.println(latestMessage);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException ignore) {

            }
        }


    }
}