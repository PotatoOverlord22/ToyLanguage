package model.expressions;

import model.exceptions.ModelException;
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
        //Check type compatibility
        if(!isCompatible(firstIValue, secondIValue))
            throw new ModelException("operands are incompatible");

        int firstInteger = ((IntValue) firstIValue).getValue();
        int secondInteger = ((IntValue) secondIValue).getValue();

        return computeOperation(operator, firstInteger, secondInteger);
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
