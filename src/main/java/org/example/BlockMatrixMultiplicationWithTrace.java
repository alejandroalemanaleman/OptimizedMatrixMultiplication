package org.example;

public class BlockMatrixMultiplicationWithTrace {

    // Block size (tile size)
    private static final int BLOCK_SIZE = 2;

    public static void execute(double[][] a, double[][] b) {
        int N = a.length; // Matrix size (N x N)
        double[][] C = new double[N][N]; // Result matrix

        // Perform matrix multiplication with blocking
        blockMatrixMultiplication(a, b, C, N);
    }

    // Matrix multiplication using the blocking approach
    private static void blockMatrixMultiplication(double[][] A, double[][] B, double[][] C, int N) {
        for (int i = 0; i < N; i += BLOCK_SIZE) {
            for (int j = 0; j < N; j += BLOCK_SIZE) {
                for (int k = 0; k < N; k += BLOCK_SIZE) {
                    multiplyBlock(A, B, C, i, j, k, N);
                }
            }
        }
    }

    // Multiply individual blocks of the matrices
    private static void multiplyBlock(double[][] A, double[][] B, double[][] C, int rowBlock, int colBlock, int kBlock, int N) {
        for (int i = rowBlock; i < Math.min(rowBlock + BLOCK_SIZE, N); i++) {
            for (int j = colBlock; j < Math.min(colBlock + BLOCK_SIZE, N); j++) {
                double sum = 0;
                for (int k = kBlock; k < Math.min(kBlock + BLOCK_SIZE, N); k++) {
                    sum += A[i][k] * B[k][j];
                }
                C[i][j] += sum; // Update the result matrix
            }
        }
    }

    // Print a specific block from the matrix
    private static void printBlock(double[][] matrix, int rowStart, int colStart, int N) {
        for (int i = rowStart; i < Math.min(rowStart + BLOCK_SIZE, N); i++) {
            for (int j = colStart; j < Math.min(colStart + BLOCK_SIZE, N); j++) {
                System.out.printf("%.2f ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    // Print the entire matrix
    private static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%.2f ", matrix[i][j]);
            }
            System.out.println();
        }
    }
}
