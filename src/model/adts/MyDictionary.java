package model.adts;

import model.adts.IMyDictionary;

import java.util.*;

public class MyDictionary<T, U> implements IMyDictionary<T, U> {
    private Map<T, U> dict = new Hashtable<>();
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
        String result = "";
        for (T key : dict.keySet())
            result += key.toString() + " => " + dict.get(key).toString() + '\n';
        return result;
    }

    public Collection<U> values(){
        return dict.values();
    }

    public Set<T> keys(){
        return dict.keySet();
    }

    @Override
    public Map<T, U> getContent() {
        return dict;
    }
}
