package model.adts;

public interface IMyList<T> {
    void add(T newItem);

    T get(int index);

    void set(int index, T newItem);

    void remove(int index);

    void clear();

    int size();
}