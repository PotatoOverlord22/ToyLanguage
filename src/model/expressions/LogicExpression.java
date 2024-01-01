package model.expressions;

import model.adts.IMyDictionary;
import model.adts.IMyHeap;
import model.adts.SymbolTable;
import model.exceptions.EvaluationException;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class LogicExpression implements IExpression {
    // Expressions to evaluate
    private IExpression firstExpression;
    private IExpression secondExpression;
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
    public IValue evaluate(SymbolTable table, IMyHeap heap) throws EvaluationException {
        IValue firstIValue, secondIValue;
        firstIValue = firstExpression.evaluate(table, heap);
        secondIValue = secondExpression.evaluate(table, heap);

        boolean firstBoolean = ((BoolValue) firstIValue).getValue();
        boolean secondBoolean = ((BoolValue) secondIValue).getValue();

        // Call function that checks the operator and operands and computes the result using Java types
        return computeOperation(operator, firstBoolean, secondBoolean);

    }

    @Override
    public IType typeCheck(IMyDictionary<String, IType> typeEnvironment) throws EvaluationException {
        IType type1, type2;
        // get the types of the expressions
        type1 = firstExpression.typeCheck(typeEnvironment);
        type2 = secondExpression.typeCheck(typeEnvironment);
        // both expressions must be of bool type
        if (!type1.equals(new BoolType()))
            throw new EvaluationException("First operand " + firstExpression + " is not of boolean type");
        if (!type2.equals(new BoolType()))
            throw new EvaluationException("Second operand " + secondExpression + " is not of boolean type");
        return new BoolType();
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
