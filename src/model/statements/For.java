package model.statements;

import model.ProgramState;
import model.adts.IMyDictionary;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.expressions.IExpression;
import model.expressions.RelationalExpression;
import model.expressions.VarExpression;
import model.types.IType;
import model.types.IntType;

public class For implements IStatement{
    private final IExpression firstExpression;
    private final IExpression secondExpression;
    private final IExpression thirdExpression;
    private final IStatement statementToExecute;

    private final String varName;

    public For(String varName, IExpression firstExpression, IExpression secondExpression, IExpression thirdExpression, IStatement statementToExecute){
        this.varName = varName;
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.thirdExpression = thirdExpression;
        this.statementToExecute = statementToExecute;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException, ReadWriteException {
        state.getExecutionStack().push(new While(new RelationalExpression("<", new VarExpression(varName), secondExpression),
                new CompoundStatement(statementToExecute, new AssignStatement(varName, thirdExpression))));
        state.getExecutionStack().push(new AssignStatement(varName, firstExpression));
        state.getExecutionStack().push(new VarDeclaration(new IntType(), varName));
        return null;
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnvironment) throws EvaluationException {
        IType firstExpressionType = firstExpression.typeCheck(typeEnvironment);
        typeEnvironment.put(varName, new IntType());
        IType secondExpressionType = secondExpression.typeCheck(typeEnvironment);
        IType thirdExpressionType = thirdExpression.typeCheck(typeEnvironment);
        if (!firstExpressionType.equals(new IntType()))
            throw new EvaluationException("Expression " + firstExpression + " is not of int type");
        if (!secondExpressionType.equals(new IntType()))
            throw new EvaluationException("Expression " + secondExpression + " is not of int type");
        if (!thirdExpressionType.equals(new IntType()))
            throw new EvaluationException("Expression " + thirdExpression + " is not of int type");
        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy() {
        return new For(varName, firstExpression.deepCopy(), secondExpression.deepCopy(), thirdExpression.deepCopy(), statementToExecute.deepCopy());
    }

    @Override
    public String toString() {
        return "(for(" + varName + "=" + firstExpression + "; " + varName + "<" + secondExpression + "; " + varName + "=" + thirdExpression + ")" + statementToExecute + ")";
    }
}
