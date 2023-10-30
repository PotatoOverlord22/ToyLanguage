package model.expressions;

import exceptions.ModelException;
import model.adts.IMyDictionary;
import model.values.IValue;

public class VarExpression implements IExpression{
    String id;

    public VarExpression(String varId){
        id = varId;
    }
    @Override
    public IValue evaluate(IMyDictionary<String, IValue> table) throws ModelException {
        return table.get(id);
    }
}
