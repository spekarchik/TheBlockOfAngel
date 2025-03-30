package com.pekar.angelblock.utils;

@FunctionalInterface
public interface TriFunction<T1, T2, T3, R>
{
    R apply(T1 p1, T2 p2, T3 p3);
}
