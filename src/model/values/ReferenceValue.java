package model.values;

import model.types.IType;
import model.types.ReferenceType;

public class ReferenceValue implements IValue {
    private IType locationType;
    private int address;
    public ReferenceValue(int heapAddress, IType type){
        this.address = heapAddress;
        this.locationType = type;
    }

    @Override
    public IValue deepCopy() {
        return new ReferenceValue(address, locationType);
    }

    @Override
    public IType getType() {
        return new ReferenceType(locationType);
    }

    public int getAddress() {
        return address;
    }

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ReferenceValue){
            return address == ((ReferenceValue) other).getAddress() && locationType.equals(((ReferenceValue) other).getLocationType());
        }
        else
            return false;
    }
}
