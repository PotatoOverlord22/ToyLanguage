package model.statements;

import javafx.beans.binding.BooleanExpression;
import model.ProgramState;
import model.adts.IMyDictionary;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.expressions.IExpression;
import model.expressions.LogicExpression;
import model.types.BoolType;
import model.types.IType;

public class RepeatUntil implements IStatement {
    private final IStatement statementToRepeat;
    private final IExpression condition;

    public RepeatUntil(IStatement statementToRepeat, IExpression expression) {
        this.statementToRepeat = statementToRepeat;
        this.condition = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException, ReadWriteException {
        IExpression negatedCondition = new LogicExpression('!', condition);
        state.getExecutionStack().push(new While(negatedCondition, statementToRepeat));

        state.getExecutionStack().push(statementToRepeat);
        return null;
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnvironment) throws EvaluationException {
        // The type of the condition should be of bool type
        IType conditionType = condition.typeCheck(typeEnvironment);
        if (!conditionType.equals(new BoolType()))
            throw new EvaluationException("Condition " + condition + " is not of bool type");
        // Send a copy because the typeEnvironment should not be modified between while loops (e.g Var declaration should not persist between iterations)
        statementToRepeat.typeCheck(typeEnvironment.copy());
        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy() {
        return new RepeatUntil(statementToRepeat.deepCopy(), condition.deepCopy());
    }

    @Override
    public String toString() {
        return "repeat (" + statementToRepeat + ") until " + "(" + condition + ")";
    }
}
