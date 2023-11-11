package model.adts;

import model.adts.IMyDictionary;

import java.util.Dictionary;
import java.util.Hashtable;

public class MyDictionary<T, U> implements IMyDictionary<T, U> {
    private Hashtable<T, U> dict = new Hashtable<>();
    @Override
    public U get(T key) {
        return dict.get(key);
    }

    @Override
    public void put(T key, U value) {
        dict.put(key, value);
    }

    @Override
    public U remove(T key) {
        return dict.remove(key);
    }

    @Override
    public String toString() {
        return dict.toString();
    }
}
