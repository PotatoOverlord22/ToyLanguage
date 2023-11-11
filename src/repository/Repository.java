package repository;

import model.ProgramState;
import model.adts.*;
import model.exceptions.ReadWriteException;
import model.statements.IStatement;
import model.values.IValue;

import java.io.*;

public class Repository implements IRepository {
    // A list of programs and their "natural language" meaning
    private final IMyList<IMyPair<ProgramState, String>> programs;

    private String logFilePath;

    public Repository(String logFilePath) {
        this.logFilePath = logFilePath;
        programs = new MyList<>();
    }

    @Override
    public void addProgram(IStatement startingStatement, String programInNaturalLanguage) {
        /*
            Add the program alongside its meaning in natural language to the repository, ex:
            startingStatement = new CompoundStatement(new VarDeclaration(new IntType(), "v"), new CompoundStatement(new AssignmentStatement("v",
                                    new ValueExpression(new IntValue(2)), new PrintStatement(new VarExpression("v"));
                In natural language is equivalent to:
            int v;
            v=2;
            Print(v)
         */
        // Create an instance of execution stack, symbol table and output for each program
        // because each program state needs its separate instance
        IMyStack<IStatement> executionStack = new MyStack<>();
        IMyList<IValue> output = new MyList<>();
        IMyDictionary<String, IValue> symbolTable = new MyDictionary<>();
        IMyDictionary<String, BufferedReader> fileTable = new MyDictionary<>();

        ProgramState program = new ProgramState(executionStack, symbolTable, output, fileTable,startingStatement);

        programs.add(new MyPair<>(program, programInNaturalLanguage));
    }

    @Override
    public int size() {
        return programs.size();
    }

    @Override
    public IMyList<IMyPair<ProgramState, String>> getAll() {
        return programs;
    }

    @Override
    public ProgramState getProgramAt(int index) {
        return programs.get(index).first();
    }

    @Override
    public void logProgramState(int programIndex) throws ReadWriteException {
        ProgramState programToPrint = programs.get(programIndex).first();
        try{
            //
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(programToPrint.toString() + '\n');
            logFile.close();
        }
        catch (IOException error){
            throw new ReadWriteException(error.getMessage());
        }
    }
}
