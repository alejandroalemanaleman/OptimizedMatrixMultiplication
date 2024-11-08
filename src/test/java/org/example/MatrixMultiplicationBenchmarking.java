package org.example;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.example.MatrixMarketReader.loadMatrixFromFile;

@BenchmarkMode({Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 1, time = 500, timeUnit = TimeUnit.MILLISECONDS) // 1 iteración de calentamiento de 500 ms
@Measurement(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS) // 3 iteraciones de medición de 500 ms cada una
@Fork(1)
public class MatrixMultiplicationBenchmarking {

    @State(Scope.Thread)
    public static class Operands {

        @Param({"10", "100", "500", "1000"})  // Tamaño de la matriz
        private int n;

        @Param({"0", "0.1", "0.2" ,"0.5", "0.7", "0.9"})  // Incluye valores para densas y sparse
        private double sparsity;

        private double[][] a;
        private double[][] b;

        private long initialMemory;
        private long initialCpuTime;

        @Setup(Level.Trial)
        public void setup() {
            a = new double[n][n];
            b = new double[n][n];
            Random random = new Random();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (random.nextDouble() >= sparsity) {
                        a[i][j] = random.nextDouble();
                    } else {
                        a[i][j] = 0.0;
                    }

                    if (random.nextDouble() >= sparsity) {
                        b[i][j] = random.nextDouble();
                    } else {
                        b[i][j] = 0.0;
                    }
                }
            }
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            initialMemory = runtime.totalMemory() - runtime.freeMemory();

            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            initialCpuTime = threadMXBean.getCurrentThreadCpuTime();
        }

        @TearDown(Level.Trial)
        public void tearDown() {
            Runtime runtime = Runtime.getRuntime();
            long finalMemory = runtime.totalMemory() - runtime.freeMemory();

            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            long finalCpuTime = threadMXBean.getCurrentThreadCpuTime();

            long memoryUsed = finalMemory - initialMemory;
            long cpuTimeUsed = finalCpuTime - initialCpuTime;

            System.out.println("Matrix size: " + n + "x" + n);
            System.out.println("Sparsity level: " + sparsity);
            System.out.println("Total Memory used: " + memoryUsed / (1024 * 1024) + " MB");
            System.out.println("Total CPU time used: " + cpuTimeUsed + " nanoseconds");
        }
    }

    @Benchmark
    public void multiplicationBasic(Operands operands) {
        BasicMatrixMultiplication basicMatrixMultiplication = new BasicMatrixMultiplication();
        basicMatrixMultiplication.execute(operands.a, operands.b);
    }
   /*
    @Benchmark
    public void multiplicationBig() throws IOException {
        String filePath = "/Users/alejandroalemanaleman/Downloads/mc2depi/mc2depi.mtx";  // Reemplaza con la ruta correcta
        MatrixMultiplicationSparseCSR csrMatrix = loadMatrixFromFile(filePath);
        csrMatrix.multiply(csrMatrix);
    }

    @Benchmark
    public void multiplicationWithBlocking(Operands operands) {
        BlockMatrixMultiplicationWithTrace blockMatrixMultiplication = new BlockMatrixMultiplicationWithTrace();
        blockMatrixMultiplication.execute(operands.a, operands.b);
    }

    @Benchmark
    public void multiplicationWithLoopUnrolling(Operands operands) {
        MatrixMultiplicationLoopUnrolling loopUnrollingMatrixMultiplication = new MatrixMultiplicationLoopUnrolling();
        loopUnrollingMatrixMultiplication.execute(operands.a, operands.b);
    }

    @Benchmark
    public void multiplicationWithStrassen(Operands operands) {
        MatrixMultiplicationStrassen strassenMatrixMultiplication = new MatrixMultiplicationStrassen();
        strassenMatrixMultiplication.execute(operands.a, operands.b);
    }*/
}