package org.example;

public class MatrixMultiplicationStrassen {

    public double[][] execute(double[][] A, double[][] B) {
        int n = A.length;
        return strassenMultiply(A, B, n);
    }

    private double[][] strassenMultiply(double[][] A, double[][] B, int n) {
        // Caso base: multiplicación de elementos individuales
        if (n == 1) {
            double[][] result = {{A[0][0] * B[0][0]}};
            return result;
        }

        // Tamaño de las submatrices
        int newSize = n / 2;
        double[][] a11 = new double[newSize][newSize];
        double[][] a12 = new double[newSize][newSize];
        double[][] a21 = new double[newSize][newSize];
        double[][] a22 = new double[newSize][newSize];

        double[][] b11 = new double[newSize][newSize];
        double[][] b12 = new double[newSize][newSize];
        double[][] b21 = new double[newSize][newSize];
        double[][] b22 = new double[newSize][newSize];

        // Dividir las matrices A y B en submatrices
        splitMatrix(A, a11, 0, 0);
        splitMatrix(A, a12, 0, newSize);
        splitMatrix(A, a21, newSize, 0);
        splitMatrix(A, a22, newSize, newSize);

        splitMatrix(B, b11, 0, 0);
        splitMatrix(B, b12, 0, newSize);
        splitMatrix(B, b21, newSize, 0);
        splitMatrix(B, b22, newSize, newSize);

        // Aplicación de las 7 multiplicaciones de Strassen
        double[][] m1 = strassenMultiply(addMatrices(a11, a22), addMatrices(b11, b22), newSize);
        double[][] m2 = strassenMultiply(addMatrices(a21, a22), b11, newSize);
        double[][] m3 = strassenMultiply(a11, subtractMatrices(b12, b22), newSize);
        double[][] m4 = strassenMultiply(a22, subtractMatrices(b21, b11), newSize);
        double[][] m5 = strassenMultiply(addMatrices(a11, a12), b22, newSize);
        double[][] m6 = strassenMultiply(subtractMatrices(a21, a11), addMatrices(b11, b12), newSize);
        double[][] m7 = strassenMultiply(subtractMatrices(a12, a22), addMatrices(b21, b22), newSize);

        // Calcular las submatrices de la matriz resultante C
        double[][] c11 = addMatrices(subtractMatrices(addMatrices(m1, m4), m5), m7);
        double[][] c12 = addMatrices(m3, m5);
        double[][] c21 = addMatrices(m2, m4);
        double[][] c22 = addMatrices(subtractMatrices(addMatrices(m1, m3), m2), m6);

        // Unir las submatrices en una matriz de tamaño completo
        double[][] result = new double[n][n];
        joinMatrix(result, c11, 0, 0);
        joinMatrix(result, c12, 0, newSize);
        joinMatrix(result, c21, newSize, 0);
        joinMatrix(result, c22, newSize, newSize);

        return result;
    }

    private void splitMatrix(double[][] parent, double[][] child, int row, int col) {
        for (int i = 0; i < child.length; i++) {
            for (int j = 0; j < child.length; j++) {
                child[i][j] = parent[i + row][j + col];
            }
        }
    }

    private void joinMatrix(double[][] parent, double[][] child, int row, int col) {
        for (int i = 0; i < child.length; i++) {
            for (int j = 0; j < child.length; j++) {
                parent[i + row][j + col] = child[i][j];
            }
        }
    }

    private double[][] addMatrices(double[][] A, double[][] B) {
        int n = A.length;
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] + B[i][j];
            }
        }
        return result;
    }

    private double[][] subtractMatrices(double[][] A, double[][] B) {
        int n = A.length;
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] - B[i][j];
            }
        }
        return result;
    }
}
