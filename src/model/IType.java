package model;

public interface IType {
    IValue getDefaultValue();

    void deepCopy();
}
