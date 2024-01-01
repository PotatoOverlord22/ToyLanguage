package model.expressions;

import model.adts.IMyDictionary;
import model.adts.IMyHeap;
import model.adts.SymbolTable;
import model.exceptions.EvaluationException;
import model.types.IType;
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

        int firstInteger = ((IntValue) firstIValue).getValue();
        int secondInteger = ((IntValue) secondIValue).getValue();

        return computeOperation(operator, firstInteger, secondInteger);
    }

    @Override
    public IType typeCheck(IMyDictionary<String, IType> typeEnvironment) throws EvaluationException {
        IType type1, type2;
        // get the types of the expressions
        type1 = firstExpression.typeCheck(typeEnvironment);
        type2 = secondExpression.typeCheck(typeEnvironment);
        // check if the types are int
        if (!type1.equals(new IntType()))
            throw new EvaluationException("First operand " + firstExpression + " is not an integer");
        if (!type2.equals(new IntType()))
            throw new EvaluationException("Second operand " + secondExpression + " is not an integer");
        return new IntType();
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
