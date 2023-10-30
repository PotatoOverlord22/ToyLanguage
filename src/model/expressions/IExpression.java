package model.expressions;

import exceptions.ModelException;
import model.adts.IMyDictionary;
import model.values.IValue;

public interface IExpression {
    IValue evaluate(IMyDictionary<String, IValue> table) throws ModelException;
}
