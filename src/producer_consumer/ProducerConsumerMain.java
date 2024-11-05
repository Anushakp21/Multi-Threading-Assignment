package producer_consumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerMain {
	public static void main(String[] args) {
		BlockingQueue<Integer> sharedBuffer= new ArrayBlockingQueue<Integer>(5);
		Producer p=new Producer(sharedBuffer);
		Consumer c=new Consumer(sharedBuffer);
		
		Thread producerThread=new Thread(p);
		Thread consumerThread=new Thread(c);
		
		producerThread.start();
		consumerThread.start();
		try
		{
		Thread.sleep(2000);
		 producerThread.interrupt();
         consumerThread.interrupt();
		}
		catch(Exception e) {
			System.out.println("Interrupted Exception");
			
		}
	}

}
