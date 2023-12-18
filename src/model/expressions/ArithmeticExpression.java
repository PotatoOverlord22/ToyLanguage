package model.expressions;

import model.adts.IMyDictionary;
import model.adts.IMyHeap;
import model.adts.SymbolTable;
import model.exceptions.EvaluationException;
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
    public IExpression deepCopy() {
        return new ArithmeticExpression(operator, firstExpression.deepCopy(), secondExpression.deepCopy());
    }

    @Override
    public IValue evaluate(SymbolTable table, IMyHeap heap) throws EvaluationException {
        IValue firstIValue, secondIValue;
        firstIValue = firstExpression.evaluate(table, heap);
        secondIValue = secondExpression.evaluate(table, heap);
        //Check type compatibility
        if(!isCompatible(firstIValue, secondIValue))
            throw new EvaluationException("operands are incompatible");

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
    private IntValue computeOperation(char operator, int first, int second) throws EvaluationException{
        if (operator == '+') return new IntValue(first + second);
        else if (operator == '-') return new IntValue(first - second);
        else if (operator == '*') return new IntValue(first * second);
        else if (operator == '/') {
            if (second == 0)
                throw new EvaluationException("division by zero");
            return new IntValue(first / second);
        } else throw new EvaluationException("unknown operator");
    }

    @Override
    public String toString() {
        return firstExpression.toString() + " " + operator + " " + secondExpression.toString();
    }
}
