package Gui.model.ADT;

import java.util.HashMap;
import java.util.Map;

public class MyBarrier<V> implements IBarrier<V>{
    private int address = 1;
    private HashMap<Integer, V> barrier;

    public MyBarrier() {
        this.barrier = new HashMap<>();
    }

    @Override
    public Map<Integer, V> getBarrier() {
        return barrier;
    }

    @Override
    public void setBarrier(Map<Integer, V> newBarrier) {
        barrier =(HashMap<Integer, V>) newBarrier;
    }

    @Override
    public void put(int location, V value) {
        barrier.put(location, value);
    }

    @Override
    public int getLocation() {
        address++;
        return address;
    }

    @Override
    public boolean isDefined(int location) {
        return barrier.containsKey(location);
    }

    @Override
    public String toString(){
        String s = "{";
        for (Integer key: barrier.keySet()){
            s += key + "->" + barrier.get(key).toString() + "; ";
        }
        return s + "}";
    }
}
