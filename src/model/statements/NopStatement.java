package model.statements;

import model.ProgramState;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;

public class NopStatement implements IStatement{
    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException {
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new NopStatement();
    }

    @Override
    public String toString() {
        return "skip step";
    }
}
