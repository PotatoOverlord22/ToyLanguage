package model.types;

import model.values.BoolValue;
import model.values.IValue;

public class BoolType implements IType{
    @Override
    public IValue getDefaultValue() {
        return new BoolValue(false);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BoolType;
    }

    @Override
    public void deepCopy() {

    }
}
