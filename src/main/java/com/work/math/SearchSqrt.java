package com.work.math;

/**
 *
 * @author ajuar
 */
public class SearchSqrt implements SqrtStrategy{

    /**
     * Método para números enteros que retorna resultado exacto cuando es
     * posible
     *
     * @param x
     * @return
     */
    private static int sqrtIntegerFloor(int x) {
        if (x < 0) {
            throw new IllegalArgumentException("No se puede calcular la raíz cuadrada de un número negativo");
        }

        if (x <= 1) {
            return x;
        }

        // Usar búsqueda binaria para enteros
        int left = 1;
        int right = x;
        int result = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (mid <= x / mid) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    /**
     * Método optimizado para números enteros que retorna el resultado decimal
     * correcto Usa una aproximación inicial inteligente seguida de
     * Newton-Raphson
     *
     * @param x
     * @return
     */
    @Override
    public double sqrt(double x) {
        if (x < 0) {
            throw new IllegalArgumentException("No se puede calcular la raíz cuadrada de un número negativo");
        }
        if (x == 0) {
            return 0;
        }

        // Aproximación inicial
        // Se realiza cast a int
        double guess = sqrtIntegerFloor((int) x);

        // Si es un cuadrado perfecto, devolver el resultado exacto
        if (guess * guess == x) {
            return guess;
        }

        // Refinar con Newton-Raphson
        double xDouble = x;
        for (int i = 0; i < 5; i++) {
            guess = (guess + xDouble / guess) / 2.0;
        }

        return guess;
    }
}
