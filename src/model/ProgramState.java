package model;

import model.adts.IMyDictionary;
import model.adts.IMyList;
import model.adts.IMyStack;
import model.statements.IStatement;
import model.values.IValue;

public class ProgramState {
    private IMyStack<IStatement> executionStack;
    private IMyDictionary<String, IValue> symbolTable;
    private IMyList<IValue> output;

    IStatement originalState;

    public ProgramState(IMyStack<IStatement> executionStack, IMyDictionary<String, IValue> symbolTable,
                        IMyList<IValue> out, IStatement startingStatement) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = out;
        originalState = startingStatement.deepCopy();
        // This is the first statement on the stack
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

    @Override
    public String toString() {
        return "ProgramState{" +
                "\nexecutionStack= " + executionStack.toString() +
                "\nsymbolTable= " + symbolTable.toString() +
                "\noutput= " + output.toString() +
                "\noriginalState= " + originalState.toString() +
                "\n}";
    }
}
