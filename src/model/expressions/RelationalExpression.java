package model.expressions;

import model.adts.IMyDictionary;
import model.adts.IMyHeap;
import model.adts.SymbolTable;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.types.IType;
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
    public IValue evaluate(SymbolTable table, IMyHeap heap) throws EvaluationException {
        // Evaluate both expressions
        IValue firstValue = firstExpression.evaluate(table, heap);
        IValue secondValue = secondExpression.evaluate(table, heap);

        // Get the raw int from the values
        int firstInt = ((IntValue) firstValue).getValue();
        int secondInt = ((IntValue) secondValue).getValue();
        // Compute the relation between the ints
        return computeRelation(operator, firstInt, secondInt);
    }
    @Override
    public IType typeCheck(IMyDictionary<String, IType> typeEnvironment) throws EvaluationException {
        IType type1, type2;
        // get the types of the expressions
        type1 = firstExpression.typeCheck(typeEnvironment);
        type2 = secondExpression.typeCheck(typeEnvironment);
        // both expressions must be of IntType
        if (!type1.equals(new IntType()))
            throw new EvaluationException("First operand " + firstExpression + " is not of Int Type");
        if (!type2.equals(new IntType()))
            throw new EvaluationException("Second operand " + secondExpression + " is not of Int Type");
        return new IntType();
    }

    private BoolValue computeRelation(String operator, int first, int second) throws EvaluationException{
        if (Objects.equals(operator, "=="))
            return new BoolValue(first == second);
        else if (Objects.equals(operator, "<"))
            return new BoolValue(first < second);
        else if (Objects.equals(operator, "<="))
            return new BoolValue(first <= second);
        else if (Objects.equals(operator, ">"))
            return new BoolValue(first > second);
        else if (Objects.equals(operator, ">="))
            return new BoolValue(first >= second);
        else if (Objects.equals(operator, "!="))
            return new BoolValue(first != second);
        else
            throw new EvaluationException("Operator " + operator + " not supported");
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
