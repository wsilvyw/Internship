package com.endava.internship.silviarudicov.multithreading;

import java.util.concurrent.*;

/**
 * Created by srudicov on 10/3/2016.
 */
public class ProducerConsumer {

    public ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);  // queue which orders elements FIFO, implements Serializable
    public Boolean continueProducing = Boolean.TRUE;

    public void put(Integer data) throws InterruptedException
    {
        this.queue.put(data);  // inserts the specified element at the tail of this queue
    }

    public Integer get() throws InterruptedException
    {
        return this.queue.poll(1, TimeUnit.SECONDS); // retrieves and removes the head of this queue waiting up 1 second
    }

    public static void main(String args[])
    {
        try
        {
            ProducerConsumer prodCon = new ProducerConsumer();

            ExecutorService threadPool = Executors.newFixedThreadPool(3); // nr. of threads

            threadPool.execute(new Consumer("1", prodCon)); // submits a Runnable task for execution and returns a Future representing that task
            threadPool.execute(new Consumer("2", prodCon)); // submits a Runnable task for execution and returns a Future representing that task

            Future producerStatus = threadPool.submit(new Producer(prodCon));  // submits a Runnable task for execution and returns a Future representing that task

            producerStatus.get(); // waits for the producer to finish its execution

            threadPool.shutdown(); // initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted.
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {

    private String name;
    private ProducerConsumer prodCon;

    public Consumer(String name, ProducerConsumer prodCon)
    {
        this.name = name;
        this.prodCon = prodCon;
    }

    public void run() {
        try
        {
            Integer data = prodCon.get();

            while (prodCon.continueProducing || data != null)
            {
                Thread.sleep(1000);
                System.out.println("Consumer " + this.name + " processed data : " + data);

                data = prodCon.get();
            }

            System.out.println("Consumer " + this.name + " finished its job.");
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }
}

class Producer implements Runnable {
    private ProducerConsumer prodCon;

    public Producer(ProducerConsumer prodCon) {
        this.prodCon = prodCon;
    }

    public void run() {
        try
        {
            for (int i = 1; i <= 5; i++)
            {
                Thread.sleep(500);
                int randomNumber = 1+(int)(i*10*Math.random());
                System.out.println("Producer produced: " + randomNumber);
                prodCon.put(randomNumber);
            }

            this.prodCon.continueProducing = Boolean.FALSE;
            System.out.println("Producer finished its job.");
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }
}
