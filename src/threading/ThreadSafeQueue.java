package threading;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadSafeQueue<T> {

    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    // Constructor to set the maximum capacity of the queue.
    public ThreadSafeQueue(int capacity) {
        this.capacity = capacity;
    }

    // Method to add an element to the queue, blocking if the queue is full.
    public synchronized void enqueue(T item) throws InterruptedException {
        while (queue.size() == capacity) {
            System.out.println("Queue is full. Waiting to enqueue...");
            wait(); // Wait until space is available in the queue
        }
        queue.add(item);
        System.out.println("Enqueued: " + item);
        notifyAll(); // Notify any waiting threads that an item has been enqueued
    }

    // Method to remove an element from the queue, blocking if the queue is empty.
    public synchronized T dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            System.out.println("Queue is empty. Waiting to dequeue...");
            wait(); // Wait until an item is available in the queue
        }
        T item = queue.poll();
        System.out.println("Dequeued: " + item);
        notifyAll(); // Notify any waiting threads that an item has been dequeued
        return item;
    }

    // Optional: Method to check the current size of the queue.
    public synchronized int size() {
        return queue.size();
    }

    public static void main(String[] args) {
        ThreadSafeQueue<Integer> queue = new ThreadSafeQueue<>(5);

        // Producer thread to add items to the queue.
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    queue.enqueue(i);
                    Thread.sleep(500); // Simulate some delay between enqueues
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Consumer thread to remove items from the queue.
        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    queue.dequeue();
                    Thread.sleep(1000); // Simulate some delay between dequeues
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();

        // Wait for both threads to finish.
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("All tasks completed.");
    }
}

