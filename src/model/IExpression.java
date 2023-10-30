package model;

import exceptions.ModelException;

public interface IExpression {
    IValue evaluate(IMyDictionary<String, IValue> table) throws ModelException;
}
