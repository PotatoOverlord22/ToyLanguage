package view.graphicalview.customhandlers;

import model.statements.IStatement;

public interface SceneSwitcher {
    void switchToExecutionScene(IStatement programToExecute);
    void switchToSelectionScene();

}
