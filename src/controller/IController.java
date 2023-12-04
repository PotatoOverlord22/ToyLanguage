package controller;

import model.ProgramState;
import model.adts.*;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.values.IValue;

public interface IController {
    ProgramState oneStep(ProgramState currentState) throws ExecutionException, EvaluationException, ReadWriteException;

    void allSteps(boolean showOnlyResult) throws ExecutionException, EvaluationException, ReadWriteException;

    public void runAllStepsOnProgram(int programIndex) throws ExecutionException, EvaluationException, ReadWriteException;

    int repositorySize();

    IMyList<ProgramState> getAll();

    IMyHeap garbageCollector(IMyDictionary<String, IValue> symbolTable, IMyHeap heap);

    void resetProgram();
}
