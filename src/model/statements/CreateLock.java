package model.statements;

import model.ProgramState;
import model.adts.IMyDictionary;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.concurrent.locks.ReentrantLock;

public class CreateLock implements IStatement{
    private final String varName;

    private static final ReentrantLock lock = new ReentrantLock();

    public CreateLock(String varName){
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException, ReadWriteException {
        lock.lock();
        IValue varValue = state.getSymbolTable().get(varName);
        if (varValue == null)
            throw new EvaluationException("Variable " + varName + " has not been declared");
        if (!varValue.getType().equals(new IntType()))
            throw new EvaluationException("Variable " + varName + " not of int type");
        state.getLockTable().put(-1);
        state.getSymbolTable().put(varName, new IntValue(state.getLockTable().lastGeneratedAddress()));

        lock.unlock();
        return null;
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
    public IStatement deepCopy() {
        return new CreateLock(varName);
    }

    @Override
    public String toString() {
        return "createLock(" + varName + ")";
    }
}
