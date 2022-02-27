package Gui.model.ADT;

import Gui.model.exceptions.ADTException;

import java.util.Collection;
import java.util.Map;

public interface ILatchTable<K,V> {
    void put(K key, V value);
    V lookup(K key) ;
    void update(K key, V value);
    Collection<V> values();
    Collection<K> keys();
    void remove(K fd) throws ADTException;
    boolean isDefined(K key);
    int getLocation();
    ILatchTable<K, V> clone();
    Map<K, V> getContent();
}
