package model.adts;

import model.values.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements IMyHeap{
    /*
        NOTE: This probably could be implemented a bit better with a list of empty locations in the heap such that
                we don't need to iterate over all the occupied locations to find an empty one
     */
    private Map<Integer, IValue> heap;
    // Keep track of the free location in our heap
    private int freeLocationIndex;

    public MyHeap(){
        heap = new HashMap<>();
        freeLocationIndex = 1;
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public IValue changeValue(int location, IValue newValue) {
        return heap.put(location, newValue);
    }

    @Override
    public IValue get(int location) {
        return heap.get(location);
    }

    @Override
    public IValue put(IValue value) {
        return heap.put(findFreeHeapLocation(), value);
    }

    @Override
    public IValue remove(int location) {
        // NOTE: This moves the index to the new empty spot
        freeLocationIndex = location;
        return heap.remove(location);
    }

    private int findFreeHeapLocation(){
        /*
            NOTE: remove also moves the index to the new free location
         */
        // Check if the current index is occupied, if not then it's a free spot
        if (heap.get(freeLocationIndex) == null)
            return freeLocationIndex;
        // Otherwise iterate over all the occupied spaces until we find a free space
        while (heap.get(freeLocationIndex) != null)
            freeLocationIndex++;
        return freeLocationIndex;
    }

    @Override
    public int lastGeneratedAddress() {
        return freeLocationIndex;
    }

    @Override
    public void clear() {
        heap.clear();
    }

    @Override
    public String toString() {
        String result = "";
        for (int key : heap.keySet())
            result += key + " => " + heap.get(key).toString() + '\n';
        return result;
    }

    public Map<Integer, IValue> getContent() {
        return heap;
    }

    @Override
    public void setContent(Map<Integer, IValue> newContent) {
        heap = newContent;
    }
}
