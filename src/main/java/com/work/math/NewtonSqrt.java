package com.work.math;

/**
 *
 * @author ajuar
 */
public class NewtonSqrt implements SqrtStrategy {

    // Tolerancia para la convergencia
    private static final double TOLERANCE = 1e-10;

    // Número máximo de iteraciones
    private static final int MAX_ITERATIONS = 100;

    /**
     * Calcula la raíz cuadrada usando el método de Newton
     *
     * @param n número del cual calcular la raíz cuadrada
     * @param precision precisión deseada (por defecto 1e-10)
     * @param maxIterations máximo número de iteraciones (por defecto 100)
     * @return raíz cuadrada aproximada de n
     */
    private static double sqrtNewton(double n, double precision, int maxIterations) {
        if (n < 0) {
            throw new IllegalArgumentException("No se puede calcular la raíz cuadrada de un número negativo");
        }

        if (n == 0) {
            return 0;
        }

        // Aproximación inicial: n/2
        double x = n / 2.0;

        for (int i = 0; i < maxIterations; i++) {
            // Aplicar la fórmula de Newton: x_{k+1} = (x_k + n/x_k) / 2
            double newX = (x + n / x) / 2.0;

            // Verificar si hemos alcanzado la precisión deseada
            if (Math.abs(newX - x) < precision) {
                return newX;
            }

            x = newX;
        }

        return x;
    }

    // Método sobrecargado con valores por defecto
    @Override
    public double sqrt(double n) {
        return sqrtNewton(n, TOLERANCE, MAX_ITERATIONS);
    }

}
