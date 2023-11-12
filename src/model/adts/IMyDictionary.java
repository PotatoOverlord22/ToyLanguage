package model.adts;

import java.util.Collection;

public interface IMyDictionary<T, U> {
    void put(T key, U value);

    U get(T key);

    U remove(T key);

    Collection<U> values();
}
