package model.expressions;

import exceptions.ModelException;
import model.adts.IMyDictionary;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class ArithmeticExpression implements IExpression {
    IExpression firstExpression;
    IExpression secondExpression;

    char operator;

    public ArithmeticExpression(char operator, IExpression firstExpression, IExpression secondExpression) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> table) throws ModelException {
        IValue firstIValue, secondIValue;
        firstIValue = firstExpression.evaluate(table);
        secondIValue = secondExpression.evaluate(table);
        if(!isCompatible(firstIValue, secondIValue))
            throw new ModelException("operands are incompatible");
        IntValue firstInteger = (IntValue) firstIValue;
        IntValue secondInteger = (IntValue) secondIValue;
        int n1, n2;
        n1 = firstInteger.getValue();
        n2 = secondInteger.getValue();
        return computeOperation(operator, n1, n2);
    }

    private boolean isCompatible(IValue first, IValue second){
        // Check if both are IntTypes
        if (!first.getType().equals(new IntType()))
            return false;
        if (!second.getType().equals(new IntType()))
            return false;
        return true;
    }
    private IntValue computeOperation(char operator, int first, int second) throws ModelException{
        if (operator == '+') return new IntValue(first + second);
        else if (operator == '-') return new IntValue(first - second);
        else if (operator == '*') return new IntValue(first * second);
        else if (operator == '/') {
            if (second == 0)
                throw new ModelException("division by zero");
            return new IntValue(first / second);
        } else throw new ModelException("unknown operator");
    }

}
