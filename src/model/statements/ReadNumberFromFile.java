package model.statements;

import model.ProgramState;
import model.adts.IMyDictionary;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.expressions.IExpression;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadNumberFromFile implements IStatement {
    private IExpression expression;

    private String varName;

    public ReadNumberFromFile(IExpression expression, String varName){
        this.expression = expression;
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExecutionException, EvaluationException, ReadWriteException {
        // Check if the variable is in the symbol table
        if (state.getSymbolTable().get(varName) == null)
            throw new ExecutionException("Variable " + varName + " has not been declared");
        // Check if variable is of type IntType
        if (!state.getSymbolTable().get(varName).getType().equals(new IntType()))
            throw new ExecutionException("Variable " + varName + " is not of IntType");
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
        // Try to read a line from the file and translate it into an int (if it's a null then the value is 0)
        try{
            String line = fileDescriptor.readLine();
            IntValue lineValue;
            if (line == null)
                lineValue = new IntValue();
            else{
                // parseInt method throws NumberFormatException if the line that has been read does not respect the format of an int
                try{
                    lineValue = new IntValue(Integer.parseInt(line));
                }
                catch (NumberFormatException error){
                    throw new ExecutionException("Invalid number format");
                }
            }
            // Update the variable with the new value read from the file
            state.getSymbolTable().put(varName, lineValue);
        }
        catch (IOException error){
            throw new ReadWriteException(error.getMessage());
        }
        return null;
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnvironment) throws EvaluationException {
        IType variableType = typeEnvironment.get(varName);
        IType expressionType = expression.typeCheck(typeEnvironment);
        // The type of expression should be string (file paths are strings)
        if (!expressionType.equals(new StringType()))
            throw new EvaluationException("Expression " + expression + " is not of string type");
        // Variable should be int type
        if (!variableType.equals(new IntType()))
            throw new EvaluationException("Variable " + varName + " is not of int type");
        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadNumberFromFile(expression, varName);
    }

    @Override
    public String toString() {
        return "readNumber(" + expression.toString() + ", " + varName + ")";
    }
}
