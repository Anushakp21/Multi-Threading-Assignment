package multithreading;

public class MatrixMultiplication {
	 public static void main(String[] args) {
	        int[][] matrixA = {
	            {1, 2},
	            {4, 5}
	        };
	        int[][] matrixB = {
	            {7, 8},
	            {9, 10},
	        };
	        
	        int[][] result = new int[matrixA.length][matrixB[0].length];

	        int rowsA = matrixA.length;
	        int columnsB = matrixB[0].length;

	        Thread[][] t = new Thread[rowsA][columnsB];

	        for (int i = 0; i < rowsA; i++) {
	            for (int j = 0; j < columnsB; j++) {
	                t[i][j] = new Thread(new MultiplicationTask(matrixA, matrixB, result, i, j));
	                t[i][j].start();
	            }
	        }
	       System.out.println("Result matrix:");
	        for (int[] row : result) {
	            for (int val : row) {
	                System.out.print(val + " ");
	            }
	            System.out.println();
	        }
	    }
}
