package model.types;

import model.values.IValue;

public interface IType {
    IValue getDefaultValue();

    IType deepCopy();
}
