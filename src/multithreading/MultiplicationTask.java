package multithreading;

public class MultiplicationTask implements Runnable {

		private final int[][] matrixA;
	    private final int[][] matrixB;
	    private final int[][] result;
	    private final int row;
	    private final int col;

	    public MultiplicationTask(int[][] matrixA, int[][] matrixB, int[][] result, int row, int col) {
	        this.matrixA = matrixA;
	        this.matrixB = matrixB;
	        this.result = result;
	        this.row = row;
	        this.col = col;
	    }

	    @Override
	    public void run() {
	        int sum = 0;
	        for (int k = 0; k < matrixB.length; k++) {
	            sum += matrixA[row][k] * matrixB[k][col];
	        }

	        synchronized (result) {
	            result[row][col] = sum;
	        }
	    }
}
