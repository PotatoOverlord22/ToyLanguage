package model.adts;

public interface IMyDictionary<T, U> {
    void put(T key, U value);

    U get(T key);

    void remove(T key);
}
