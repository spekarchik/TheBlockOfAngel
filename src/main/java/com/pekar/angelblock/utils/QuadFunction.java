package com.pekar.angelblock.utils;

@FunctionalInterface
public interface QuadFunction<T1, T2, T3, T4, R>
{
    R apply(T1 p1, T2 p2, T3 p3, T4 p4);
}
