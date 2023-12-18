package model.statements;

import model.ProgramState;
import model.adts.IMyDictionary;
import model.adts.SymbolTable;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.types.IType;
import model.values.IValue;

public class VarDeclaration implements IStatement{
    private final String varId;
    private final IType varType;

    public VarDeclaration(IType varType, String varId){
        this.varType = varType;
        this.varId = varId;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException {
        SymbolTable symbolTable = state.getSymbolTable();
        // Check if variable is already declared
        if (symbolTable.get(varId) != null)
            throw new ExecutionException("variable " + varId + " already declared");
        symbolTable.put(varId, varType.getDefaultValue());
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new VarDeclaration(varType, varId);
    }

    @Override
    public String toString() {
        return varType.toString() + " " + varId;
    }
}
