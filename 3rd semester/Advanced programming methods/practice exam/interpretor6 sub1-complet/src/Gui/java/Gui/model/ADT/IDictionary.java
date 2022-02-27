package Gui.model.ADT;

import Gui.model.exceptions.ADTException;

import java.util.Map;

public interface IDictionary<K, V> {
    V lookup(K key);
    void update(K key, V value);
    void remove(K key) throws ADTException;
    V search(K key) throws ADTException;
    boolean isDefined(K id);

    void add(K name, V intValue) throws ADTException;
    Map<K, V> getContent();
    IDictionary<K,V> clone();
}