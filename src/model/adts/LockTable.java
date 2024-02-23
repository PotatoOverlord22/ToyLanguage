package model.adts;

import model.values.IValue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LockTable implements ILockTable {
    private Map<Integer, Integer> table = new ConcurrentHashMap<>();
    // Keep track of the free location in our heap
    private int freeLocationIndex;

    public LockTable() {
        freeLocationIndex = 1;
    }

    @Override
    public boolean isEmpty() {
        return table.isEmpty();
    }

    @Override
    public void changeValue(int location, int newValue) {
        table.put(location, newValue);
    }

    @Override
    public Integer get(int location) {
        return table.get(location);
    }

    @Override
    public void put(int value) {
        table.put(findFreeHeapLocation(), value);
    }

    @Override
    public int remove(int location) {
        // NOTE: This moves the index to the new empty spot
        freeLocationIndex = location;
        return table.remove(location);
    }

    private int findFreeHeapLocation() {
        /*
            NOTE: remove also moves the index to the new free location
         */
        // Check if the current index is occupied, if not then it's a free spot
        if (table.get(freeLocationIndex) == null)
            return freeLocationIndex;
        // Otherwise iterate over all the occupied spaces until we find a free space
        while (table.get(freeLocationIndex) != null)
            freeLocationIndex++;
        return freeLocationIndex;
    }

    @Override
    public int lastGeneratedAddress() {
        return freeLocationIndex;
    }

    @Override
    public void clear() {
        table.clear();
    }

    @Override
    public String toString() {
        String result = "";
        for (int key : table.keySet())
            result += key + " => " + table.get(key).toString() + '\n';
        return result;
    }

    public Map<Integer, Integer> getContent() {
        return table;
    }

    @Override
    public void setContent(Map<Integer, Integer> newContent) {
        table = newContent;
    }
}
