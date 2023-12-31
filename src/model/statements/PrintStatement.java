package model.statements;

import model.ProgramState;
import model.adts.IMyDictionary;
import model.adts.IMyList;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.expressions.IExpression;
import model.types.IType;
import model.values.IValue;

public class PrintStatement implements IStatement {
    private final IExpression expression;


    public PrintStatement(IExpression expressionToPrint) {
        expression = expressionToPrint;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException {
        IMyList<IValue> out = state.getOutput();
        out.add(expression.evaluate(state.getSymbolTable(), state.getHeap()));
        return null;
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnvironment) throws EvaluationException {
        expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
