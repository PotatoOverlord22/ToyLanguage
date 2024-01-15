package view.graphicalview.scenes;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Pair;
import model.ProgramState;
import model.adts.IMyHeap;
import model.adts.IMyStack;
import model.adts.MyHeap;
import model.adts.SymbolTable;
import model.statements.IStatement;
import model.values.IValue;
import view.graphicalview.customhandlers.SceneSwitcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExecutionScene extends Scene{
    private SceneSwitcher sceneSwitcher;
    private IController programController;
    private TextField numberOfProgramStates;
    private TableView<Pair<Integer, IValue>> heapTableView;

    private TableView<Pair<String, IValue>> symTableView;
    private ListView<Integer> idListView;
    private ListView<IValue> outListView;
    private ListView<IStatement> executionStackListView;
    private ListView<String> filesListView;
    private Button oneStepButton;
    private Button backButton;
    private Scene scene;
    private ProgramState previousProgramState;
    private VBox layout;

    public ExecutionScene(VBox layout, int width, int height, SceneSwitcher sceneSwitcher, IController programController){
        super(layout, width, height);
        this.layout = layout;
        this.sceneSwitcher = sceneSwitcher;
        this.programController = programController;
        init();
    }

    private void init(){
        previousProgramState = programController.getAll().get(0);

        // Create the back button
        backButton = new Button("Back to program selection");
        backButton.setOnAction(actionEvent -> sceneSwitcher.switchToSelectionScene());

        // Text field with the number of program states
        numberOfProgramStates = new TextField("Number of threads: " + programController.repositorySize());

        // Table View representing the heap
        heapTableView = new TableView<>();
        // Get the heap from one of the program states (doesn't matter which)
        // TODO: check if heap is null and raise an error / maybe handle it here?
        IMyHeap heap = programController.getAll().get(0).getHeap();

        // Populate the heap data into a list of Pair
        List<Pair<Integer, IValue>> heapTableList = new ArrayList<>();
        for (var entry : heap.getContent().entrySet())
            heapTableList.add(new Pair<>(entry.getKey(), entry.getValue()));

        // Set up columns
        TableColumn<Pair<Integer, IValue>, Integer> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getKey()).asObject());

        TableColumn<Pair<Integer, IValue>, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        // Add columns to the TableView
        heapTableView.getColumns().addAll(locationColumn, valueColumn);

        // Set the data
        heapTableView.setItems(FXCollections.observableList(heapTableList));



        // The output of the program as ListView
        outListView = new ListView<>();
        List<IValue> outList = programController.getAll().get(0).getOutput().getContent();
        outListView.setItems(FXCollections.observableList(outList));

        // The files opened by the program as ListView
        filesListView = new ListView<>();
        List<String> fileNames = new ArrayList<>();
        programController.getAll().get(0).getFileTable().getContent().forEach((key, value) ->{
            fileNames.add(key);
        });
        filesListView.setItems(FXCollections.observableList(fileNames));

        // The ids of all the program states
        idListView = new ListView<>();
        List<Integer> ids = new ArrayList<>();
        programController.getAll().forEach(prg -> ids.add(prg.getId()));
        idListView.setItems(FXCollections.observableList(ids));
        idListView.setOnMouseClicked(mouseEvent -> {
            updateAll();
        });

        // Symbol table view
        symTableView = new TableView<>();
        SymbolTable symbolTable = programController.getAll().get(0).getSymbolTable();

        // Populate the symbol table data into a list of Pair
        List<Pair<String, IValue>> symbolTableList = new ArrayList<>();
        for (var entry : symbolTable.getContent().entrySet())
            symbolTableList.add(new Pair<>(entry.getKey(), entry.getValue()));

        // Set up columns
        TableColumn<Pair<String, IValue>, String> varNameColumn = new TableColumn<>("Variable name");
        varNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));

        TableColumn<Pair<String, IValue>, String> varValueColumn = new TableColumn<>("Variable value");
        varValueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        // Add columns to the TableView
        symTableView.getColumns().addAll(varNameColumn, varValueColumn);

        // Set the data
        symTableView.setItems(FXCollections.observableList(symbolTableList));


        // The exe stack of the currently selected thread
        executionStackListView = new ListView<>();
        Stack<IStatement> stack = programController.getAll().get(0).getExecutionStack().getStack();
        List<IStatement> statementList = new ArrayList<>(stack.reversed());
        executionStackListView.setItems(FXCollections.observableList(statementList));

        // Execute one step button
        oneStepButton = new Button("Execute one step for all threads");
        oneStepButton.setOnAction(actionEvent -> {
            previousProgramState = programController.getProgramById(idListView.getSelectionModel().getSelectedItem());
            programController.oneStep();
            updateAll();
        });

        // remove this
        Button updateButton = new Button("Update");
        updateButton.setOnAction(actionEvent -> updateAll());

        // Add elements to layout
        layout.getChildren().add(numberOfProgramStates);
        layout.getChildren().add(heapTableView);
        layout.getChildren().add(symTableView);
        layout.getChildren().add(outListView);
        layout.getChildren().add(filesListView);
        layout.getChildren().add(idListView);
        layout.getChildren().add(executionStackListView);
        layout.getChildren().add(oneStepButton);
        layout.getChildren().add(updateButton);
        layout.getChildren().add(backButton);
    }
    private ProgramState getCurrentlySelectedProgramState(){
        int id = idListView.getSelectionModel().getSelectedItem();
        return (programController.getProgramById(id) == null) ? previousProgramState : programController.getProgramById(id);
    }
    private void updateAll(){
        updateOutView();
        updateFileView();
        updateHeap();
        updateIdView();
        updateNumberOfProgramStates();
        updateCurrentThreadExecutionStack();
        updateSymbolTableView();
    }
    private void updateOutView(){
        List<IValue> outList = getCurrentlySelectedProgramState().getOutput().getContent();
        outListView.setItems(FXCollections.observableList(outList));
    }
    private void updateFileView(){
        List<String> fileNames = new ArrayList<>();
        getCurrentlySelectedProgramState().getFileTable().getContent().forEach((key, value) ->{
            fileNames.add(key);
        });
        filesListView.setItems(FXCollections.observableList(fileNames));
    }

    private void updateHeap(){
        IMyHeap heap = getCurrentlySelectedProgramState().getHeap();
        // Populate the heap data into a list of Pair
        List<Pair<Integer, IValue>> heapTableList = new ArrayList<>();
        for (var entry : heap.getContent().entrySet())
            heapTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        heapTableView.setItems(FXCollections.observableList(heapTableList));
        heapTableView.refresh();
    }

    private void updateSymbolTableView(){
        SymbolTable symbolTable = getCurrentlySelectedProgramState().getSymbolTable();
        // Populate the heap data into a list of Pair
        List<Pair<String, IValue>> symbolTableList = new ArrayList<>();
        for (var entry : symbolTable.getContent().entrySet())
            symbolTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        symTableView.setItems(FXCollections.observableList(symbolTableList));
        symTableView.refresh();
    }

    private void updateIdView(){
        List<Integer> ids = new ArrayList<>();
        programController.getAll().forEach(prg -> ids.add(prg.getId()));
        idListView.setItems(FXCollections.observableList(ids));
    }

    private void updateNumberOfProgramStates(){
        numberOfProgramStates.setText("Number of threads: " + programController.repositorySize());
    }

    private void updateCurrentThreadExecutionStack(){
        Stack<IStatement> stack = getCurrentlySelectedProgramState().getExecutionStack().getStack();
        List<IStatement> statementList = new ArrayList<>(stack.reversed());
        executionStackListView.setItems(FXCollections.observableList(statementList));
    }
}
