package model.adts;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

public interface IMyDictionary<T, U> {
    void put(T key, U value);

    U get(T key);

    U remove(T key);

    Collection<U> values();

    Set<T> keys();

    Map<T, U> getContent();
}
