package com.work.math;

import com.work.math.util.QuakeMagicExplanation;
import com.work.math.util.SqrtOptimizedExplanation;
import com.work.math.util.ConsoleHelper;

public class Main {

    public static void main(String[] args) {
        double[] testValues = {0, 1, 4, 9, 16, 25, 100, 1000, 898.789};

        ConsoleHelper.println("Comparacion de metodos para calcular raiz cuadrada:");
        ConsoleHelper.println("=".repeat(60));

        for (double value : testValues) {
            ConsoleHelper.printf("√%.3f:%n", value);
            ConsoleHelper.printf("  Math.sqrt():     %.10f%n", SqrtCalculator.sqrt(SqrtCalculator.Algorithms.MATH, value));
            ConsoleHelper.printf("  Newton-Raphson:  %.10f%n", SqrtCalculator.sqrt(SqrtCalculator.Algorithms.NEWTON, value));
            ConsoleHelper.printf("  Optimizado:      %.10f%n", SqrtCalculator.sqrt(SqrtCalculator.Algorithms.OPTIMIZED, value));
            ConsoleHelper.printf("  Quake:           %.10f%n", SqrtCalculator.sqrt(SqrtCalculator.Algorithms.QUAKE, value));
            ConsoleHelper.printf("  Caculate Root:   %.10f%n", SqrtCalculator.sqrt(SqrtCalculator.Algorithms.ROOT, value));

            if (value == (int) value && value >= 0) {
                ConsoleHelper.printf("  Entero:          %.10f%n", SqrtCalculator.sqrt(SqrtCalculator.Algorithms.SEARCH, (int) value));
            }
            ConsoleHelper.println();
        }

        benchmarkMethods();

        QuakeMagicExplanation.explanation();
        SqrtOptimizedExplanation.explanation();
    }

    private static void benchmarkMethods() {
        ConsoleHelper.println("Benchmark (1,000,000 operaciones):");
        ConsoleHelper.println("=".repeat(40));

        int iterations = 1_000_000;

        // Benchmark Math.sqrt()
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            SqrtCalculator.sqrt(SqrtCalculator.Algorithms.MATH, i);
        }
        long mathTime = System.nanoTime() - start;

        // Benchmark Newton-Raphson
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            SqrtCalculator.sqrt(SqrtCalculator.Algorithms.NEWTON, i);
        }
        long newtonTime = System.nanoTime() - start;

        // Benchmark optimizado
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            SqrtCalculator.sqrt(SqrtCalculator.Algorithms.OPTIMIZED, i);
        }
        long optimizedTime = System.nanoTime() - start;

        // Benchmark Quake
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            SqrtCalculator.sqrt(SqrtCalculator.Algorithms.QUAKE, i);
        }
        long quakeTime = System.nanoTime() - start;

        ConsoleHelper.printf("Math.sqrt():     %d ms%n", mathTime / 1_000_000);
        ConsoleHelper.printf("Newton-Raphson:  %d ms%n", newtonTime / 1_000_000);
        ConsoleHelper.printf("Optimizado:      %d ms%n", optimizedTime / 1_000_000);
        ConsoleHelper.printf("Quake:           %d ms%n", quakeTime / 1_000_000);
    }
}
