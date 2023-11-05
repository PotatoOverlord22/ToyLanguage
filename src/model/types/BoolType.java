package model.types;

import model.values.BoolValue;
import model.values.IValue;

public class BoolType implements IType {
    private static final boolean DEFAULT_VALUE = false;

    @Override
    public IValue getDefaultValue() {
        return new BoolValue(DEFAULT_VALUE);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BoolType;
    }

    @Override
    public IType deepCopy() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return "bool";
    }
}
