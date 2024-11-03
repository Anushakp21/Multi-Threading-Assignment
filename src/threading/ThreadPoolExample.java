package threading;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {

    // Task class that implements Runnable, representing work for each thread.
    static class Task implements Runnable {
        private final String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("Executing " + name + " by " + Thread.currentThread().getName());
            try {
                // Simulating work with sleep.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(name + " was interrupted.");
            }
            System.out.println(name + " completed by " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        // Define the core and maximum pool sizes, and the keep-alive time.
        int corePoolSize = 2;
        int maximumPoolSize = 4;
        long keepAliveTime = 10;

        // Create a ThreadPoolExecutor with a fixed-size pool and a bounded queue.
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                ((ThreadPoolExecutor) Executors.newFixedThreadPool(maximumPoolSize)).getQueue()
        );

        // Submit several tasks to the pool.
        for (int i = 1; i <= 6; i++) {
            Task task = new Task("Task-" + i);
            System.out.println("Submitting " + task.name);
            executor.execute(task);
        }

        // Initiate a graceful shutdown after submitting all tasks.
        executor.shutdown();
        
        try {
            // Wait for all tasks to complete before proceeding.
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println("All tasks are finished.");
    }
}

