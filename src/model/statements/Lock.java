package model.statements;

import model.ProgramState;
import model.adts.ILockTable;
import model.adts.IMyDictionary;
import model.adts.SymbolTable;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.concurrent.locks.ReentrantLock;

public class Lock implements IStatement{
    private final String varName;
    private static final ReentrantLock lock = new ReentrantLock();

    public Lock(String varName){
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException, ReadWriteException {
        lock.lock();
        ILockTable lockTable = state.getLockTable();
        IValue foundIndexValue = state.getSymbolTable().get(varName);
        if (foundIndexValue == null)
            throw new ExecutionException("Variable " + varName + " has not been declared");
        if (!foundIndexValue.getType().equals(new IntType()))
            throw new ExecutionException("Variable" + varName + " is not of int type");
        int foundIndex = ((IntValue) foundIndexValue).getValue();
        if (lockTable.get(foundIndex) == null)
            throw new ExecutionException(foundIndex + " is not an index in the lock table");
        if (lockTable.get(foundIndex) == -1)
            lockTable.changeValue(foundIndex, state.getId());
        else if (lockTable.get(foundIndex) != state.getId())
            state.getExecutionStack().push(this);
        lock.unlock();
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new Lock(varName);
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnvironment) throws EvaluationException {
        IType variableType = typeEnvironment.get(varName);
        if (variableType == null)
            throw new EvaluationException("Variable " + varName + " does not exist");
        if (!variableType.equals(new IntType()))
            throw new EvaluationException("Variable " + varName + " is not of int type");
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "lock(" + varName + ")";
    }
}
