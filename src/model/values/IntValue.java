package model.values;

import model.types.IType;
import model.types.IntType;

public class IntValue implements IValue{
    private int value;
    public IntValue(int value){
        this.value = value;
    }
    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public int getValue(){
        return value;
    }


}
