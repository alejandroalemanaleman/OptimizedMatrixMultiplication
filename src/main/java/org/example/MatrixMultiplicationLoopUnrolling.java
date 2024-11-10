package org.example;

public class MatrixMultiplicationLoopUnrolling {

    public double[][] execute(double[][] a, double[][] b) {
        int n = a.length;
        double[][] result = new double[n][n];

         for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double sum = 0.0;
                int k = 0;

                 for (; k <= n - 2; k += 2) {
                    sum += a[i][k] * b[k][j] + a[i][k + 1] * b[k + 1][j];
                }

                 for (; k < n; k++) {
                    sum += a[i][k] * b[k][j];
                }

                result[i][j] = sum;
            }
        }
        return result;
    }
}
