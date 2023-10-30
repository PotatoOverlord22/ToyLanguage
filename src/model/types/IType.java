package model.types;

import model.values.IValue;

public interface IType {
    IValue getDefaultValue();

    void deepCopy();
}
