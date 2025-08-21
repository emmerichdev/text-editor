package com.emmerichbrowne.texteditor;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HelloController {

    @FXML
    private TabPane tabPane;
    
    private final Map<Tab, File> tabFiles = new HashMap<>();

    public void initialize() {
        onNew();
    }

    private void newTab() {
        Tab tab = new Tab("Untitled");
        tab.setClosable(true);
        tab.setOnCloseRequest(event -> {
            if (tabPane.getTabs().size() == 1) {
                event.consume();
            }
        });
        tab.setContent(new TextArea(""));
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    public void onNew() {
        newTab();
    }

    public void onOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Markdown Files", "*.md"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                String content = Files.readString(file.toPath());
                Tab tab = new Tab(file.getName());
                tab.setClosable(true);
                tab.setOnCloseRequest(event -> {
                    if (tabPane.getTabs().size() == 1) {
                        event.consume();
                    }
                });
                tab.setContent(new TextArea(content));
                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
                tabFiles.put(tab, file);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not open file");
                alert.setContentText("The file could not be opened. Please check the file and try again.");
                alert.showAndWait();
            }
        }
    }

    public void onSave() {
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        if (currentTab == null) {
            return;
        }
        
        File associatedFile = tabFiles.get(currentTab);
        if (associatedFile == null) {
            // No file associated, treat as saveas
            onSaveAs();
        } else {
            saveToFile(currentTab, associatedFile);
        }
    }

    public void onSaveAs() {
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        if (currentTab == null) {
            return;
        }
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File As");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Markdown Files", "*.md"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        
        // Set initial file name based on current tab name
        String currentName = currentTab.getText();
        if (!currentName.equals("Untitled")) {
            fileChooser.setInitialFileName(currentName);
        }
        
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            saveToFile(currentTab, file);
        }
    }
    
    private void saveToFile(Tab tab, File file) {
        try {
            TextArea textArea = (TextArea) tab.getContent();
            String content = textArea.getText();
            Files.writeString(file.toPath(), content);
            
            tab.setText(file.getName());
            tabFiles.put(tab, file);
            
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save file");
            alert.setContentText("The file could not be saved. Please check the file location and try again.");
            alert.showAndWait();
        }
    }

    public void onExit() {
        Platform.exit();
    }
}
