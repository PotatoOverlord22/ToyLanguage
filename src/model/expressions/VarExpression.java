package model.expressions;

import model.adts.IMyDictionary;
import model.exceptions.EvaluationException;
import model.values.IValue;

public class VarExpression implements IExpression{
    String id;

    public VarExpression(String varId){
        id = varId;
    }
    @Override
    public IValue evaluate(IMyDictionary<String, IValue> table) throws EvaluationException {
        return table.get(id);
    }

    @Override
    public IExpression deepCopy() {
        return new VarExpression(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
