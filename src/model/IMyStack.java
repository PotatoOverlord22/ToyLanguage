package model;

public interface IMyStack<T> {
    T pop();
    void push(T newItem);

    T peek();
}
