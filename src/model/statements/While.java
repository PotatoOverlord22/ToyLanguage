package model.statements;

import model.ProgramState;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.expressions.IExpression;
import model.values.BoolValue;

public class While implements IStatement{
    private final IExpression condition;
    private final IStatement statementToExecute;

    public While(IExpression condition, IStatement statementToExecute){
        this.condition = condition;
        this.statementToExecute = statementToExecute;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException, ReadWriteException {
        // The condition should be a BoolValue
        if (!(condition.evaluate(state.getSymbolTable(), state.getHeap()) instanceof BoolValue boolCondition))
            throw new ExecutionException("Condition " + condition + " does not evaluate to a Bool Value");
        // If the condition is true, then put on the execution stack the while statement again and the statement that needs to be executed
        if (boolCondition.getValue()){
            // Put the while statement again first
            state.getExecutionStack().push(this.deepCopy());
            // Now we put the statement that needs to be executed, so that it is executed before the while (avoiding an infinite loop)
            state.getExecutionStack().push(statementToExecute);
        }
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new While(condition.deepCopy(), statementToExecute.deepCopy());
    }

    @Override
    public String toString() {
        return "while(" + condition + ") " + statementToExecute.toString();
    }
}
