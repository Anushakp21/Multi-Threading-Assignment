package synchronous_operation;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {

    // Number of threads (parties) that must wait at the barrier
    private static final int NUM_PARTIES = 3;
    
    // Create a CyclicBarrier for 3 parties, with a barrier action that runs when all threads reach the barrier
    private static final CyclicBarrier barrier = new CyclicBarrier(NUM_PARTIES, 
            () -> System.out.println("All parties have reached the barrier. Proceeding to the next phase.\n"));

    // Worker class that simulates a task with phases
    static class Worker implements Runnable {
        private final String name;

        public Worker(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 2; i++) { // Simulate 2 phases
                    System.out.println(name + " is performing phase " + i + " of the task.");
                    Thread.sleep((int) (Math.random() * 1000) + 500); // Simulate time taken to complete the phase
                    System.out.println(name + " completed phase " + i + " and is waiting at the barrier.");

                    // Wait at the barrier until all parties have reached it
                    barrier.await();

                    System.out.println(name + " has crossed the barrier and is moving to the next phase.");
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println(name + " was interrupted or the barrier was broken.");
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        // Create and start worker threads
        Thread worker1 = new Thread(new Worker("Worker-1"));
        Thread worker2 = new Thread(new Worker("Worker-2"));
        Thread worker3 = new Thread(new Worker("Worker-3"));

        worker1.start();
        worker2.start();
        worker3.start();

        try {
            worker1.join();
            worker2.join();
            worker3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("All workers have completed their tasks.");
    }
}

