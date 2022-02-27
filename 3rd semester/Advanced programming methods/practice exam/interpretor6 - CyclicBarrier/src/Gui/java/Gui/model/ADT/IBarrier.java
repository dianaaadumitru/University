package Gui.model.ADT;

import java.util.Map;

public interface IBarrier<V> {
    String toString();
    Map<Integer, V> getBarrier();
    void setBarrier(Map<Integer, V> newBarrier);
    void put(int location, V value);
    int getLocation();
    boolean isDefined(int location);
}
