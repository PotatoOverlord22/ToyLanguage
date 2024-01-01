package model.statements;

import model.ProgramState;
import model.adts.IMyDictionary;
import model.adts.IMyStack;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.expressions.IExpression;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class IfStatement implements IStatement{
    private final IExpression ifExpression;
    private final IStatement thenStatement;
    private final IStatement elseStatement;

    public IfStatement(IExpression ifExpression, IStatement thenStatement, IStatement elseStatement){
        this.ifExpression = ifExpression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException {
        IValue condition = ifExpression.evaluate(state.getSymbolTable(), state.getHeap());
        // Check if the condition is boolean in the first place
        if (!condition.getType().equals(new BoolType()))
            throw new ExecutionException("condition in if statement " + ifExpression.toString() + " is not boolean");
        IMyStack<IStatement> exeStack = state.getExecutionStack();
        // If expression is true
        if (condition.equals(new BoolValue(true))){
            exeStack.push(thenStatement);
        }
        else
            exeStack.push(elseStatement);
        return null;
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnvironment) throws EvaluationException {
        IType conditionType = ifExpression.typeCheck(typeEnvironment);
        if (!conditionType.equals(new BoolType()))
            throw new EvaluationException("Condition " + ifExpression + " is not of bool type");
        thenStatement.typeCheck(typeEnvironment.copy());
        elseStatement.typeCheck(typeEnvironment.copy());
        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(ifExpression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public String toString() {
        return "if(" + ifExpression.toString() + ")" + "then " + thenStatement.toString() + " else " + elseStatement.toString();
    }
}
