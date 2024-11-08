package org.example;
import java.util.HashMap;
import java.util.Map;

public class MatrixMultiplicationSparse {

    public static class SparseMatrixCOO {
        int rows;
        int cols;
        Map<String, Double> values;

        public SparseMatrixCOO(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
            this.values = new HashMap<>();
        }

        public void addValue(int row, int col, double value) {
            if (value != 0.0) {
                values.put(row + "," + col, value);
            }
        }

        public double getValue(int row, int col) {
            return values.getOrDefault(row + "," + col, 0.0);
        }
    }

    public SparseMatrixCOO multiply(SparseMatrixCOO A, SparseMatrixCOO B) {
        if (A.cols != B.rows) throw new IllegalArgumentException("Dimensiones incompatibles para la multiplicación");

        SparseMatrixCOO result = new SparseMatrixCOO(A.rows, B.cols);

        for (Map.Entry<String, Double> entryA : A.values.entrySet()) {
            String[] posA = entryA.getKey().split(",");
            int rowA = Integer.parseInt(posA[0]);
            int colA = Integer.parseInt(posA[1]);
            double valA = entryA.getValue();

            for (Map.Entry<String, Double> entryB : B.values.entrySet()) {
                String[] posB = entryB.getKey().split(",");
                int rowB = Integer.parseInt(posB[0]);
                int colB = Integer.parseInt(posB[1]);
                double valB = entryB.getValue();

                if (colA == rowB) { // Solo multiplicar cuando las posiciones coinciden
                    double existingValue = result.getValue(rowA, colB);
                    result.addValue(rowA, colB, existingValue + valA * valB);
                }
            }
        }
        return result;
    }
}
