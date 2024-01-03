package model.types;

import model.values.IValue;
import model.values.ReferenceValue;

public class ReferenceType implements IType {
    private IType inner;

    public ReferenceType(IType referredType) {
        inner = referredType;
    }

    public IType getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ReferenceType)
            return inner.equals(((ReferenceType) other).getInner());
        else
            return false;
    }

    @Override
    public String toString() {
        return "Reference(" + inner.toString() + ")";
    }

    @Override
    public IValue getDefaultValue() {
        // address 0 is considered null in our case
        return new ReferenceValue(0, inner);
    }

    @Override
    public IType deepCopy() {
        return new ReferenceType(inner.deepCopy());
    }
}
