package Gui.model.ADT;

import java.util.HashMap;

public interface ILock {
    HashMap<Integer, Integer> getLock();
    void setLock(HashMap<Integer, Integer> lock);
    int getFreeAddress();
    void put(int key,int value);
    boolean containsKey(int key);
    int get(int key);
    void update(int key,int value);
    String toString();
}
