package multithreading;

public class DeadLockDemo {
	 static class Resource {
	        private  String name;

	        public Resource(String name) {
	            this.name = name;
	        }

	        public String getName() {
	            return name;
	        }
	    }

	    public static void main(String[] args) {
	       Resource lock1 = new Resource("Lock1");
	       Resource lock2 = new Resource("Lock2");

	        Thread thread1 = new Thread(() -> {
	            synchronized (lock1) {
	                System.out.println("Thread 1: Holding " + lock1.getName());

	                try { Thread.sleep(100); } catch (InterruptedException e) {}

	                System.out.println("Thread 1: Waiting for " + lock2.getName());
	                synchronized (lock2) {
	                    System.out.println("Thread 1: Holding both locks!");
	                }
	            }
	        });

	        Thread thread2 = new Thread(() -> {
	            synchronized (lock2) {
	                System.out.println("Thread 2: Holding " + lock2.getName());

	                try { Thread.sleep(100); } catch (InterruptedException e) {}

	                System.out.println("Thread 2: Waiting for " + lock1.getName());
	                synchronized (lock1) {
	                    System.out.println("Thread 2: Holding both locks!");
	                }
	            }
	        });

	        thread1.start();
	        thread2.start();
	    }

}
