package view.commands;

import controller.IController;
import model.exceptions.EvaluationException;
import model.exceptions.ExecutionException;
import model.exceptions.ReadWriteException;

public class RunProgramCommand extends Command{
    private IController controller;
    public RunProgramCommand(String key, String description, IController controller){
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        boolean showOnlyResult = false;
        try{
            controller.allSteps(showOnlyResult);
            controller.resetProgram();
        }
        catch (EvaluationException | ExecutionException | ReadWriteException exception){
            System.out.println(exception.getMessage());
        }
    }
}
