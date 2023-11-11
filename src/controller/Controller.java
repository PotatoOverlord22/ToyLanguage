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
    public Controller (IRepository repository){
        this.repository = repository;
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
    public void allSteps(ProgramState program) throws ExecutionException, EvaluationException, ReadWriteException {
        /*
            Function executes the whole program and then stops, meaning this function does not exit until the execution stack is empty
         */
        IMyStack<IStatement> executionStack = program.getExecutionStack();

        while (!executionStack.isEmpty()){
            oneStep(program);
            }
    }

    public void runAllStepsOnProgram(int programIndex) throws ExecutionException, EvaluationException, ReadWriteException{
        ProgramState programToRun = repository.getProgramAt(programIndex);
        allSteps(programToRun);
    }

    @Override
    public int repositorySize() {
        return repository.size();
    }

    @Override
    public IMyList<IMyPair<ProgramState, String>> getAll() {
        return repository.getAll();
    }

    @Override
    public void resetProgram(int programIndex) {
        
    }
}
