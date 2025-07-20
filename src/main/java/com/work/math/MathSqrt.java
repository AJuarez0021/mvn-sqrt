package com.work.math;

/**
 *
 * @author ajuar
 */
public class MathSqrt implements SqrtStrategy {

    /**
     * Método más óptimo para calcular la raíz cuadrada usando Math.sqrt() Esta
     * es la implementación más eficiente para la mayoría de casos de uso
     *
     * @param x
     * @return
     */
    @Override
    public double sqrt(double x) {
        if (x < 0) {
            throw new IllegalArgumentException("No se puede calcular la raíz cuadrada de un número negativo");
        }
        return Math.sqrt(x);
    }
}
