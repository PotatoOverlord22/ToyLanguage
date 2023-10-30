package model.values;

import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue{
    boolean value;
    public BoolValue(boolean value){
        this.value = value;
    }
    @Override
    public IType getType() {
        return new BoolType();
    }

    public boolean getValue(){
        return value;
    }
}
