package com.work.math;

/**
 *
 * @author ajuar
 */
public class OptimizedSqrt implements SqrtStrategy {

    /**
     * Método optimizado usando bit manipulation para aproximación inicial Más
     * eficiente que Newton-Raphson puro para ciertos rangos
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
        // Usar representación en bits para una mejor aproximación inicial
        long bits = Double.doubleToLongBits(x);
        bits = (bits >> 1) + (1023L << 51); // Aproximación inicial
        double guess = Double.longBitsToDouble(bits);

        // Refinar con Newton-Raphson (pocas iteraciones necesarias)
        for (int i = 0; i < 3; i++) {
            guess = (guess + x / guess) / 2.0;
        }

        return guess;
    }

}
