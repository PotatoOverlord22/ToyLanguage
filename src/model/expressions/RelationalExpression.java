package model.expressions;

import model.adts.IMyDictionary;
import model.adts.IMyHeap;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

import java.util.Objects;

public class RelationalExpression implements IExpression {
    private IExpression firstExpression;
    private IExpression secondExpression;

    private String operator;

    public RelationalExpression(String operator, IExpression firstExpression, IExpression secondExpression){
        this.operator = operator;
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> table, IMyHeap heap) throws EvaluationException {
        // Check if the operator given is implemented
        if(!isSupportedOperator(operator))
            throw new EvaluationException("Operator " + operator + " is not supported");
        // Check if both the expressions evaluate to integers
        IValue firstValue = firstExpression.evaluate(table, heap);
        IValue secondValue = secondExpression.evaluate(table, heap);
        // Both values must be of integer type, otherwise throw exception
        if (!(firstValue.getType().equals(new IntType()) && secondValue.getType().equals(new IntType())))
            throw new EvaluationException("Expression " + firstExpression + " and/or expression " + secondExpression + " don't meet the required expression type");

        int firstInt = ((IntValue) firstValue).getValue();
        int secondInt = ((IntValue) secondValue).getValue();
        if (operator.equals("=="))
            return new BoolValue(firstInt == secondInt);
        if (operator.equals("<"))
            return new BoolValue(firstInt < secondInt);
        if (operator.equals(">"))
            return new BoolValue(firstInt > secondInt);
        if (operator.equals("!="))
            return new BoolValue(firstInt != secondInt);
        if (operator.equals("<="))
            return new BoolValue(firstInt <= secondInt);
        if (operator.equals(">="))
            return new BoolValue(firstInt >= secondInt);

        // If this is thrown, then there is something wrong in the function's logic.
        throw new EvaluationException("Something broke, check the evaluation of relational expression.");
    }

    private boolean isSupportedOperator(String operator){
        if (Objects.equals(operator, "=="))
            return true;
        if (Objects.equals(operator, "<"))
            return true;
        if (Objects.equals(operator, "<="))
            return true;
        if (Objects.equals(operator, ">"))
            return true;
        if (Objects.equals(operator, ">="))
            return true;
        if (Objects.equals(operator, "!="))
            return true;
        return false;
    }
    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(operator, firstExpression.deepCopy(), secondExpression.deepCopy());
    }

    @Override
    public String toString() {
        return firstExpression + " " + operator + " " + secondExpression;
    }
}
