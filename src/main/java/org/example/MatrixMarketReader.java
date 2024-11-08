package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatrixMarketReader {

    public static MatrixMultiplicationSparseCSR loadMatrixFromFile(String filePath) throws IOException {
        List<Double> valuesList = new ArrayList<>();
        List<Integer> columnIndicesList = new ArrayList<>();
        List<Integer> rowPointersList = new ArrayList<>();
        rowPointersList.add(0);  // Primer valor del puntero de fila

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentRow = -1;
            int nonZeroCount = 0;

            // Procesar cada línea del archivo .mtx
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                if (tokens.length != 3) {
                    System.err.println("Formato inválido en la línea: " + line);
                    continue;
                }

                // Leer el valor de fila, columna y valor
                int row = Integer.parseInt(tokens[0]) - 1; // Convertir de 1 a 0 en base de índice
                int col = Integer.parseInt(tokens[1]) - 1;
                double value = Double.parseDouble(tokens[2]);

                valuesList.add(value);
                columnIndicesList.add(col);
                nonZeroCount++;

                // Actualizar rowPointers cuando cambie la fila
                if (row != currentRow) {
                    for (int i = currentRow + 1; i <= row; i++) {
                        rowPointersList.add(rowPointersList.get(rowPointersList.size() - 1) + nonZeroCount);
                        nonZeroCount = 0;
                    }
                    currentRow = row;
                }
            }

            // Completar los punteros de fila si hay filas vacías al final
            rowPointersList.add(rowPointersList.get(rowPointersList.size() - 1) + nonZeroCount);

            // Convertir listas a arreglos para CSRMatrix
            double[] values = valuesList.stream().mapToDouble(Double::doubleValue).toArray();
            int[] columnIndices = columnIndicesList.stream().mapToInt(Integer::intValue).toArray();
            int[] rowPointers = rowPointersList.stream().mapToInt(Integer::intValue).toArray();

            // Crear y devolver la matriz en formato CSR
            int rows = rowPointers.length - 1;
            int cols = columnIndices.length > 0 ? columnIndices[columnIndices.length - 1] + 1 : 0;
            return new MatrixMultiplicationSparseCSR(values, columnIndices, rowPointers, rows, cols);
        }
    }


}