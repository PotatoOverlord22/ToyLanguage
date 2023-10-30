package model.expressions;

import exceptions.ModelException;
import model.adts.IMyDictionary;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class LogicExpression implements IExpression {
    // Expressions to evaluate
    IExpression e1;
    IExpression e2;
    char operator;

    public LogicExpression(char operator, IExpression first, IExpression second) {
        this.operator = operator;
        e1 = first;
        e2 = second;
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> table) throws ModelException {
        IValue firstIValue, secondIValue;
        firstIValue = e1.evaluate(table);
        secondIValue = e2.evaluate(table);
        // Check type compatibility
        if (!isCompatible(firstIValue, secondIValue))
            throw new ModelException("operands are incompatible");

        BoolValue firstBoolean = (BoolValue) firstIValue;
        BoolValue secondBoolean = (BoolValue) secondIValue;
        boolean first, second;
        first = firstBoolean.getValue();
        second = secondBoolean.getValue();

        // Call function that checks the operator and operands and computes the result using Java types
        return computeOperation(operator, first, second);

    }

    private boolean isCompatible(IValue first, IValue second){
        // Check if both are IntTypes
        if (!first.getType().equals(new BoolType()))
            return false;
        if (!second.getType().equals(new BoolType()))
            return false;
        return true;
    }

    private BoolValue computeOperation(char operator, boolean first, boolean second) throws ModelException {
        if (operator == '&')
            return new BoolValue(first && second);
        else if (operator == '|')
            return new BoolValue(first || second);
        throw new ModelException("unknown operator");
    }
}
