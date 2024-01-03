package model.expressions;

import model.adts.IMyDictionary;
import model.adts.IMyHeap;
import model.adts.SymbolTable;
import model.exceptions.EvaluationException;
import model.types.IType;
import model.values.IValue;

public interface IExpression {
    IValue evaluate(SymbolTable table, IMyHeap heap) throws EvaluationException;

    IType typeCheck(IMyDictionary<String, IType> typeEnvironment) throws EvaluationException;

    IExpression deepCopy();
}
