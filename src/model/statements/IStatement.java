package model.statements;

import model.ProgramState;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException, ReadWriteException;

    IStatement deepCopy();
}
