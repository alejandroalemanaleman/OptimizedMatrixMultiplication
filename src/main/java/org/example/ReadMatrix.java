package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ReadMatrix {
    public static MatrixMultiplicationSparseCSR loadCSRMatrix(String valuesFile, String columnIndicesFile, String rowPointersFile, int rows, int cols) throws IOException {
        double[] values = loadDoubleArray(valuesFile);
        int[] columnIndices = loadIntArray(columnIndicesFile);
        int[] rowPointers = loadIntArray(rowPointersFile);

        return new MatrixMultiplicationSparseCSR(values, columnIndices, rowPointers, rows, cols);
    }

    private static double[] loadDoubleArray(String filePath) throws IOException {
        ArrayList<Double> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            list.add(Double.parseDouble(line));
        }
        reader.close();
        return list.stream().mapToDouble(Double::doubleValue).toArray();
    }

    private static int[] loadIntArray(String filePath) throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            list.add(Integer.parseInt(line));
        }
        reader.close();
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        long currentTimeMillis1 = System.currentTimeMillis();

        try {
            int rows = 525825; // Número de filas de la matriz A
            int cols = 525825; // Número de columnas de la matriz A
            MatrixMultiplicationSparseCSR csrMatrix = loadCSRMatrix("/Users/alejandroalemanaleman/Downloads/fichero_matlab/values.txt", "/Users/alejandroalemanaleman/Downloads/fichero_matlab/column_indices.txt", "/Users/alejandroalemanaleman/Downloads/fichero_matlab/row_pointers.txt", rows, cols);

        } catch (IOException e) {
            e.printStackTrace();
        }
        long currentTimeMillis2 = System.currentTimeMillis();
        System.out.println(currentTimeMillis2-currentTimeMillis1);
    }
}