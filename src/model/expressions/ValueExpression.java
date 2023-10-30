package model.expressions;

import exceptions.ModelException;
import model.adts.IMyDictionary;
import model.values.IValue;

public class ValueExpression implements IExpression{
    IValue value;

    public ValueExpression(IValue value){
        this.value = value;
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> table) throws ModelException {
        return value;
    }
}
