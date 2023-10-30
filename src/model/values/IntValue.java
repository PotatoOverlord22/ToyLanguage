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

    public int getValue(){
        return value;
    }
}
