package model.ADT;

import model.exceptions.ADTException;

public interface IStack<T> {
    public T pop() throws ADTException;
    public void push(T value);

    boolean isEmpty();
}