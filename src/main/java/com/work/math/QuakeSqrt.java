package com.work.math;

/**
 *
 * @author ajuar
 */
public class QuakeSqrt implements SqrtStrategy {

    /**
     * Método usando aproximación rápida de Quake III (solo para demostración)
     * NO es más preciso ni más rápido que Math.sqrt() en Java moderno.
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
        if (x == 1) {
            return 1;
        }

        // Algoritmo original de Quake III para calcular 1/√x
        float xf = (float) x;
        float xhalf = 0.5f * xf;
        int i = Float.floatToIntBits(xf);
        i = 0x5f3759df - (i >> 1); // Constante mágica
        float y = Float.intBitsToFloat(i);

        // Una iteración para el inverso de la raíz
        y = y * (1.5f - xhalf * y * y);

        // Obtener aproximación inicial para √x
        double guess = x * y;

        // Refinar con Newton-Raphson directo para √x
        for (int j = 0; j < 3; j++) {
            guess = 0.5 * (guess + x / guess);
        }

        return guess;
    }

}
