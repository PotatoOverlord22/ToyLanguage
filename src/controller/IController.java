package controller;

import model.ProgramState;
import model.adts.*;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.values.IValue;

import java.util.List;

public interface IController {
    void allStep();

    void oneStepForAllPrograms(List<ProgramState> programList);

    int repositorySize();

    List<ProgramState> getAll();

    IMyHeap garbageCollector(SymbolTable symbolTable, IMyHeap heap);

    void resetProgram();

    List<ProgramState> removeCompletedPrograms(List<ProgramState> programList);

}
