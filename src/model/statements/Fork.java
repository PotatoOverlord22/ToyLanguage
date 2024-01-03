package model.statements;

import model.ProgramState;
import model.adts.IMyDictionary;
import model.adts.MyStack;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.types.IType;

public class Fork implements IStatement {
    private IStatement statement;

    public Fork(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException, ReadWriteException {
        return new ProgramState(new MyStack<>(), state.getSymbolTable().deepCopy(), state.getOutput(), state.getFileTable(), statement.deepCopy(), state.getHeap());
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnvironment) throws EvaluationException {
        // Send a copy because it is a different thread
        statement.typeCheck(typeEnvironment.copy());
        return typeEnvironment;
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
