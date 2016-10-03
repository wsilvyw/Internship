package com.endava.internship.silviarudicov.multithreadingV2;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by srudicov on 10/3/2016.
 */
public class ProducerConsumerV2 {

    public static void main(String args[]) {
        Queue<Integer> buffer = new LinkedList<>();
        int maxSize = 10;
        Thread producer = new Producer(buffer, maxSize, "Producer");
        Thread consumer1 = new Consumer(buffer, "Consumer1");
        Thread consumer2 = new Consumer(buffer, "Consumer2");
        producer.start();
        consumer1.start();
        consumer2.start();
    }

}

class Producer extends Thread {
    private Queue<Integer> queue;
    private int maxSize;

    public Producer(Queue<Integer> queue, int maxSize, String name) {
        super(name);
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                Thread.sleep(500);
                synchronized (queue) {
                    while (queue.size() == maxSize) {
                        try {
                            System.out.println("Queue is full, " + "Producer thread waiting for "
                                    + "consumer to take something from queue");
                            queue.wait();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    int randomNumber = 1 + (int) (i * 10 * Math.random());
                    System.out.println("Producing value : " + randomNumber);
                    queue.add(randomNumber);
                    queue.notifyAll();
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

class Consumer extends Thread {
    private Queue<Integer> queue;

    public Consumer(Queue<Integer> queue, String name) {
        super(name);
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        //System.out.println("Queue is empty," + Thread.currentThread().getName() +" is waiting for producer thread to put something in queue");
                        try {
                            queue.wait();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    System.out.println("Consumer "+ Thread.currentThread().getName() +" consuming value : " + queue.remove());
                    queue.notifyAll();
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}


