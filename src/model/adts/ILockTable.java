package model.adts;

import model.values.IValue;
import model.values.IntValue;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public interface ILockTable {
    Integer get(int location);

    boolean isEmpty();

    // Returns old value
    void put(int value);

    // Returns the old value
    void changeValue(int location, int newValue);

    // Return the removed value
    int remove(int location);

    int lastGeneratedAddress();

    void clear();

    Map<Integer, Integer> getContent();

    void setContent(Map<Integer, Integer> newContent);
}
