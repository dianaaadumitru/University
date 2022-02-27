package Gui.model.ADT;

import java.util.HashMap;
import java.util.Map;

public class MySemaphore<V> implements ISemaphore<V>{
    private int address = 1;
    private HashMap<Integer, V> semaphore;

    public MySemaphore() {
        this.semaphore = new HashMap<>();
    }

    @Override
    public Map<Integer, V> getSemaphore() {
        return semaphore;
    }

    @Override
    public void setSemaphore(Map<Integer, V> newSemaphore) {
        semaphore = (HashMap<Integer, V>) newSemaphore;
    }

    @Override
    public void put(int location, V value) {
        semaphore.put(location, value);
    }

    @Override
    public int getLocation() {
        address++;
        return address;
    }

    @Override
    public boolean isDefined(int location) {
        return semaphore.containsKey(location);
    }

    public String toSting(){
        String s = "{";
        for (Integer key: semaphore.keySet()){
            s += key + "->" + semaphore.get(key).toString() + "; ";
        }
        return s + "}";
    }
}
