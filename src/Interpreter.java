import controller.Controller;
import controller.IController;
import model.ProgramState;
import model.adts.*;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.ReferenceType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;
import view.TextView;
import view.commands.ExitCommand;
import view.commands.RunProgramCommand;

import java.io.BufferedReader;
import java.util.Scanner;

public class Interpreter {
    private static ProgramState createProgram(IStatement startingStatement) {
        IMyStack<IStatement> executionStack = new MyStack<>();
        IMyList<IValue> output = new MyList<>();
        SymbolTable symbolTable = new SymbolTable();
        IMyDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
        IMyHeap heap = new MyHeap();

        return new ProgramState(executionStack, symbolTable, output, fileTable, startingStatement, heap);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the log file path: ");
        String logFilePath = scanner.next();
        System.out.println();
        TextView view = new TextView();

        // In this part we add hard coded program entries and their meaning in a more understandable way
        /*
                Program 1:
            int v;
            v=2;
            Print(v)
         */
        IStatement program1 = new CompoundStatement(new VarDeclaration(new IntType(), "v"), new
                CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                new PrintStatement(new VarExpression("v"))));

        try{
            program1.typeCheck(new MyDictionary<>());
            IRepository repository1 = new Repository(logFilePath, createProgram(program1));
            IController controller1 = new Controller(repository1);
            view.addCommand(new RunProgramCommand("1", "\n\tint v;\n\tv=2;\n\tPrint(v)", controller1));

        }
        catch (EvaluationException exception){
            System.out.println(exception.getMessage());
        }

        /*
                Program 2:
            int x;
            int y;
            x = 2*3 + 3;
            y = 6-3;
            int z;
            z = x / y;
            Print(z)
         */
        IStatement program2 = new CompoundStatement(new VarDeclaration(new IntType(), "x"), new CompoundStatement(
                new VarDeclaration(new IntType(), "y"), new CompoundStatement(new AssignStatement(
                "x", new ArithmeticExpression('+', new ValueExpression(new IntValue(3)), new ArithmeticExpression(
                '*', new ValueExpression(new IntValue(2)), new ValueExpression(new IntValue(3))))),
                new CompoundStatement(new AssignStatement("y",
                        new ArithmeticExpression('-', new ValueExpression(new IntValue(6)), new ValueExpression(new IntValue(6)))),
                        new CompoundStatement(new VarDeclaration(new IntType(), "z"), new CompoundStatement(
                                new AssignStatement("z", new ArithmeticExpression('/', new VarExpression("x"),
                                        new VarExpression("y"))), new PrintStatement(new VarExpression("z")))
                        )))));

        try{
            program2.typeCheck(new MyDictionary<>());
            IRepository repository2 = new Repository(logFilePath, createProgram(program2));
            IController controller2 = new Controller(repository2);
            view.addCommand(new RunProgramCommand("2", "\n\tint x;\n\tint y;\n\tx = 2*3 + 3;\n\ty = 6-3;\n\tint z;\n\tz = x / y;\n\tPrint(z)", controller2));
        }
        catch (EvaluationException exception){
            System.out.println(exception.getMessage());
        }
        /*
                Program 3:
            bool a;
            a=false;
            int v;
            If a Then v=2 Else v=3;
            Print(v)
         */
        IStatement program3 = new CompoundStatement(new VarDeclaration(new BoolType(), "a"), new CompoundStatement(
                new AssignStatement("a", new ValueExpression(new BoolValue(false))), new CompoundStatement(
                new VarDeclaration(new IntType(), "v"), new CompoundStatement(
                new IfStatement(new VarExpression("a"), new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VarExpression("v"))
        )
        )));
        try{
            program3.typeCheck(new MyDictionary<>());
            IRepository repository3 = new Repository(logFilePath, createProgram(program3));
            IController controller3 = new Controller(repository3);
            view.addCommand(new RunProgramCommand("3", "\n\tbool a;\n\ta=false;\n\tint v;\n\tIf a Then v=2 Else v=3;\n\tPrint(v));", controller3));
        }
        catch (EvaluationException exception){
            System.out.println(exception.getMessage());
        }
       /*
            Program 4:
            String varf;
            varf = "test.in";
            openRFile(varf);
            int varc;
            readFile(varf, varc);
            print(varc);

            readFile("src/logs/test2.in", varc);
            closeRFile(varf);
        */
        IStatement program4 = new CompoundStatement(new VarDeclaration(new StringType(), "varf"), new CompoundStatement(
                new AssignStatement("varf", new ValueExpression(new StringValue("src/logs/test.in"))), new CompoundStatement(new OpenReadFile(new VarExpression("varf")),
                new CompoundStatement(
                        new VarDeclaration(new IntType(), "varc"), new CompoundStatement(
                        new ReadNumberFromFile(new VarExpression("varf"), "varc"), new CompoundStatement(
                        new PrintStatement(new VarExpression("varc")), new CloseReadFile(new VarExpression("varf")))
                ))
        ))
        );
        try{
            program4.typeCheck(new MyDictionary<>());
            IRepository repository4 = new Repository(logFilePath, createProgram(program4));
            IController controller4 = new Controller(repository4);
            view.addCommand(new RunProgramCommand("4", "\n\tString varf;\n\tvarf = test.in\n\topenRFile(varf);\n\tint varc;\n\treadFile(varf, varc);" +
                    "\n\tprint(varc);\n\tcloseRFile(varf);", controller4));

        }
        catch (EvaluationException exception){
            System.out.println(exception.getMessage());
        }

//        IStatement program4 = new CompoundStatement(new VarDeclaration(new StringType(), "varf"), new CompoundStatement(
//                new AssignStatement("varf", new ValueExpression(new StringValue("src/logs/test.in"))), new CompoundStatement(new OpenReadFile(new VarExpression("varf")),
//                new CompoundStatement(
//                        new VarDeclaration(new IntType(), "varc"), new CompoundStatement(
//                        new ReadNumberFromFile(new VarExpression("varf"), "varc"), new CompoundStatement(
//                        new PrintStatement(new VarExpression("varc")), new CompoundStatement(new ReadNumberFromFile(
//                                new ValueExpression(new StringValue("src/logs/test2.in")), "varc"
//                ) ,new CloseReadFile(new VarExpression("varf")))
//                )))
//        ))
//        );

        /*
             Program 5:
             bool a;
             bool b;
             a = true;
             b = false;
             Print(a && b);
         */

        IStatement program5 = new CompoundStatement(new VarDeclaration(new IntType(), "a"), new AssignStatement("a", new ValueExpression(new IntValue(2))));

        try{
            program5.typeCheck(new MyDictionary<>());
            IRepository repository5 = new Repository(logFilePath, createProgram(program5));
            IController controller5 = new Controller(repository5);
            view.addCommand(new RunProgramCommand("5", "\n\tbool a;\n\tbool b;\n\ta = true;\n\tb = false;\n\tPrint(a && b);", controller5));
        }
        catch (EvaluationException exception){
            System.out.println(exception.getMessage());
        }


        IStatement program6 = new CompoundStatement(new VarDeclaration(new ReferenceType(new IntType()), "v"),
                new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VarDeclaration(new ReferenceType(new ReferenceType(new IntType())), "a"),
                                new CompoundStatement(new HeapAllocation("a", new VarExpression("v")),
                                        new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new PrintStatement(new HeapReadExpression(new HeapReadExpression(new VarExpression("a")))),
                                                        new HeapAllocation("v", new ValueExpression(new IntValue(90))))))
                        )
                )

        );
        try{
            program6.typeCheck(new MyDictionary<>());
            IRepository repository6 = new Repository(logFilePath, createProgram(program6));
            IController controller6 = new Controller(repository6);
            view.addCommand(new RunProgramCommand("6", "{Reference(int) v;{new(v, 20);{Reference(Reference(int)) a;{new(a, v);{new(v, 30);{print(heapRead(heapRead(a)));new(v, 90)}}}}}}", controller6));
        }
        catch (EvaluationException exception){
            System.out.println(exception.getMessage());
        }

        IStatement threadsExample = new CompoundStatement(new VarDeclaration(new IntType(), "v"),
                new CompoundStatement(new VarDeclaration(new ReferenceType(new IntType()), "a"),
                        new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new HeapAllocation("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new Fork(
                                                new CompoundStatement(new HeapWrite("a", new ValueExpression(new IntValue(30))),
                                                        new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                                new CompoundStatement(new PrintStatement(new VarExpression("v")),
                                                                        new PrintStatement(new HeapReadExpression(new VarExpression("a"))))
                                                        ))), new CompoundStatement(new PrintStatement(new VarExpression("v")),
                                                new PrintStatement(new HeapReadExpression(new VarExpression("a"))))
                                        )
                                )
                        )
                )
        );
        try{
            threadsExample.typeCheck(new MyDictionary<>());
            IRepository repository7 = new Repository(logFilePath, createProgram(threadsExample));
            IController controller7 = new Controller(repository7);
            view.addCommand(new RunProgramCommand("7", "Threads example 1", controller7));
        }
        catch (EvaluationException exception){
            System.out.println(exception.getMessage());
        }

        IStatement threadsExample1 = new CompoundStatement(new VarDeclaration(new IntType(), "counter"), new CompoundStatement(
                new VarDeclaration(new ReferenceType(new IntType()), "a"),
                new While(new RelationalExpression("<", new VarExpression("counter"), new ValueExpression(new IntValue(10))), new CompoundStatement(
                        new Fork(new Fork(new CompoundStatement(
                                new HeapAllocation("a", new VarExpression("counter")), new PrintStatement(new HeapReadExpression(new VarExpression("a")))
                        ))), new AssignStatement("counter", new ArithmeticExpression('+', new VarExpression("counter"),
                        new ValueExpression(new IntValue(1))))))));
        try{
            threadsExample1.typeCheck(new MyDictionary<>());
            IRepository repository8 = new Repository(logFilePath, createProgram(threadsExample1));
            IController controller8 = new Controller(repository8);
            view.addCommand(new RunProgramCommand("8", "Threads example 2", controller8));
        }
        catch (EvaluationException exception){
            System.out.println(exception.getMessage());
        }


        IStatement typeCheckerFail = new CompoundStatement(new VarDeclaration(new IntType(), "v"), new AssignStatement("v", new ValueExpression(new BoolValue(false))));

        try{
            typeCheckerFail.typeCheck(new MyDictionary<>());
            IRepository repository9 = new Repository(logFilePath, createProgram(typeCheckerFail));
            IController controller9 = new Controller(repository9);
            view.addCommand(new RunProgramCommand("9", "Type checker should fail this one", controller9));
        }
        catch (EvaluationException exception){
            System.out.println(exception.getMessage());
        }

        view.addCommand(new ExitCommand("0", "Exit"));

        view.show();
    }
}
