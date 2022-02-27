package model.ADT;

import model.exceptions.ADTException;

public interface IList<T> {
    void add(T item);
    void remove(T item) throws ADTException;
    int size();
    T get(int index) throws ADTException;

}