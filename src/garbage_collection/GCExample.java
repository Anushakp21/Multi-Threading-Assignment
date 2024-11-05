package garbage_collection;

public class GCExample {
	public GCExample()
	{
		System.out.println(" object is garbage collected ");
	}
	@Override
	protected void finalize() throws Throwable {
		System.out.println("GCExample instance is being garbage collected.");
		super.finalize();
	}
	public static void main(String[] args) {
		GCExample obj1 = new GCExample();
		GCExample obj2 = new GCExample();
		GCExample obj3 = new GCExample();

		obj1 = null;
		obj2 = null;
		obj3 = null;

		System.out.println("Requesting garbage collection...");
		System.gc();

		// main thread sleep for sometimes
		try
		{
			Thread.sleep(1000); 
		}
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
		System.out.println("End of main method.");
	}
}
