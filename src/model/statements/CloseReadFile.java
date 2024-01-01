package model.statements;

import model.ProgramState;
import model.adts.IMyDictionary;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.expressions.IExpression;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFile implements IStatement{
    private IExpression expression;

    public CloseReadFile(IExpression expression){
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException, ReadWriteException {
        IValue fileValue = expression.evaluate(state.getSymbolTable(), state.getHeap());
        // Check if the expression evaluates to a string type (we need a file name and that must be a string)
        if(!fileValue.getType().equals(new StringType()))
            throw  new ExecutionException("File name " + fileValue + "is not of StringType");
        String fileName = ((StringValue) fileValue).getValue();
        // Check if the file is in the file table
        if (state.getFileTable().get(fileName) == null)
            throw new ExecutionException("File " + fileName + " has not been found in the File Table");
        // Get the file descriptor associated with the file name
        BufferedReader fileDescriptor = state.getFileTable().get(fileName);
        // Check if there is a file descriptor associated with the computed file name
        if (fileDescriptor == null)
            throw new ExecutionException("File " + fileName + " has no associated file descriptor");
        try{
            fileDescriptor.close();
        }
        catch (IOException error){
            throw new ReadWriteException(error.getMessage());
        }
        // Remove the file from the file table
        state.getFileTable().remove(fileName);
        return null;
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnvironment) throws EvaluationException {
        IType expressionType = expression.typeCheck(typeEnvironment);
        // The type of expression should be string (file paths are strings)
        if (!expressionType.equals(new StringType()))
            throw new EvaluationException("Close read file: expression " + expression + " is not of string type");
        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseReadFile(expression);
    }

    @Override
    public String toString() {
        return "closeFile(" + expression.toString() + ")";
    }
}
