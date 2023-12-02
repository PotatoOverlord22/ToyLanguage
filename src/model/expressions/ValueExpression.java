package model.expressions;

import model.adts.IMyDictionary;
import model.adts.IMyHeap;
import model.exceptions.EvaluationException;
import model.values.IValue;

public class ValueExpression implements IExpression{
    IValue value;

    public ValueExpression(IValue value){
        this.value = value;
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(value.deepCopy());
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> table, IMyHeap heap) throws EvaluationException {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
