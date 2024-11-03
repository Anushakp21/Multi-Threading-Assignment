package multithreading;

import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
	static class Philosopher extends Thread {
		private final int id;
		private final Semaphore leftChopstick;
		private final Semaphore rightChopstick;

		public Philosopher(int id, Semaphore leftChopstick, Semaphore rightChopstick)
		{
			this.id = id;
			this.leftChopstick = leftChopstick;
			this.rightChopstick = rightChopstick;
		}

		private void think() 
		{
			System.out.println("Philosopher " + id + " is thinking.");
			try 
			{
				Thread.sleep((long) (Math.random() * 1000));
			} 
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}

		private void eat() 
		{
			System.out.println("Philosopher " + id + " is eating.");
			try
			{
				Thread.sleep((long) (Math.random() * 1000));
			}
			catch (InterruptedException e) 
			{
				Thread.currentThread().interrupt();
			}
		}

		@Override
		public void run()
		{
			while (true)
			{
				think(); // Philosopher thinks before trying to pick up chopsticks.

				try {
					// Pick up left chopstick.
					leftChopstick.acquire();
					System.out.println("Philosopher " + id + " picked up left chopstick.");

					// Pick up right chopstick.
					rightChopstick.acquire();
					System.out.println("Philosopher " + id + " picked up right chopstick.");

					eat(); // Philosopher eats when both chopsticks are acquired.

				} 
				catch (InterruptedException e)
				{
					Thread.currentThread().interrupt();
				}
				finally 
				{
					// Release both chopsticks after eating.
					rightChopstick.release();
					System.out.println("Philosopher " + id + " put down right chopstick.");
					leftChopstick.release();
					System.out.println("Philosopher " + id + " put down left chopstick.");
				}
			}
		}
	}
}