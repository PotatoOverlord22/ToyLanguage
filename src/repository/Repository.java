package repository;

import model.ProgramState;
import model.adts.*;
import model.exceptions.ReadWriteException;
import model.statements.IStatement;
import model.values.IValue;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> programs;

    private final String logFilePath;

    public Repository(String logFilePath, ProgramState program) {
        this.logFilePath = logFilePath;
        programs = new ArrayList<>();
        programs.add(program);
    }

    @Override
    public int size() {
        return programs.size();
    }

    @Override
    public List<ProgramState> getProgramList() {
        return programs;
    }

    @Override
    public void logProgramState(ProgramState programToLog) {
        try {
            //
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(programToLog.toString() + '\n');
            logFile.close();
        } catch (IOException error) {
            System.out.println(error.getMessage());
        }
    }

    public void logErrorMessage(String errorMsg) {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
            logFile.println(errorMsg + '\n');
            logFile.close();
        } catch (IOException error) {
            System.out.println(error.getMessage());
        }
    }

    @Override
    public void resetAllPrograms() {
        programs.forEach(ProgramState::resetProgram);
    }

    public void setProgramList(List<ProgramState> newProgramList) {
        this.programs = newProgramList;
    }
}
