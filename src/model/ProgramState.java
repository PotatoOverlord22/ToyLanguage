package model;

import model.adts.*;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.statements.IStatement;
import model.values.IValue;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class ProgramState {
    private static int idCounter = 0; // all PrgStates will see this
    private final int id; // Personal id for each ProgramState
    private static final Object idLock = new Object(); // Lock used for synchronizing id assignment
    private IMyStack<IStatement> executionStack;
    private SymbolTable symbolTable;
    private IMyList<IValue> output;

    private IMyDictionary<String, BufferedReader> fileTable;

    //
    private IMyHeap heap;

    private

    IStatement originalState;

    public ProgramState(IMyStack<IStatement> executionStack, SymbolTable symbolTable, IMyList<IValue> out,
                        IMyDictionary<String, BufferedReader> fileTable, IStatement startingStatement, IMyHeap heap) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = out;
        this.fileTable = fileTable;
        this.heap = heap;
        originalState = startingStatement.deepCopy();
        // This is the first statement on the stack
        executionStack.push(startingStatement);
        this.id = getNextId();
    }

    public static int getNextId() {
        synchronized (idLock) {
            return idCounter++;
        }
    }

    public void resetProgram() {
        executionStack = new MyStack<>();
        symbolTable = new SymbolTable();
        output = new MyList<>();
        fileTable = new MyDictionary<>();
        heap = new MyHeap();

        IStatement startingStatement = originalState.deepCopy();
        executionStack.push(startingStatement);
    }

    public ProgramState oneStep() throws ExecutionException, EvaluationException, ReadWriteException {
        // Check if the program is completed already or not
        if (executionStack.isEmpty())
            throw new ExecutionException("Program execution stack is empty already");
        // run the current statement that is on top of the stack
        return executionStack.pop().execute(this);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public IMyList<IValue> getOutput() {
        return output;
    }

    public void setOutput(IMyList<IValue> output) {
        this.output = output;
    }

    public IMyStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(IMyStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public IStatement getOriginalState() {
        return originalState;
    }

    public void setFileTable(IMyDictionary<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public IMyDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IMyHeap getHeap() {
        return heap;
    }

    public void setHeap(IMyHeap heap) {
        this.heap = heap;
    }

    public boolean isCompleted() {
        return executionStack.isEmpty();
    }

    @Override
    public String toString() {
        String fileTableString = "";
        // We only need the keys from the fileTable
        for (String reader : fileTable.keys()) {
            fileTableString += reader.toString() + ", ";
        }
        return "ProgramState " + id + " :\n" + "Execution Stack: \n" + executionStack.toString() + "SymbolTable: \n" + symbolTable.toString()
                + "Output: \n" + output.toString() + "FileTable: \n" + fileTableString + "Heap: \n" + heap.toString();
    }
}
