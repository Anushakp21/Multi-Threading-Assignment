package multithreading;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class ProducerConsumerExample {
	 static class SharedQueue {
	        private final Queue<Integer> queue = new LinkedList<>();
	        private final int maxSize;

	        // Semaphores to manage access to queue
	        private final Semaphore emptySlots;
	        private final Semaphore fullSlots;
	        private final Semaphore mutex;

	        public SharedQueue(int maxSize) {
	            this.maxSize = maxSize;
	            this.emptySlots = new Semaphore(maxSize); // Max size for empty slots
	            this.fullSlots = new Semaphore(0);        // Start with no items
	            this.mutex = new Semaphore(1);            // Mutex for mutual exclusion
	        }

	        // Producer adds items to the queue
	        public void produce(int item) throws InterruptedException {
	            emptySlots.acquire();       // Wait for an empty slot
	            mutex.acquire();            // Acquire mutex lock for mutual exclusion
	            queue.add(item);
	            System.out.println("Produced: " + item);
	            mutex.release();            // Release mutex lock
	            fullSlots.release();        // Signal that there's a new item
	        }

	        // Consumer removes items from the queue
	        public int consume() throws InterruptedException {
	            fullSlots.acquire();        // Wait for an available item
	            mutex.acquire();            // Acquire mutex lock for mutual exclusion
	            int item = queue.poll();
	            System.out.println("Consumed: " + item);
	            mutex.release();            // Release mutex lock
	            emptySlots.release();       // Signal that an empty slot is available
	            return item;
	        }
	    }

	    static class Producer extends Thread {
	        private final SharedQueue sharedQueue;

	        public Producer(SharedQueue sharedQueue) {
	            this.sharedQueue = sharedQueue;
	        }

	        @Override
	        public void run() {
	            for (int i = 0; i < 3; i++) {
	                try {
	                    sharedQueue.produce(i); // Produce items from 0 to 9
	                    Thread.sleep(100);      // Simulate production time
	                } catch (InterruptedException e) {
	                    Thread.currentThread().interrupt();
	                }
	            }
	        }
	    }

	    static class Consumer extends Thread {
	        private final SharedQueue sharedQueue;

	        public Consumer(SharedQueue sharedQueue) {
	            this.sharedQueue = sharedQueue;
	        }

	        @Override
	        public void run() {
	            for (int i = 0; i < 10; i++) {
	                try {
	                    sharedQueue.consume(); // Consume items
	                    Thread.sleep(150);     // Simulate consumption time
	                } catch (InterruptedException e) {
	                    Thread.currentThread().interrupt();
	                }
	            }
	        }
	    }

	    public static void main(String[] args) {
	        // Create a shared queue with a maximum size of 5
	        SharedQueue sharedQueue = new SharedQueue(5);

	        // Start producer and consumer threads
	        Thread producer = new Producer(sharedQueue);
	        Thread consumer = new Consumer(sharedQueue);

	        producer.start();
	        consumer.start();
	    }

}
