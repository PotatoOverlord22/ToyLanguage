package model;

import model.adts.*;
import model.statements.IStatement;
import model.values.IValue;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class ProgramState {
    private IMyStack<IStatement> executionStack;
    private IMyDictionary<String, IValue> symbolTable;
    private IMyList<IValue> output;

    private IMyDictionary<String, BufferedReader> fileTable;

    //
    private IMyHeap heap;

    private

    IStatement originalState;

    public ProgramState(IMyStack<IStatement> executionStack, IMyDictionary<String, IValue> symbolTable, IMyList<IValue> out,
                        IMyDictionary<String, BufferedReader> fileTable, IStatement startingStatement, IMyHeap heap) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = out;
        this.fileTable = fileTable;
        this.heap = heap;
        originalState = startingStatement.deepCopy();
        // This is the first statement on the stack
        executionStack.push(startingStatement);
    }

    public void resetProgram() {
        executionStack = new MyStack<>();
        symbolTable = new MyDictionary<>();
        output = new MyList<>();
        fileTable = new MyDictionary<>();
        heap = new MyHeap();

        IStatement startingStatement = originalState.deepCopy();
        executionStack.push(startingStatement);
    }

    public IMyDictionary<String, IValue> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(IMyDictionary<String, IValue> symbolTable) {
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

    @Override
    public String toString() {
        String fileTableString = "";
        // We only need the keys from the fileTable
        for (String reader : fileTable.keys()) {
            fileTableString += reader.toString() + ", ";
        }
        return "ProgramState:\n" + "Execution Stack: \n" + executionStack.toString() + "SymbolTable: \n" + symbolTable.toString()
                + "Output: \n" + output.toString() + "FileTable: \n" + fileTableString + "Heap: \n" + heap.toString();
    }
}
