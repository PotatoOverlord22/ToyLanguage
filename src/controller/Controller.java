package controller;

import model.ProgramState;
import model.adts.IMyList;
import model.adts.IMyPair;
import model.adts.IMyStack;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.statements.IStatement;
import repository.IRepository;

public class Controller implements IController{
    private final IRepository repository;

    private int programIndex;
    public Controller (IRepository repository, int programToRunIndex){
        this.repository = repository;
        this.programIndex = programToRunIndex;
    }
    @Override
    public ProgramState oneStep(ProgramState currentState) throws ExecutionException, EvaluationException, ReadWriteException {
        /*
            Function executes one step of the interpreter then stops and returns the new state of the program (or an exception)
            Executing one step means executing the statement at the top of the execution stack.
         */
        IMyStack<IStatement> exeStack = currentState.getExecutionStack();
        if (exeStack.isEmpty())
            throw new ExecutionException("Stack is empty!");
        return exeStack.pop().execute(currentState);
    }

    @Override
    public void allSteps(boolean showOnlyResult) throws ExecutionException, EvaluationException, ReadWriteException {
        /*
            Function executes the whole program and then stops, meaning this function does not exit until the execution stack is empty
         */
        ProgramState program = repository.getProgramAt(programIndex);
        if(!showOnlyResult)
            repository.logProgramState(programIndex);
        IMyStack<IStatement> executionStack = program.getExecutionStack();

        while (!executionStack.isEmpty()){
            oneStep(program);
            if (!showOnlyResult)
                repository.logProgramState(programIndex);
        }
        if (showOnlyResult)
            repository.logProgramState(programIndex);
    }

    public void runAllStepsOnProgram(int programIndex) throws ExecutionException, EvaluationException, ReadWriteException{
        this.programIndex = programIndex;
        allSteps(true);
    }

    @Override
    public int repositorySize() {
        return repository.size();
    }

    @Override
    public IMyList<ProgramState> getAll() {
        return repository.getAll();
    }

    @Override
    public void resetProgram() {
        repository.resetProgram(programIndex);
    }

    public void setProgramIndex(int programIndex) {
        this.programIndex = programIndex;
    }

    public int getProgramIndex() {
        return programIndex;
    }
}
