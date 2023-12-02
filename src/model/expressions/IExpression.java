package model.expressions;

import model.adts.IMyDictionary;
import model.adts.IMyHeap;
import model.exceptions.EvaluationException;
import model.values.IValue;

public interface IExpression {
    IValue evaluate(IMyDictionary<String, IValue> table, IMyHeap heap) throws EvaluationException;

    IExpression deepCopy();
}
