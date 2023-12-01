package repository;

import model.ProgramState;
import model.adts.*;
import model.exceptions.ReadWriteException;
import model.statements.IStatement;
import model.values.IValue;

import java.io.*;

public class Repository implements IRepository {
    private final IMyList<ProgramState> programs;

    private String logFilePath;

    public Repository(String logFilePath) {
        this.logFilePath = logFilePath;
        programs = new MyList<>();
    }

    @Override
    public void addProgram(IStatement startingStatement) {
        /*
            Add the program to the repository, ex:
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
        IMyHeap heap = new MyHeap();

        ProgramState program = new ProgramState(executionStack, symbolTable, output, fileTable,startingStatement, heap);

        programs.add(program);
    }

    @Override
    public int size() {
        return programs.size();
    }

    @Override
    public IMyList<ProgramState> getAll() {
        return programs;
    }

    @Override
    public ProgramState getProgramAt(int index) {
        return programs.get(index);
    }

    @Override
    public void logProgramState(int programIndex) throws ReadWriteException {
        ProgramState programToPrint = programs.get(programIndex);
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

    @Override
    public void resetProgram(int index) {
        programs.get(index).resetProgram();
    }
}
