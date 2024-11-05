package finilizer;

public class ResourceManagementTest {
	public static void main(String[] args) {
		System.out.println("Strating try-with resource");
		tryWithResourcesExample();

		System.out.println("Starting without try-with-resources...");
		withoutTryWithResourcesExample();

		System.gc();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void withoutTryWithResourcesExample() {
		// TODO Auto-generated method stub
		try(ResourceWithFinalizer rs=new ResourceWithFinalizer())
		{
			rs.useResource();
		} catch (Exception e) {
			System.out.println("Exception during resource usage.");
		}	
	}

	private static void tryWithResourcesExample() {
		// TODO Auto-generated method stub
		ResourceWithFinalizer resource = new ResourceWithFinalizer();
		try {
			resource.useResource();
		} finally {
			// Manually close the resource to simulate cleanup
			resource.close();
		}

	}

}
