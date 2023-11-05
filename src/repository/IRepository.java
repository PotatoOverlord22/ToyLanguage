package repository;

import model.ProgramState;
import model.adts.IMyList;
import model.adts.IMyPair;
import model.statements.IStatement;

public interface IRepository {
    void addProgram(IStatement startingStatement, String programInNaturalLanguage);

    int size();

    IMyList<IMyPair<ProgramState, String>> getAll();

    ProgramState getProgramAt(int index);
}
