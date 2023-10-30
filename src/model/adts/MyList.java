package model.adts;

import model.adts.IMyList;

import java.util.ArrayList;

public class MyList<T> implements IMyList<T> {
    private ArrayList<T> list = new ArrayList<T>();

    @Override
    public void add(T newItem) {
        list.add(newItem);
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public void set(int index, T newItem) {
        list.set(index, newItem);
    }

    @Override
    public void remove(int index) {
        list.remove(index);
    }

    @Override
    public void clear() {
        list.clear();
    }
}
