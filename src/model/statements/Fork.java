package model.statements;

import model.ProgramState;
import model.adts.MyStack;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;

public class Fork implements IStatement{
    private IStatement statement;

    public Fork(IStatement statement){
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException, ReadWriteException {
        return new ProgramState(new MyStack<>(), state.getSymbolTable().deepCopy(), state.getOutput(), state.getFileTable(), statement.deepCopy(), state.getHeap());
    }

    @Override
    public IStatement deepCopy() {
        return new Fork(statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + statement + ")";
    }
}
