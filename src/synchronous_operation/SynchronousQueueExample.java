package synchronous_operation;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SynchronousQueueExample {

    // Shared blocking queue with a fixed capacity.
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);

    // Producer class to add items to the queue.
    static class Producer implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 1; i <= 2; i++) {
                    System.out.println("Producer is producing: " + i);
                    queue.put(i);  // Blocks if the queue is full
                    System.out.println("Producer added: " + i);
                    Thread.sleep(500);  // Simulate production time
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Consumer class to take items from the queue.
    static class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 1; i <= 5; i++) {
                    Integer item = queue.take();  // Blocks if the queue is empty
                    System.out.println("Consumer took: " + item);
                    Thread.sleep(1000);  // Simulate consumption time
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Thread producerThread = new Thread(new Producer(), "Producer-Thread");
        Thread consumerThread = new Thread(new Consumer(), "Consumer-Thread");

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Producer and Consumer tasks completed.");
    }
}

