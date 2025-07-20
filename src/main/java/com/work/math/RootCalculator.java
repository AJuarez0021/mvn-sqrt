package com.work.math;

/**
 *
 * @author ajuar
 */
public class RootCalculator implements SqrtStrategy {

    // Tolerancia para la convergencia
    private static final double TOLERANCE = 1e-10;

    // Número máximo de iteraciones
    private static final int MAX_ITERATIONS = 100;

    /**
     * Calcula la raíz n-ésima de un número usando el método de Newton-Raphson
     *
     * @param number El número del cual calcular la raíz
     * @param n La potencia de la raíz (2 para cuadrada, 3 para cúbica, etc.)
     * @return La raíz n-ésima del número
     */
    public double calculateRoot(double number, int n) {
        // Validaciones
        if (n <= 0) {
            throw new IllegalArgumentException("La potencia debe ser un número entero positivo");
        }

        if (number < 0 && n % 2 == 0) {
            throw new IllegalArgumentException("No se puede calcular raíz par de un número negativo");
        }

        if (number == 0) {
            return 0;
        }

        // Manejar números negativos para raíces impares
        boolean isNegative = number < 0;
        number = Math.abs(number);

        // Estimación inicial
        double x = getInitialGuess(number, n);

        // Iteraciones del método de Newton
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            double xPrevious = x;

            // Fórmula de Newton: x_{k+1} = ((n-1)*x_k + a/x_k^(n-1)) / n
            double xPowerNMinus1 = Math.pow(x, n - 1);
            x = ((n - 1) * x + number / xPowerNMinus1) / n;

            // Verificar convergencia
            if (Math.abs(x - xPrevious) < TOLERANCE) {
                break;
            }
        }

        // Aplicar el signo original si es necesario
        return isNegative ? -x : x;
    }

    /**
     * Calcula una estimación inicial para el método de Newton
     *
     * @param number El número
     * @param n La potencia
     * @return Una estimación inicial razonable
     */
    private static double getInitialGuess(double number, int n) {
        // Para números pequeños, usar 1 como estimación inicial
        if (number <= 1) {
            return 1;
        }

        // Para números grandes, usar una estimación basada en logaritmos
        return Math.exp(Math.log(number) / n);
    }

    @Override
    public double sqrt(double x) {
        return calculateRoot(x, 2);
    }
}
