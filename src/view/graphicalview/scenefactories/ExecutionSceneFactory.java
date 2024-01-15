package view.graphicalview.scenefactories;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import model.ProgramState;
import model.adts.*;
import model.values.IValue;
import view.graphicalview.customhandlers.SceneSwitcher;
import view.graphicalview.scenes.ExecutionScene;

import java.util.ArrayList;
import java.util.List;

public class ExecutionSceneFactory {
    private static final int SCENE_HEIGHT = 500;
    private static final int SCENE_WIDTH = 500;
    public Scene createExecutionScene(SceneSwitcher sceneSwitcher, IController programController){
        // Create the scene layout
        VBox executionSceneLayout = new VBox();

        return new ExecutionScene(executionSceneLayout, SCENE_WIDTH, SCENE_HEIGHT, sceneSwitcher, programController);
    }
}
