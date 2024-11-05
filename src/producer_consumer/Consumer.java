package producer_consumer;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable 
{
  BlockingQueue<Integer> sharedBuffer;
  
	public Consumer(BlockingQueue<Integer> sharedBuffer) {
	super();
	this.sharedBuffer = sharedBuffer;
}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
		while(true)
		{
			Integer item=sharedBuffer.take();
			System.out.println("Consumer consumed");
            Thread.sleep(2000);
		}
		}
		catch(Exception e)
		{
			Thread.currentThread().interrupt();
			System.out.println("Consumer Interrupted");
		}
	}

}
