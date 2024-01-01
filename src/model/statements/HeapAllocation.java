package model.statements;

import model.ProgramState;
import model.adts.IMyDictionary;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.expressions.IExpression;
import model.types.IType;
import model.types.ReferenceType;
import model.values.IValue;
import model.values.ReferenceValue;

public class HeapAllocation implements IStatement{
    String varName;
    IExpression expression;

    public HeapAllocation(String varName, IExpression expression){
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException, ReadWriteException {
        // Check if the variable varName is in the symbol table
        IValue varValue = state.getSymbolTable().get(varName);
        if(varValue == null)
            throw new ExecutionException("Variable " + varName + " is not defined in the symbol table");
        // Check if the type of the variable is of reference type
        if (!(varValue.getType() instanceof ReferenceType))
            throw new ExecutionException("Variable " + varName + " is not a reference type");
        // Now we can evaluate the expression
        IValue expValue = expression.evaluate(state.getSymbolTable(), state.getHeap());
        // Check if the type of the evaluated expression is the same as the referenced type of varName
        if (!expValue.getType().equals(((ReferenceValue) varValue).getLocationType()))
            throw new ExecutionException("Expression " + expression + " and variable " + varName + " type mismatch");
        // Add the value that we got from evaluating the expression to the heap
        state.getHeap().put(expValue);

        // Finally, attach the type of the evaluated expression to the appropriate variable in the symbol table
        state.getSymbolTable().put(varName, new ReferenceValue(state.getHeap().lastGeneratedAddress(), expValue.getType()));

        return null;
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnvironment) throws EvaluationException {
        IType variableType = typeEnvironment.get(varName);
        IType expressionType = expression.typeCheck(typeEnvironment);
        // Variable type should be a reference type referencing a type that the expression should evaluate to
        if (!variableType.equals(new ReferenceType(expressionType)))
            throw new EvaluationException("Heap allocation: left side " + varName + " and right side " + expression + " have different types");
        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocation(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + expression.toString() + ")";
    }
}
