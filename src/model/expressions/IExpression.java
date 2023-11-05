package model.expressions;

import model.adts.IMyDictionary;
import model.exceptions.EvaluationException;
import model.values.IValue;

public interface IExpression {
    IValue evaluate(IMyDictionary<String, IValue> table) throws EvaluationException;

    IExpression deepCopy();
}
