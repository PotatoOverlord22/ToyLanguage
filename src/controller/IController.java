package controller;

import model.ProgramState;
import model.adts.IMyList;
import model.adts.IMyPair;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;

public interface IController {
    ProgramState oneStep(ProgramState currentState) throws ExecutionException, EvaluationException, ReadWriteException;

    void allSteps(boolean showOnlyResult) throws ExecutionException, EvaluationException, ReadWriteException;

    public void runAllStepsOnProgram(int programIndex) throws ExecutionException, EvaluationException, ReadWriteException;

    int repositorySize();

    IMyList<ProgramState> getAll();

    void resetProgram();
}
