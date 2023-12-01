package repository;

import model.ProgramState;
import model.adts.IMyList;
import model.adts.IMyPair;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.statements.IStatement;

public interface IRepository {
    void addProgram(IStatement startingStatement);

    int size();

    IMyList<ProgramState> getAll();

    ProgramState getProgramAt(int index);

    void logProgramState(int programIndex) throws ReadWriteException;

    void resetProgram(int index);
}
