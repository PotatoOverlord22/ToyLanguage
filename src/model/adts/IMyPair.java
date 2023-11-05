package model.adts;

public interface IMyPair<T, U> {
    T first();

    U second();

    void setFirst(T first);

    void setSecond(U second);
}
