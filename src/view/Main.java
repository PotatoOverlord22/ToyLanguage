package view;

import model.ProgramState;
import model.adts.*;
import model.statements.IStatement;
import model.values.IValue;

public class Main {
    public static void main(String[] args) {
        // Provide instances of executionStack, symbolTable and out in order to instantiate ProgramState

        IMyStack<IStatement> executionStack = new MyStack<IStatement>();
        IMyDictionary<String, IValue> symbolTable = new MyDictionary<String, IValue>();
        IMyList<IValue> out = new MyList<IValue>();

        ProgramState programState = new ProgramState(executionStack, symbolTable, out);
    }
}
