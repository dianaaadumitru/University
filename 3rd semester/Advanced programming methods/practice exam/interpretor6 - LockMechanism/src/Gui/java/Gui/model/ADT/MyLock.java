package Gui.model.ADT;

import java.util.HashMap;

public class MyLock implements ILock{
    private HashMap<Integer, Integer> lock;
    private int address = 0;

    public MyLock() {
        this.lock = new HashMap<>();
    }

    @Override
    public HashMap<Integer, Integer> getLock() {
        return lock;
    }

    @Override
    public void setLock(HashMap<Integer, Integer> lock) {
        this.lock = lock;
    }

    @Override
    public int getFreeAddress() {
        address++;
        return address;
    }

    @Override
    public void put(int key, int value) {
        lock.put(key, value);
    }

    @Override
    public boolean containsKey(int key) {
        return lock.containsKey(key);
    }

    @Override
    public int get(int key) {
        return lock.get(key);
    }

    @Override
    public void update(int key, int value) {
        lock.replace(key, value);
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for(Integer key: lock.keySet())
            s.append(key.toString()).append("->").append(lock.get(key).toString()).append(" ");
        return s.toString();
    }
}
