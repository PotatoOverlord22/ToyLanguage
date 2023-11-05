package view;

import controller.IController;
import model.ProgramState;
import model.adts.IMyDictionary;
import model.adts.IMyList;
import model.adts.IMyPair;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.statements.IStatement;

import java.util.Scanner;

public class TextView implements IView{
    private boolean stopView = false;
    private final IController controller;
    public TextView(IController controller){
        this.controller = controller;
    }
    @Override
    public void startView(){
        int i;
        controller.setPrintToConsole(true);
        while (!stopView){
            IMyList<IMyPair<ProgramState, String>> allPrograms = controller.getAll();
            System.out.println("\nChoose a program to run from the list or 0 to end:");
            for (i = 1; i <= allPrograms.size(); ++i)
                // Print the natural language meaning of the program
                System.out.println("Program " + i + ":" + allPrograms.get(i - 1).second());
            System.out.println();
            System.out.println("User option: ");
            Scanner scanner = new Scanner(System.in);
            int userOption = scanner.nextInt();
            if (userOption == 0)
                stopView = true;
            else{
                try{
                    controller.runAllStepsOnProgram(userOption - 1);
                }
                catch (ExecutionException | EvaluationException exception){
                    System.out.println(exception.getMessage());
                }
            }

        }
    }
}
