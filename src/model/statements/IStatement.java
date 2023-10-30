package model.statements;

import model.ProgramState;
import model.exceptions.ExecutionException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws ExecutionException;
}
