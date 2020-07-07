public class Main {
	
	
	public static void main(String[] args) {
		Runtime runtime = Runtime.getRuntime();

		long availableBytes = runtime.freeMemory();
		System.out.println("Available memory: " + availableBytes / 1024 + "k");

		// let's create a ton of garbage....
		Customer c;
		for (int i = 0; i < 1000000; i++) {
			c = new Customer("John");
		}

		availableBytes = runtime.freeMemory();
		System.out.println("Available memory: " + availableBytes / 1024 + "k");

		System.gc(); // No Guarentee that is will execute, it is totaly depends on JVM.

		availableBytes = runtime.freeMemory();
		System.out.println("Available memory: " + availableBytes / 1024 + "k");
	}

}
