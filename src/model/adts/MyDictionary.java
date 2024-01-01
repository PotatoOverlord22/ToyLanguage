package model.adts;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyDictionary<T, U> implements IMyDictionary<T, U> {
    private ConcurrentHashMap<T, U> map = new ConcurrentHashMap<>();
    @Override
    public U get(T key) {
        return map.get(key);
    }

    @Override
    public void put(T key, U value) {
        map.put(key, value);
    }

    @Override
    public U remove(T key) {
        return map.remove(key);
    }

    @Override
    public String toString() {
        String result = "";
        for (T key : map.keySet())
            result += key.toString() + " => " + map.get(key).toString() + '\n';
        return result;
    }

    public Collection<U> values(){
        return map.values();
    }

    public Set<T> keys(){
        return map.keySet();
    }

    @Override
    public ConcurrentHashMap<T, U> getContent() {
        return map;
    }

    @Override
    public void setContent(ConcurrentHashMap<T, U> newContent) {
        this.map = newContent;
    }

    @Override
    public IMyDictionary<T, U> copy() {
        IMyDictionary<T, U> copy = new MyDictionary<>();
        copy.setContent(map);
        return copy;
    }
}
