package model.adts;

import model.values.IValue;

import java.util.HashMap;
import java.util.Map;

public interface IMyHeap {
    IValue get(int location);

    boolean isEmpty();

    // Returns old value
    IValue put(IValue value);

    // Returns the old value
    IValue changeValue(int location, IValue newValue);

    // Return the removed value
    IValue remove(int location);

    int lastGeneratedAddress();

    void clear();

    Map<Integer, IValue> getContent();
}
