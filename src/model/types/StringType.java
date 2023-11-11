package model.types;

import model.values.IValue;
import model.values.StringValue;

public class StringType implements IType{
    private static final String DEFAULT_VALUE = "";
    @Override
    public IType deepCopy() {
        return new StringType();
    }

    @Override
    public IValue getDefaultValue() {
        return new StringValue(DEFAULT_VALUE);
    }

    @Override
    public String toString() {
        return "String";
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof StringType);
    }
}
