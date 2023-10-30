package model;

import model.adts.IMyDictionary;
import model.adts.IMyList;
import model.adts.IMyStack;
import model.statements.IStatement;
import model.values.IValue;

public class ProgramState {
    private IMyStack<IStatement> executionStack;
    private IMyDictionary<String, IValue> symbolTable;
    private IMyList<IValue> out;

    IStatement originalState;

    public ProgramState(IMyStack<IStatement> executionStack, IMyDictionary<String, IValue> symbolTable,
                        IMyList<IValue> out){
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
    }

    // TODO: make getters and setters for all the fields
    // TODO: override toString() method
}
