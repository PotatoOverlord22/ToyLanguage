package model.expressions;

import model.adts.IMyDictionary;
import model.exceptions.EvaluationException;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class LogicExpression implements IExpression {
    // Expressions to evaluate
    IExpression firstExpression;
    IExpression secondExpression;
    char operator;

    public LogicExpression(char operator, IExpression first, IExpression second) {
        this.operator = operator;
        firstExpression = first;
        secondExpression = second;
    }

    @Override
    public IExpression deepCopy(){
        return new LogicExpression(operator, firstExpression.deepCopy(), secondExpression.deepCopy());
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> table) throws EvaluationException {
        IValue firstIValue, secondIValue;
        firstIValue = firstExpression.evaluate(table);
        secondIValue = secondExpression.evaluate(table);
        // Check type compatibility
        if (!isCompatible(firstIValue, secondIValue))
            throw new EvaluationException("operands are incompatible");

        boolean firstBoolean = ((BoolValue) firstIValue).getValue();
        boolean secondBoolean = ((BoolValue) secondIValue).getValue();

        // Call function that checks the operator and operands and computes the result using Java types
        return computeOperation(operator, firstBoolean, secondBoolean);

    }

    private boolean isCompatible(IValue first, IValue second){
        // Check if both are IntTypes
        if (!first.getType().equals(new BoolType()))
            return false;
        if (!second.getType().equals(new BoolType()))
            return false;
        return true;
    }

    private BoolValue computeOperation(char operator, boolean first, boolean second) throws EvaluationException {
        if (operator == '&')
            return new BoolValue(first && second);
        else if (operator == '|')
            return new BoolValue(first || second);
        throw new EvaluationException("unknown operator");
    }

    @Override
    public String toString() {
        return "(" + firstExpression.toString() + " " + operator + " " + secondExpression.toString() + ")";
    }
}
