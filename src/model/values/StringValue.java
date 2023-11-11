package model.values;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue{
    private String value;

    public StringValue(){
        value = "";
    }
    public StringValue(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(value);
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StringValue))
            return false;
        return value.equals(((StringValue) other).getValue());
    }

    @Override
    public String toString() {
        return value;
    }
}
