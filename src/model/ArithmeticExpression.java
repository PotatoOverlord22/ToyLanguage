package model;

import exceptions.ModelException;

public class ArithmeticExpression implements IExpression{
    IExpression firstExpression;
    IExpression secondExpression;

    char operator;

    public ArithmeticExpression(char operator, IExpression firstExpression, IExpression secondExpression){
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }
    @Override
    public IValue evaluate(IMyDictionary<String, IValue> table) throws ModelException {
        IValue firstValue, secondValue;
        firstValue = firstExpression.evaluate(table);
        if (firstValue.getType().equals(new IntType())){
            secondValue = secondExpression.evaluate(table);
            if (secondValue.getType().equals(new IntType())){
                IntValue firstInteger = (IntValue) firstValue;
                IntValue secondInteger = (IntValue) secondValue;
                int n1, n2;
                n1 = firstInteger.getValue();
                n2 = secondInteger.getValue();
                if (operator == '+') return new IntValue(n1 + n2);
                else if (operator == '-') return new IntValue(n1 - n2);
                else if (operator == '*') return new IntValue(n1 * n2);
                else if (operator == '/'){
                    if (n2 == 0)
                        throw new ModelException("division by zero");
                    return new IntValue(n1 / n2);
                }
                else throw new ModelException("unknown operator");
            }
            else throw new ModelException("second operand is not an integer");
        }
        else throw new ModelException("first operand is not an integer");
    }
}
