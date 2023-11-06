import controller.Controller;
import controller.IController;
import model.ProgramState;
import model.adts.*;
import model.expressions.ArithmeticExpression;
import model.expressions.ValueExpression;
import model.expressions.VarExpression;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import repository.IRepository;
import repository.Repository;
import view.IView;
import view.TextView;

public class Interpreter {
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
                        new ArithmeticExpression('-', new ValueExpression(new IntValue(6)), new ValueExpression(new IntValue(3)))),
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

        IRepository repository = new Repository();
        repository.addProgram(program1, "\n\tint v;\n\tv=2;\n\tPrint(v)");
        repository.addProgram(program2, "\n\tint x;\n\tint y;\n\tx = 2*3 + 3;\n\ty = 6-3;\n\tint z;\n\tz = x / y;\n\tPrint(z)");
        repository.addProgram(program3, "\n\tbool a;\n\ta=false;\n\tint v;\n\tIf a Then v=2 Else v=3;\n\tPrint(v));");

        IController controller = new Controller(repository);
        IView view = new TextView(controller);

        view.startView();
    }
}
