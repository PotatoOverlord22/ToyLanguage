package model.statements;

import model.ProgramState;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.expressions.IExpression;
import model.types.ReferenceType;
import model.values.IValue;
import model.values.ReferenceValue;

public class HeapWrite implements IStatement{
    // TODO: write tests
    private final String varName;
    private final IExpression expression;

    public HeapWrite(String varName, IExpression expression){
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException, ReadWriteException {
        // Check if varName is a defined variable in the symbol table
        IValue varValue = state.getSymbolTable().get(varName);
        if (varValue == null)
            throw new ExecutionException("Variable " + varName + " has not been defined in the symbol table");
        // Check if the variable is of Reference Type
        if (!(varValue.getType() instanceof ReferenceType))
            throw new ExecutionException("Variable " + varName + " is not of reference type");
        // Now we know the value is an instance of Reference value because we checked its type
        // Check if the location of our value associated with varName is a correct one
        if (state.getHeap().get(((ReferenceValue) varValue).getAddress()) == null)
            throw new ExecutionException("Location of value " + varValue.toString() + " is invalid");
        // Now we move on to checking if the expression meets our requirements
        IValue expValue = expression.evaluate(state.getSymbolTable(), state.getHeap());
        // The type of the evaluated expression must have the same type as the type reserved by varName
        if (!expValue.getType().equals(varValue.getType()))
            throw new ExecutionException("Type mismatch between location type of " + varName + " and value " + expValue);
        // If we reached this point, then everything should be okay and ready to update the heap with the new value we got from evaluating our expression
        state.getHeap().changeValue(((ReferenceValue) varValue).getAddress(), expValue);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapWrite(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "heapWrite(" + varName + ", " + expression.toString() + ")";
    }
}
