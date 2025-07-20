package com.work.math;

/**
 *
 * @author ajuar
 */
public final class SqrtCalculator {

    private static final java.util.Map<Algorithms, SqrtStrategy> strategies
            = new java.util.HashMap<>();

    public enum Algorithms {
        MATH, NEWTON, QUAKE, OPTIMIZED, SEARCH, ROOT
    }

    private SqrtCalculator() {

    }

    static {

        SqrtCalculator.strategies.put(Algorithms.MATH, new MathSqrt());
        SqrtCalculator.strategies.put(Algorithms.NEWTON, new NewtonSqrt());
        SqrtCalculator.strategies.put(Algorithms.QUAKE, new QuakeSqrt());
        SqrtCalculator.strategies.put(Algorithms.OPTIMIZED, new OptimizedSqrt());
        SqrtCalculator.strategies.put(Algorithms.SEARCH, new SearchSqrt());
        SqrtCalculator.strategies.put(Algorithms.ROOT, new RootCalculator());
    }

    public static double sqrt(Algorithms algorithm, double x) {
        return SqrtCalculator.strategies.get(algorithm).sqrt(x);
    }
}
