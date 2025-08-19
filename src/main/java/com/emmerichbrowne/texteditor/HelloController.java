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

public class HelloController {

    @FXML
    private TabPane tabPane;

    public void initialize() {
        onNew();
    }

    private void newTab(String name, String content) {
        Tab tab = new Tab(name);
        tab.setClosable(true);
        tab.setOnCloseRequest(event -> {
            if (tabPane.getTabs().size() == 1) {
                event.consume();
            }
        });
        tab.setContent(new TextArea(content));
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    public void onNew() {
        newTab("Untitled", "");
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
                newTab(file.getName(), content);
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
        System.out.println("Save");
    }

    public void onSaveAs() {
        System.out.println("Save As");
    }

    public void onExit() {
        Platform.exit();
    }
}
