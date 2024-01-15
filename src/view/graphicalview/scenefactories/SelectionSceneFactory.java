package view.graphicalview.scenefactories;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.exceptions.ExecutionException;
import model.statements.IStatement;
import view.graphicalview.customhandlers.SceneSwitcher;

public class SelectionSceneFactory {
    private static final int SCENE_HEIGHT = 500;
    private static final int SCENE_WIDTH = 500;
    public Scene createSelectionScene(SceneSwitcher sceneSwitcher, ObservableList<IStatement> statements){
        // Create the list view of statements
        ListView<IStatement> programs = new ListView<>();
        programs.setItems(statements);

        // Select scene layout
        VBox selectionLayout = new VBox();

        // Execute program button
        Button executeProgramButton = new Button("Execute program");
        // On button press, extract the selected IStatement and send it to the scene switcher to create an execution scene based on it
        executeProgramButton.setOnAction(actionEvent -> {
            try {
                sceneSwitcher.switchToExecutionScene(getSelectedStatement(programs));
            } catch (ExecutionException error) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No selected item found");
                alert.setContentText(error.getMessage());
                alert.showAndWait();
            }
        });

        // Add the elements to the layout
        selectionLayout.getChildren().add(programs);
        selectionLayout.getChildren().add(executeProgramButton);
        return new Scene(selectionLayout, SCENE_WIDTH, SCENE_HEIGHT);
    }
    private static IStatement getSelectedStatement(ListView<IStatement> programs) throws ExecutionException{
        IStatement statement = programs.getSelectionModel().getSelectedItem();
        if (statement == null){
            throw new ExecutionException("A program must be selected");
        }
        return statement;
    }
}
