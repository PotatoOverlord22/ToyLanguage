import controller.Controller;
import controller.IController;
import model.ProgramState;
import model.adts.*;
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
    private static ProgramState createProgram(IStatement startingStatement){
        IMyStack<IStatement> executionStack = new MyStack<>();
        IMyList<IValue> output = new MyList<>();
        SymbolTable symbolTable = new SymbolTable();
        IMyDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
        IMyHeap heap = new MyHeap();

        return new ProgramState(executionStack, symbolTable, output, fileTable,startingStatement, heap);
    }
    public static void main(String[] args) {
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

        IStatement program5 = new CompoundStatement(new VarDeclaration(new IntType(), "a"), new CompoundStatement(
                new VarDeclaration(new BoolType(), "b"), new CompoundStatement(new AssignStatement("a", new ValueExpression(
                new BoolValue(true)
        )), new CompoundStatement(new AssignStatement("b", new ValueExpression(new BoolValue(false))),
                new PrintStatement(new LogicExpression('&', new VarExpression("a"), new VarExpression("b")))))
        ));

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

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the log file path: ");
        String logFilePath = scanner.next();
        System.out.println();

        IRepository repository1 = new Repository(logFilePath, createProgram(program1));
        IRepository repository2 = new Repository(logFilePath, createProgram(program2));
        IRepository repository3 = new Repository(logFilePath, createProgram(program3));
        IRepository repository4 = new Repository(logFilePath, createProgram(program4));
        IRepository repository5 = new Repository(logFilePath, createProgram(program5));
        IRepository repository6 = new Repository(logFilePath, createProgram(program6));
        IRepository repository7 = new Repository(logFilePath, createProgram(threadsExample));
        TextView view = new TextView();

        IController controller1 = new Controller(repository1);
        view.addCommand(new RunProgramCommand("1", "\n\tint v;\n\tv=2;\n\tPrint(v)", controller1));

        IController controller2 = new Controller(repository2);
        view.addCommand(new RunProgramCommand("2", "\n\tint x;\n\tint y;\n\tx = 2*3 + 3;\n\ty = 6-3;\n\tint z;\n\tz = x / y;\n\tPrint(z)", controller2));

        IController controller3 = new Controller(repository3);
        view.addCommand(new RunProgramCommand("3", "\n\tbool a;\n\ta=false;\n\tint v;\n\tIf a Then v=2 Else v=3;\n\tPrint(v));", controller3));

        IController controller4 = new Controller(repository4);
        view.addCommand(new RunProgramCommand("4", "\n\tString varf;\n\tvarf = test.in\n\topenRFile(varf);\n\tint varc;\n\treadFile(varf, varc);" +
                "\n\tprint(varc);\n\tcloseRFile(varf);", controller4));

        IController controller5 = new Controller(repository5);
        view.addCommand(new RunProgramCommand("5", "\n\tbool a;\n\tbool b;\n\ta = true;\n\tb = false;\n\tPrint(a && b);", controller5));

        IController controller6 = new Controller(repository6);
        view.addCommand(new RunProgramCommand("6", "{Reference(int) v;{new(v, 20);{Reference(Reference(int)) a;{new(a, v);{new(v, 30);{print(heapRead(heapRead(a)));new(v, 90)}}}}}}", controller6));

        IController controller7 = new Controller(repository7);
        view.addCommand(new RunProgramCommand("7", "", controller7));


        view.addCommand(new ExitCommand("0", "Exit"));

        view.show();
    }
}
