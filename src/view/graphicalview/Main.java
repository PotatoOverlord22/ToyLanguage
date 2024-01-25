package view.graphicalview;

import controller.Controller;
import controller.IController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.ProgramState;
import model.adts.*;
import model.exceptions.EvaluationException;
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
import view.graphicalview.customhandlers.SceneSwitcher;
import view.graphicalview.scenefactories.ExecutionSceneFactory;
import view.graphicalview.scenefactories.SelectionSceneFactory;

import java.io.BufferedReader;

public class Main extends Application implements SceneSwitcher {
    private static final String LOG_FILE_PATH = "src/logs/logs.txt";

    private static ObservableList<IStatement> statements;

    // Primary stage that will be used to just switch between scenes
    private Stage primaryStage;

    // Factory instance used to create program execution scenes
    private final ExecutionSceneFactory executionSceneFactory = new ExecutionSceneFactory();

    // Factory instance used to create program selection scenes
    private final SelectionSceneFactory selectionSceneFactory = new SelectionSceneFactory();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Save the stage for later use
        this.primaryStage = primaryStage;

        // Populate the list with programs
        populateStatementsList();

        // Create the program selection scene
        Scene selectionScene = selectionSceneFactory.createSelectionScene(this, statements);

        // Program always starts at selection scene, then the rest is handled by ui elements calling switch scenes functions
        primaryStage.setScene(selectionScene);
        primaryStage.show();
    }

    @Override
    public void switchToExecutionScene(IStatement programToExecute) {
        // Try to create the controller of the program, if the type checker fails, show an alert
        try{
            IController programController = createProgramController(programToExecute, LOG_FILE_PATH);
            primaryStage.setScene(executionSceneFactory.createExecutionScene(this, programController));
        } // Handle the type checker exception by showing an alert with the error
        catch (EvaluationException error){
            Alert typeCheckerAlert = new Alert(Alert.AlertType.ERROR);
            typeCheckerAlert.setTitle("Type checker failure");
            typeCheckerAlert.setContentText(error.getMessage());
            typeCheckerAlert.showAndWait();
        }
    }

    @Override
    public void switchToSelectionScene() {
        primaryStage.setScene(selectionSceneFactory.createSelectionScene(this, statements));
    }
    private static IController createProgramController(IStatement startingStatement, String logFilePath) throws EvaluationException {
        startingStatement.typeCheck(new MyDictionary<>());
        IRepository repository = new Repository(logFilePath, createProgram(startingStatement));
        return new Controller(repository);
    }

    private static ProgramState createProgram(IStatement startingStatement) {
        IMyStack<IStatement> executionStack = new MyStack<>();
        IMyList<IValue> output = new MyList<>();
        SymbolTable symbolTable = new SymbolTable();
        IMyDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
        IMyHeap heap = new MyHeap();

        return new ProgramState(executionStack, symbolTable, output, fileTable, startingStatement, heap);
    }

    private static void populateStatementsList(){
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
        IStatement program2 = new CompoundStatement(new VarDeclaration(new ReferenceType(new IntType()), "v"),
                new CompoundStatement(new HeapAllocation("v", new ValueExpression(new IntValue(2))), new PrintStatement(new VarExpression("v"))));
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

        IStatement program5 = new CompoundStatement(new VarDeclaration(new IntType(), "a"), new AssignStatement("a", new ValueExpression(new IntValue(2))));



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

        IStatement threadsExample1 = new CompoundStatement(new VarDeclaration(new IntType(), "counter"), new CompoundStatement(
                new VarDeclaration(new ReferenceType(new IntType()), "a"),
                new While(new RelationalExpression("<", new VarExpression("counter"), new ValueExpression(new IntValue(10))), new CompoundStatement(
                        new Fork(new Fork(new CompoundStatement(
                                new HeapAllocation("a", new VarExpression("counter")), new PrintStatement(new HeapReadExpression(new VarExpression("a")))
                        ))), new AssignStatement("counter", new ArithmeticExpression('+', new VarExpression("counter"),
                        new ValueExpression(new IntValue(1))))))));

        IStatement typeCheckerFail = new CompoundStatement(new VarDeclaration(new IntType(), "v"),
                new AssignStatement("v", new ValueExpression(new BoolValue(false))));

        IStatement repeatUntilExample = new CompoundStatement(new VarDeclaration(new IntType(), "v"), new CompoundStatement(
                new RepeatUntil(new CompoundStatement(new Fork(
                        new CompoundStatement(new PrintStatement(new VarExpression("v")),
                                new AssignStatement("v", new ArithmeticExpression('-', new VarExpression("v"), new ValueExpression(new IntValue(1)))))), new
                        AssignStatement("v", new ArithmeticExpression('+', new VarExpression("v"), new ValueExpression(new IntValue(1))))),
                        new RelationalExpression("==", new VarExpression("v"), new ValueExpression(new IntValue(3)))),
                new PrintStatement(new ArithmeticExpression('*', new VarExpression("v"), new ValueExpression(new IntValue(10))))
        ));

        statements = FXCollections.observableArrayList(program1, program2, program3, program4, program5,
                                            program6, threadsExample, threadsExample1, typeCheckerFail, repeatUntilExample);

    }
}
