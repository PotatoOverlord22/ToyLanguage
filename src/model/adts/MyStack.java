package model.adts;

import model.adts.IMyStack;

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
                stack.reversed()) {
            result += elem.toString() + "\n";
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public Stack<T> getStack() {
        return stack;
    }
}
