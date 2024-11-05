package synchronous_operation;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    // Initialize CountDownLatch with a count of 3, meaning we are waiting for 3 tasks to complete.
    private static final int TASK_COUNT = 3;
    private static CountDownLatch latch = new CountDownLatch(TASK_COUNT);

    // Worker class simulating a task.
    static class Worker implements Runnable {
        private final String name;

        public Worker(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(name + " is starting its task.");
                Thread.sleep((int) (Math.random() * 1000) + 500); // Simulate work with random sleep
                System.out.println(name + " has completed its task.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                latch.countDown(); // Reduce the count by 1
                System.out.println(name + " has called countDown(). Remaining count: " + latch.getCount());
            }
        }
    }

    public static void main(String[] args) {
        // Creating and starting three worker threads
        Thread worker1 = new Thread(new Worker("Worker-1"));
        Thread worker2 = new Thread(new Worker("Worker-2"));
        Thread worker3 = new Thread(new Worker("Worker-3"));

        worker1.start();
        worker2.start();
        worker3.start();

        try {
            System.out.println("Main thread waiting for all workers to complete...");
            latch.await(); // Main thread waits until the count reaches 0
            System.out.println("All workers have completed. Main thread resuming.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

