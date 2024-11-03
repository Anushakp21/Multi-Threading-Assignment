package threading;

public class ThreadLocalExample {

    // Thread-local variable for maintaining a separate counter for each thread.
    private static ThreadLocal<Integer> threadLocalCounter = ThreadLocal.withInitial(() -> 0);

    // Task class simulating some work with the thread-local counter.
    static class Task implements Runnable {
        @Override
        public void run() {
            // Retrieve and modify the thread-local counter for each thread
            for (int i = 0; i < 5; i++) {
                int currentCount = threadLocalCounter.get();
                threadLocalCounter.set(currentCount + 1);
                
                System.out.println(Thread.currentThread().getName() + " - Counter: " + threadLocalCounter.get());

                try {
                    Thread.sleep(100); // Simulate some work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            // Cleanup: remove the thread-local value after task completion
            threadLocalCounter.remove();
        }
    }

    public static void main(String[] args) {
        // Creating multiple threads that will use the thread-local variable
        Thread thread1 = new Thread(new Task(), "Thread-1");
        Thread thread2 = new Thread(new Task(), "Thread-2");
        Thread thread3 = new Thread(new Task(), "Thread-3");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("All threads have completed their tasks.");
    }
}

