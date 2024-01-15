package model.adts;

import java.util.Stack;

public interface IMyStack<T> {
    T pop();

    void push(T newItem);

    T peek();

    boolean isEmpty();

    Stack<T> getStack();
}
