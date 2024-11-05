package memory_usage;

import java.util.ArrayList;
import java.util.List;

public class MemoryUsageExample {
	public static class LargeData
	{
		private int[] data;
		public LargeData(int size)
		{
			this.data= new int[size];	
		}
	}
	public static void main(String[] args) {
		List<LargeData> dataList = new ArrayList<>();
		int array_size=100000;
		int iteration=2;
		for(int i=0;i<iteration;i++)
		{
			System.out.println("Iteration : "+(i+1));
		}
		printMemoryUsage("Before adding data");

		dataList.clear();

		System.gc();

		printMemoryUsage("After adding data");

		System.out.println();
	}
	private static void printMemoryUsage(String message) {
		Runtime runtime = Runtime.getRuntime();//getting runtime
		long usedMemory = runtime.totalMemory() - runtime.freeMemory();
		System.out.println(message + " - Used Memory: " + (usedMemory / (1024 * 1024)) + " MB ");		
	}
}
