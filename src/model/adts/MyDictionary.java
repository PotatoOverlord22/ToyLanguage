package model.adts;

import model.adts.IMyDictionary;

import java.util.Dictionary;
import java.util.Hashtable;

public class MyDictionary<T, U> implements IMyDictionary<T, U> {
    private Dictionary<T, U> dict = new Hashtable<>();
    @Override
    public U get(T key) {
        return dict.get(key);
    }

    @Override
    public void put(T key, U value) {
        dict.put(key, value);
    }

    @Override
    public void remove(T key) {
        dict.remove(key);
    }
}
