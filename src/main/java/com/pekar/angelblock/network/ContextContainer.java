package com.pekar.angelblock.network;

public class ContextContainer<T>
{
    private final T context;

    public ContextContainer(T context)
    {
        this.context = context;
    }

    public T getContext()
    {
        return context;
    }
}
