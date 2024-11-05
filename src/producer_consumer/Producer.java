package producer_consumer;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable
{
	BlockingQueue<Integer> sharedBuffer;
	int items=9;

	public Producer(BlockingQueue<Integer> sharedBuffer) {
		super();
		this.sharedBuffer = sharedBuffer;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			for(int i=1;i<items;i++)
			{
				System.out.println("Producer Produced = "+i);
				sharedBuffer.put(i);
				Thread.sleep(1000);
			}

		}
		catch(Exception e)
		{
			Thread.currentThread().interrupt();
			System.out.println("Producer Interrupted");
		}

	}

}
