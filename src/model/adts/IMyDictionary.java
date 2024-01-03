package model.adts;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public interface IMyDictionary<T, U> {
    void put(T key, U value);

    U get(T key);

    U remove(T key);

    Collection<U> values();

    Set<T> keys();

    ConcurrentHashMap<T, U> getContent();

    void setContent(ConcurrentHashMap<T, U> newContent);

    IMyDictionary<T, U> copy();
}
