package Gui.model.ADT;

import Gui.model.exceptions.ADTException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyLatchTable<K,V> implements ILatchTable<K,V>{
    private Map<K,V> latch;
    private int address = 1;

    public MyLatchTable() {
        this.latch = new HashMap<>();
    }

    @Override
    public void put(K key, V value) {
        latch.put(key, value);
    }

    @Override
    public V lookup(K key) {
        return latch.get(key);
    }

    @Override
    public void update(K key, V value) {
        latch.put(key, value);
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Collection<K> keys() {
        return null;
    }

    @Override
    public void remove(K fd) throws ADTException {
        if (!latch.containsKey(fd)) {
            throw new ADTException("Key not exists in the map");
        }
        latch.remove(fd);
    }

    @Override
    public boolean isDefined(K key) {
        return latch.containsKey(key);
    }

    @Override
    public int getLocation() {
        address++;
        return address;
    }

    @Override
    public ILatchTable<K, V> clone() {
        ILatchTable<K,V> copy = new MyLatchTable<>();
        for (K k : latch.keySet()) {
            copy.update(k, latch.get(k));
        }
        return copy;
    }

    @Override
    public Map<K, V> getContent() {
        return latch;
    }

    @Override
    public String toString(){
        StringBuilder content = new StringBuilder();
        for (Map.Entry<K, V> el : latch.entrySet()) {
            content.append(el.getKey()).append("->").append(el.getValue()).append(", ");
        }
        return content.toString();
    }
}
