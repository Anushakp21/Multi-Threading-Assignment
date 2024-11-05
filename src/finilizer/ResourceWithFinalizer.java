package finilizer;

public class ResourceWithFinalizer implements AutoCloseable {

	public ResourceWithFinalizer() {
		System.out.println("Resource acquired");
	}

	@Override
	public void finalize() throws Throwable {
		try {
			System.out.println("Finalize method called: Resource cleanup through finalize.");
		} finally {
			super.finalize();
		}
	}

	@Override
	public void close() {
		System.out.println("Resource is closed");
	}

	public void useResource() {
		System.out.println("Using the resource...");
	}
}
