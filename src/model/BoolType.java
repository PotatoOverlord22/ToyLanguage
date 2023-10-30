package model;

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
