package repository;

import model.ProgramState;

import java.util.List;

public interface IRepository {
    int size();

    List<ProgramState> getProgramList();

    void setProgramList(List<ProgramState> newProgramList);

    void logProgramState(ProgramState programToLog);

    void resetAllPrograms();

    void logErrorMessage(String errorMsg);
    
}
