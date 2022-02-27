package Gui.model.ADT;

import java.util.Map;

public interface ISemaphore<V> {
    String toString();
    Map <Integer, V> getSemaphore();
    void setSemaphore(Map<Integer, V> newSemaphore);
    void put(int location, V value);
    int getLocation();
    boolean isDefined(int location);
}
