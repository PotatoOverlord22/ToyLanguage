package model;

public class IntType implements IType{
    @Override
    public IValue getDefaultValue() {
        return new IntValue(0);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof IntType;
    }

    @Override
    public String toString(){
        return "int";
    }

    @Override
    public void deepCopy() {

    }
}
