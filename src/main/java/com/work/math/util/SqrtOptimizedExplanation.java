package com.work.math.util;

import com.work.math.SqrtCalculator;

/**
 *
 * @author ajuar
 */
public final class SqrtOptimizedExplanation {

    private SqrtOptimizedExplanation() {

    }

    public static void explanation() {
        ConsoleHelper.println("=== EXPLICACIÓN DETALLADA DEL MÉTODO sqrtOptimized ===");
        ConsoleHelper.println();

        explainIEEE754ForDouble();
        explainBitManipulation();
        explainNewtonRaphson();
        demonstrateWithExample();
        compareAccuracy();
    }

    private static void explainIEEE754ForDouble() {
        ConsoleHelper.println("1. REPRESENTACIÓN IEEE 754 PARA DOUBLE (64 bits):");
        ConsoleHelper.println("=".repeat(55));

        ConsoleHelper.println("┌─┬─────────────┬──────────────────────────────────────────────────────────────────┐");
        ConsoleHelper.println("│S│EEEEEEEEEEE│MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM│");
        ConsoleHelper.println("└─┴─────────────┴──────────────────────────────────────────────────────────────────┘");
        ConsoleHelper.println(" │      │                              │");
        ConsoleHelper.println(" │      │                              └─ 52 bits de mantisa");
        ConsoleHelper.println(" │      └─ 11 bits de exponente (sesgado +1023)");
        ConsoleHelper.println(" └─ 1 bit de signo");
        ConsoleHelper.println();

        ConsoleHelper.println("Valor = (-1)^S × (1 + M/2^52) × 2^(E-1023)");
        ConsoleHelper.println();

        // Ejemplo con números específicos
        double[] examples = {1.0, 2.0, 4.0, 16.0, 100.0};
        for (double d : examples) {
            long bits = Double.doubleToLongBits(d);
            long sign = (bits >>> 63) & 0x1L;
            long exponent = (bits >>> 52) & 0x7FFL;
            long mantissa = bits & 0xFFFFFFFFFFFFFL;

            ConsoleHelper.printf("%.1f -> S:%d E:%d(real:%d) M:0x%013X%n",
                    d, sign, exponent, exponent - 1023, mantissa);
        }
        ConsoleHelper.println();
    }

    private static void explainBitManipulation() {
        ConsoleHelper.println("2. MANIPULACIÓN DE BITS PARA APROXIMACIÓN:");
        ConsoleHelper.println("=".repeat(45));

        ConsoleHelper.println("La transformación: bits = (bits >> 1) + (1023L << 51)");
        ConsoleHelper.println();

        ConsoleHelper.println("¿Por qué funciona esta transformación?");
        ConsoleHelper.println();

        ConsoleHelper.println("Matemática detrás:");
        ConsoleHelper.println("- Si log₂(x) ≈ (bits_como_entero - BIAS)/SCALE");
        ConsoleHelper.println("- Entonces log₂(√x) = ½ × log₂(x)");
        ConsoleHelper.println("- Por lo tanto: bits_sqrt ≈ ½ × bits_x + ajuste");
        ConsoleHelper.println();

        ConsoleHelper.println("Descomponiendo la transformación:");
        ConsoleHelper.println("1. (bits >> 1): Divide el valor por 2 aproximadamente");
        ConsoleHelper.println("2. (1023L << 51): Añade el sesgo necesario para double");
        ConsoleHelper.println();

        // Demostración con ejemplo
        double x = 100.0;
        long originalBits = Double.doubleToLongBits(x);
        long transformedBits = (originalBits >> 1) + (1023L << 51);
        double approximation = Double.longBitsToDouble(transformedBits);

        ConsoleHelper.printf("Ejemplo con x = %.1f:%n", x);
        ConsoleHelper.printf("  Bits originales:      0x%016X%n", originalBits);
        ConsoleHelper.printf("  Bits >> 1:           0x%016X%n", originalBits >> 1);
        ConsoleHelper.printf("  1023L << 51:         0x%016X%n", 1023L << 51);
        ConsoleHelper.printf("  Bits transformados:  0x%016X%n", transformedBits);
        ConsoleHelper.printf("  Aproximación:        %.6f%n", approximation);
        ConsoleHelper.printf("  Valor real √%.1f:     %.6f%n", x, Math.sqrt(x));
        ConsoleHelper.printf("  Error inicial:       %.6f%n", Math.abs(approximation - Math.sqrt(x)));
        ConsoleHelper.println();
    }

    private static void explainNewtonRaphson() {
        ConsoleHelper.println("3. REFINAMIENTO CON NEWTON-RAPHSON:");
        ConsoleHelper.println("=".repeat(40));

        ConsoleHelper.println("Objetivo: Encontrar la raíz de f(y) = y² - x = 0");
        ConsoleHelper.println("Donde y = √x");
        ConsoleHelper.println();

        ConsoleHelper.println("Fórmula de Newton-Raphson:");
        ConsoleHelper.println("y_{n+1} = y_n - f(y_n)/f'(y_n)");
        ConsoleHelper.println();

        ConsoleHelper.println("Para f(y) = y² - x:");
        ConsoleHelper.println("- f'(y) = 2y");
        ConsoleHelper.println("- y_{n+1} = y_n - (y_n² - x)/(2y_n)");
        ConsoleHelper.println("- y_{n+1} = y_n - y_n/2 + x/(2y_n)");
        ConsoleHelper.println("- y_{n+1} = y_n/2 + x/(2y_n)");
        ConsoleHelper.println("- y_{n+1} = (y_n + x/y_n)/2");
        ConsoleHelper.println();

        ConsoleHelper.println("Esta es la fórmula clásica del método babilónico!");
        ConsoleHelper.println();

        // Demostración de convergencia
        double x = 50.0;
        double guess = 7.0; // Aproximación inicial pobre a propósito
        double actual = Math.sqrt(x);

        ConsoleHelper.printf("Demostración de convergencia para √%.1f = %.6f:%n", x, actual);
        ConsoleHelper.printf("Aproximación inicial pobre: %.6f (error: %.6f)%n",
                guess, Math.abs(guess - actual));

        for (int i = 0; i < 5; i++) {
            guess = (guess + x / guess) / 2.0;
            double error = Math.abs(guess - actual);
            ConsoleHelper.printf("Iteración %d: %.10f (error: %.10f)%n", i + 1, guess, error);
        }
        ConsoleHelper.println();
    }

    private static void demonstrateWithExample() {
        ConsoleHelper.println("4. DEMOSTRACIÓN COMPLETA CON EJEMPLO:");
        ConsoleHelper.println("=".repeat(45));

        double testValue = 1000.0;
        ConsoleHelper.printf("Calculando √%.1f:%n", testValue);
        ConsoleHelper.println();

        double result = SqrtCalculator.sqrt(SqrtCalculator.Algorithms.OPTIMIZED, testValue);
        double expected = Math.sqrt(testValue);

        ConsoleHelper.printf("Resultado final: %.10f%n", result);
        ConsoleHelper.printf("Math.sqrt():     %.10f%n", expected);
        ConsoleHelper.printf("Error final:     %.10f%n", Math.abs(result - expected));
        ConsoleHelper.println();
    }

    private static void compareAccuracy() {
        ConsoleHelper.println("5. COMPARACIÓN DE PRECISIÓN:");
        ConsoleHelper.println("=".repeat(35));

        double[] testValues = {0.25, 1.0, 4.0, 16.0, 100.0, 1000.0, 1000000.0};

        ConsoleHelper.println("Valor      | Aproximación | Newton 1     | Newton 2     | Newton 3     | Math.sqrt()");
        ConsoleHelper.println("-----------|--------------|--------------|--------------|--------------|------------");

        for (double x : testValues) {
            if (x == 0) {
                continue;
            }

            // Aproximación inicial
            long bits = Double.doubleToLongBits(x);
            bits = (bits >> 1) + (1023L << 51);
            double approx = Double.longBitsToDouble(bits);

            // Iteraciones de Newton-Raphson
            double guess1 = (approx + x / approx) / 2.0;
            double guess2 = (guess1 + x / guess1) / 2.0;
            double guess3 = (guess2 + x / guess2) / 2.0;

            double expected = Math.sqrt(x);

            ConsoleHelper.printf("%-10.1f | %12.6f | %12.6f | %12.6f | %12.6f | %12.6f%n",
                    x, approx, guess1, guess2, guess3, expected);
        }
        ConsoleHelper.println();

        ConsoleHelper.println("OBSERVACIONES:");
        ConsoleHelper.println("- La aproximación inicial es sorprendentemente buena");
        ConsoleHelper.println("- Newton-Raphson converge muy rápidamente");
        ConsoleHelper.println("- 2-3 iteraciones suelen ser suficientes para alta precisión");
        ConsoleHelper.println("- La manipulación de bits es la clave del rendimiento");
    }
}
