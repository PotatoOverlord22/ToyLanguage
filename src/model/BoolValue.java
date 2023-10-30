package model;

public class BoolValue implements IValue{
    boolean value;
    public BoolValue(boolean value){
        this.value = value;
    }
    @Override
    public IType getType() {
        return new BoolType();
    }
}
