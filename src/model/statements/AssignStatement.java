package model.statements;

import model.ProgramState;
import model.adts.IMyDictionary;
import model.adts.SymbolTable;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.expressions.IExpression;
import model.types.IType;
import model.values.IValue;

public class AssignStatement implements IStatement {
    private final String varId;
    private final IExpression assignedExpression;

    public AssignStatement(String varId, IExpression expressionToBeAssigned) {
        this.varId = varId;
        assignedExpression = expressionToBeAssigned;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException {
        SymbolTable symbolTable = state.getSymbolTable();
        // Check if the variable is in the symbol table
        if (symbolTable.get(varId) == null)
            throw new ExecutionException("variable " + varId + " was not declared");
        IValue value = assignedExpression.evaluate(symbolTable, state.getHeap());
        IType varType = symbolTable.get(varId).getType();
        // Check if variable type and the value of the expression match (can't assign to an int var type a boolean value
        if (!value.getType().equals(varType))
            throw new ExecutionException("mismatch between variable type " + varId + " and expression type " + assignedExpression.toString());
        // We found the variable in the table, we know the type match, now update the variable
        symbolTable.put(varId, value);
        return null;
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnvironment) throws EvaluationException {
        // Get the types of both the variable and the expression
        IType variableType = typeEnvironment.get(varId);
        if (variableType == null)
            throw new EvaluationException("Variable " + varId + " does not exist");
        IType expressionType = assignedExpression.typeCheck(typeEnvironment);
        // They should have the same type
        if (!(variableType.equals(expressionType)))
            throw new EvaluationException("Assignment between variable " + varId + " and expression " + assignedExpression + " is not compatible because of different types");
        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(varId, assignedExpression.deepCopy());
    }

    @Override
    public String toString() {
        return varId + "=" + assignedExpression.toString();
    }
}
