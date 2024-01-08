package model.expressions;

import model.adts.IMyDictionary;
import model.adts.IMyHeap;
import model.adts.SymbolTable;
import model.exceptions.EvaluationException;
import model.types.IType;
import model.values.IValue;

public class VarExpression implements IExpression {
    String id;

    public VarExpression(String varId) {
        id = varId;
    }

    @Override
    public IValue evaluate(SymbolTable table, IMyHeap heap) throws EvaluationException {
        return table.get(id);
    }

    @Override
    public IType typeCheck(IMyDictionary<String, IType> typeEnvironment) throws EvaluationException {
        IType variableType = typeEnvironment.get(id);
        if (variableType == null)
            throw new EvaluationException("Variable " + id + " does not exist");
        return variableType;
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
