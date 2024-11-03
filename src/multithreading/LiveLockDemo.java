package multithreading;

public class LiveLockDemo {
	 static class Worker {
	        private boolean wantsToWork = true;

	        public void toggleWantsToWork() {
	            wantsToWork = !wantsToWork;
	        }

	        public boolean isWantsToWork() {
	            return wantsToWork;
	        }
	    }

	    public static void main(String[] args) {
	        Worker worker1 = new Worker();
	        Worker worker2 = new Worker();

	        Thread thread1 = new Thread(() -> {
	            while (worker1.isWantsToWork()) {
	                if (!worker2.isWantsToWork()) {
	                    System.out.println("Worker 1: Doing work...");
	                    break;
	                }
	                System.out.println("Worker 1: Waiting for Worker 2 to stop wanting to work");
	                worker1.toggleWantsToWork();
	                try { Thread.sleep(100); } catch (InterruptedException e) {}
	            }
	        });

	        Thread thread2 = new Thread(() -> {
	            while (worker2.isWantsToWork()) {
	                if (!worker1.isWantsToWork()) {
	                    System.out.println("Worker 2: Doing work...");
	                    break;
	                }
	                System.out.println("Worker 2: Waiting for Worker 1 to stop wanting to work");
	                worker2.toggleWantsToWork();
	                try { Thread.sleep(100); } catch (InterruptedException e) {}
	            }
	        });

	        thread1.start();
	        thread2.start();
	    }
}
