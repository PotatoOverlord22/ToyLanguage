package model.statements;

import model.ProgramState;
import model.adts.IMyStack;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;

public class CompoundStatement implements IStatement {
    private final IStatement firstStatement;
    private final IStatement secondStatement;

    public CompoundStatement(IStatement first, IStatement second) {
        firstStatement = first;
        secondStatement = second;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException {
        IMyStack<IStatement> exeStack = state.getExecutionStack();
        exeStack.push(secondStatement);
        exeStack.push(firstStatement);
        return null;
    }

    @Override
    public String toString() {
        return "{" + firstStatement.toString() + ';' + secondStatement.toString() + "}";
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(firstStatement.deepCopy(), secondStatement.deepCopy());
    }
}
