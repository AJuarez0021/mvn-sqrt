package com.work.math.util;

/**
 *
 * @author ajuar
 */
public final class QuakeMagicExplanation {

    private QuakeMagicExplanation() {
        
    }
    /**
     * Demostración de cómo funciona la constante mágica 0x5f3759df y de dónde
     * proviene matemáticamente
     */
    public static void explanation() {
        ConsoleHelper.println("=== EXPLICACIÓN DE LA CONSTANTE MÁGICA DE QUAKE III ===");
        ConsoleHelper.println();

        explainFloatRepresentation();
        explainMathematicalDerivation();
        explainConstantCalculation();
        demonstrateAlgorithm();
        explainAlternatives();
    }

    private static void explainFloatRepresentation() {
        ConsoleHelper.println("1. REPRESENTACIÓN IEEE 754 DE NÚMEROS FLOAT:");
        ConsoleHelper.println("=".repeat(50));

        float[] examples = {1.0f, 2.0f, 4.0f, 0.5f};

        for (float f : examples) {
            int bits = Float.floatToIntBits(f);
            ConsoleHelper.printf("Número: %6.1f -> Bits: 0x%08X -> Binario: %s%n",
                    f, bits, Integer.toBinaryString(bits));
        }

        ConsoleHelper.println();
        ConsoleHelper.println("Formato IEEE 754 (32 bits):");
        ConsoleHelper.println("┌─┬────────┬──────────────────────────────┐");
        ConsoleHelper.println("│S│EEEEEEEE│MMMMMMMMMMMMMMMMMMMMMMM│");
        ConsoleHelper.println("└─┴────────┴──────────────────────────────┘");
        ConsoleHelper.println(" │    │              │");
        ConsoleHelper.println(" │    │              └─ 23 bits de mantisa");
        ConsoleHelper.println(" │    └─ 8 bits de exponente (sesgado +127)");
        ConsoleHelper.println(" └─ 1 bit de signo");
        ConsoleHelper.println();

        ConsoleHelper.println("Valor = (-1)^S × (1 + M/2^23) × 2^(E-127)");
        ConsoleHelper.println("Logaritmo: log₂(valor) = (E-127) + log₂(1 + M/2^23)");
        ConsoleHelper.println();
    }

    private static void explainMathematicalDerivation() {
        ConsoleHelper.println("2. DERIVACIÓN MATEMÁTICA:");
        ConsoleHelper.println("=".repeat(30));

        ConsoleHelper.println("Objetivo: Calcular y = 1/√x");
        ConsoleHelper.println();
        ConsoleHelper.println("Paso 1: Tomamos logaritmo");
        ConsoleHelper.println("   log₂(y) = log₂(1/√x) = log₂(x^(-1/2)) = -1/2 × log₂(x)");
        ConsoleHelper.println();

        ConsoleHelper.println("Paso 2: Representación en bits");
        ConsoleHelper.println("   Si I = bits_como_entero(x), entonces:");
        ConsoleHelper.println("   log₂(x) ≈ (I >> 23) - 127 + (I & 0x7FFFFF)/2^23");
        ConsoleHelper.println();

        ConsoleHelper.println("Paso 3: Aproximación lineal");
        ConsoleHelper.println("   Ignorando la parte fraccionaria compleja:");
        ConsoleHelper.println("   log₂(x) ≈ I/2^23 - 127");
        ConsoleHelper.println();

        ConsoleHelper.println("Paso 4: Aplicar la transformación");
        ConsoleHelper.println("   log₂(y) = -1/2 × (I/2^23 - 127)");
        ConsoleHelper.println("   log₂(y) = -I/(2×2^23) + 127/2");
        ConsoleHelper.println("   log₂(y) = -I/2^24 + 63.5");
        ConsoleHelper.println();

        ConsoleHelper.println("Paso 5: Convertir de vuelta a entero");
        ConsoleHelper.println("   J = 2^23 × (log₂(y) + 127)");
        ConsoleHelper.println("   J = 2^23 × (-I/2^24 + 63.5 + 127)");
        ConsoleHelper.println("   J = 2^23 × (-I/2^24 + 190.5)");
        ConsoleHelper.println("   J = -I/2 + 190.5 × 2^23");
        ConsoleHelper.println("   J = (190.5 × 2^23) - I/2");
        ConsoleHelper.println();
    }

    private static void explainConstantCalculation() {
        ConsoleHelper.println("3. CÁLCULO DE LA CONSTANTE:");
        ConsoleHelper.println("=".repeat(30));

        double theoretical = 190.5 * Math.pow(2, 23);
        long theoreticalLong = Math.round(theoretical);

        ConsoleHelper.printf("Valor teórico: 190.5 × 2^23 = %.0f%n", theoretical);
        ConsoleHelper.printf("En hexadecimal: 0x%08X%n", theoreticalLong);
        ConsoleHelper.println();

        long quakeConstant = 0x5f3759dfL;
        ConsoleHelper.printf("Constante de Quake: 0x%08X = %d%n", quakeConstant, quakeConstant);
        ConsoleHelper.printf("Diferencia: %d%n", quakeConstant - theoreticalLong);
        ConsoleHelper.println();

        ConsoleHelper.println("¿Por qué la diferencia?");
        ConsoleHelper.println("- El valor teórico asume aproximaciones");
        ConsoleHelper.println("- Los desarrolladores ajustaron empíricamente para mejor precisión");
        ConsoleHelper.println("- Chris Lomont demostró que 0x5f375a86 es matemáticamente mejor");
        ConsoleHelper.println("- Pero 0x5f3759df funciona muy bien en la práctica");
        ConsoleHelper.println();
    }

    private static void demonstrateAlgorithm() {
        ConsoleHelper.println("4. DEMOSTRACIÓN PASO A PASO:");
        ConsoleHelper.println("=".repeat(35));

        float x = 4.0f;  // Ejemplo: √4 = 2, así que 1/√4 = 0.5
        ConsoleHelper.printf("Calcular 1/√%.1f (resultado esperado: %.3f)%n", x, 1.0f / Math.sqrt(x));
        ConsoleHelper.println();

        // Paso 1: Obtener representación en bits
        int i = Float.floatToIntBits(x);
        ConsoleHelper.printf("Paso 1 - Bits de x: 0x%08X%n", i);

        // Paso 2: Aplicar la transformación mágica
        int magic = 0x5f3759df - (i >> 1);
        ConsoleHelper.printf("Paso 2 - Aplicar magia: 0x%08X - (0x%08X >> 1) = 0x%08X%n",
                0x5f3759df, i, magic);

        // Paso 3: Convertir de vuelta a float
        float y = Float.intBitsToFloat(magic);
        ConsoleHelper.printf("Paso 3 - Resultado inicial: %.6f%n", y);

        // Paso 4: Una iteración de Newton-Raphson
        float xhalf = 0.5f * x;
        y = y * (1.5f - xhalf * y * y);
        ConsoleHelper.printf("Paso 4 - Después de Newton-Raphson: %.6f%n", y);

        ConsoleHelper.printf("Error: %.6f%n", Math.abs(y - 1.0f / Math.sqrt(x)));
        ConsoleHelper.println();
    }

    private static void explainAlternatives() {
        ConsoleHelper.println("5. CONSTANTES ALTERNATIVAS:");
        ConsoleHelper.println("=".repeat(30));

        long[] constants = {
            0x5f3759dfL, // Original de Quake
            0x5f375a86L, // Lomont's better constant
            0x5f37642fL // Otra alternativa
        };

        String[] names = {
            "Quake III Original",
            "Lomont's Improved",
            "Alternativa"
        };

        float testValue = 100.0f;
        float expected = (float) (1.0 / Math.sqrt(testValue));

        ConsoleHelper.printf("Probando con x = %.1f (1/√x esperado = %.6f):%n", testValue, expected);
        ConsoleHelper.println();

        for (int idx = 0; idx < constants.length; idx++) {
            float result = fastInverseSqrt(testValue, (int) constants[idx]);
            float error = Math.abs(result - expected);
            ConsoleHelper.printf("%-18s: %.6f (error: %.8f)%n", names[idx], result, error);
        }
        ConsoleHelper.println();

        ConsoleHelper.println("CONCLUSIÓN:");
        ConsoleHelper.println("La constante 0x5f3759df no es 'perfecta' matemáticamente,");
        ConsoleHelper.println("pero fue elegida por su buen balance entre precisión y rendimiento");
        ConsoleHelper.println("en el hardware de los años 90.");
    }

    private static float fastInverseSqrt(float x, int magicConstant) {
        float xhalf = 0.5f * x;
        int i = Float.floatToIntBits(x);
        i = magicConstant - (i >> 1);
        float y = Float.intBitsToFloat(i);
        y = y * (1.5f - xhalf * y * y);  // Una iteración de Newton-Raphson
        return y;
    }
}
