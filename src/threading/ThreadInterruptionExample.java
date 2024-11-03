package threading;

public class ThreadInterruptionExample {

    // Task class representing a long-running task that checks for interruption.
    static class LongRunningTask implements Runnable {
        @Override
        public void run() {
            System.out.println("Task started by " + Thread.currentThread().getName());

            for (int i = 1; i <= 10; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Task interrupted! Exiting gracefully...");
                    break;
                }

                // Simulate some work by sleeping for a short time.
                System.out.println("Processing step " + i);
                try {
                    Thread.sleep(1000); // Simulate work by sleeping for 1 second
                } catch (InterruptedException e) {
                    // Handle the interruption during sleep.
                    System.out.println("Task was interrupted during sleep. Cleaning up...");
                    Thread.currentThread().interrupt(); // Re-interrupt to handle it after cleanup.
                    break;
                }
            }

            System.out.println("Task completed by " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        Thread taskThread = new Thread(new LongRunningTask(), "Worker-Thread");

        System.out.println("Starting the task...");
        taskThread.start();

        // Let the task run for 3 seconds, then interrupt it.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Re-interrupt if main thread was interrupted
        }

        System.out.println("Requesting to cancel the task...");
        taskThread.interrupt(); // Interrupt the thread to request cancellation

        // Wait for the task thread to finish.
        try {
            taskThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Task finished or was canceled. Exiting program.");
    }
}

