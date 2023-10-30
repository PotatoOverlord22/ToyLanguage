package model;

import java.util.Stack;

public class MyStack<T> implements IMyStack<T> {
    private Stack<T> stack = new Stack<T>();

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T newItem) {
        stack.push(newItem);
    }

    @Override
    public T peek() {
        return stack.peek();
    }

    @Override
    public String toString() {
        String result = "";
        for (T elem :
                stack) {
            result += elem.toString() + " ";
        }
        return result;
    }
}
