package model.statements;

import model.ProgramState;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.expressions.IExpression;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OpenReadFile implements IStatement{
    private IExpression expression;

    public OpenReadFile(IExpression expression){
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException {
        IValue value = expression.evaluate(state.getSymbolTable(), state.getHeap());
        // Check if the evaluated expression is of String Type (file paths must be strings)
        if (!value.getType().equals(new StringType()))
            throw new ExecutionException("File name is not of string type.");
        String fileName = ((StringValue) value).getValue();
        // Check if the file name is already in the file table
        if (state.getFileTable().get(fileName) != null)
            throw new ExecutionException("File name is already in the file table.");

        // Try to open the file and put it in the file table if successful
        try{
            BufferedReader fileDescriptor = new BufferedReader(new FileReader(fileName));
            state.getFileTable().put(fileName, fileDescriptor);
        }
        catch (FileNotFoundException error){
            throw new ExecutionException(error.getMessage());
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenReadFile(expression);
    }

    @Override
    public String toString() {
        return "openFileWithRead(" + expression.toString() + ")";
    }
}
