package controller;

import model.ProgramState;
import model.adts.IMyList;
import model.adts.IMyPair;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;

public interface IController {
    ProgramState oneStep(ProgramState currentState) throws ExecutionException, EvaluationException;

    void allSteps(ProgramState program) throws ExecutionException, EvaluationException;

    public void runAllStepsOnProgram(int programIndex) throws ExecutionException, EvaluationException;

    int repositorySize();

    IMyList<IMyPair<ProgramState, String>> getAll();

    void setPrintToConsole(boolean newFlagValue);
}
