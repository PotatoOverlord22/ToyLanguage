package controller;

import model.ProgramState;
import model.adts.*;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.statements.IStatement;
import model.values.IValue;
import model.values.ReferenceValue;
import repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            program.setHeap(garbageCollector(program.getSymbolTable(), program.getHeap()));
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
    public IMyHeap garbageCollector(IMyDictionary<String, IValue> symbolTable, IMyHeap heap){
        /*

         */
        // Create a list with correct addresses from the symbol table and the heap
        List<Integer> addressesToKeep = Stream.concat(getAddresses(symbolTable.values()).stream(),
                getAddresses(heap.getContent().values()).stream()).toList();
        IMyHeap newHeap = new MyHeap();
        newHeap.setContent(heap.getContent().entrySet().stream().filter(elem -> addressesToKeep.contains(elem.getKey())).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        return newHeap;
    }

    private List<Integer> getAddresses(Collection<IValue> collection){
        /*
            returns all the addresses from the elements that are Reference Values from a collection
         */
        return collection.stream().filter(elem -> elem instanceof ReferenceValue).map(elem -> ((ReferenceValue) elem).getAddress()).collect(Collectors.toList());
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
