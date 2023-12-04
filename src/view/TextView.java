package view;

import controller.IController;
import model.ProgramState;
import model.adts.IMyDictionary;
import model.adts.IMyList;
import model.adts.IMyPair;
import model.adts.MyDictionary;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;
import model.statements.IStatement;
import view.commands.Command;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class TextView implements IView{
    private boolean stopView = false;
    private boolean showOnlyResult;

    private IMyDictionary<String, Command> commands;

    public TextView(){
        commands = new MyDictionary<>();
    }

    public void addCommand(Command command){
        commands.put(command.getKey(), command);
    }
    @Override
    public void show(){
        Scanner scanner = new Scanner(System.in);
        while(!stopView){
            printMenu();
            System.out.print("Select which command to run: ");
            String key = scanner.nextLine();
            Command com = commands.get(key);
            if (com == null){
                System.out.println("Invalid option");
            }
            else
                com.execute();
        }
    }

    private void printMenu(){
        for (Command currentCommand : commands.values()){
            String line = String.format("%4s : %s", currentCommand.getKey(), currentCommand.getDescription());
            System.out.println(line);
        }
    }

    public void setShowOnlyResult(boolean showOnlyResult) {
        this.showOnlyResult = showOnlyResult;
    }

    public boolean getShowOnlyResult(){
        return showOnlyResult;
    }

    public void setStopView(boolean stopView) {
        this.stopView = stopView;
    }

    public boolean getStopView(){
        return stopView;
    }
}
