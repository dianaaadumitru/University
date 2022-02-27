package model.ADT;

import model.exceptions.ADTException;

public interface IDictionary<K, V> {
    public V lookup(K key);
    public void update(K key, V value);
    public void remove(K key) throws ADTException;

    boolean isDefined(K id);

    void add(K name, V intValue) throws ADTException;
}