package model;

import exceptions.ModelException;

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
