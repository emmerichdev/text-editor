package com.emmerichbrowne.texteditor;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

public class HelloController {

    @FXML
    private TabPane tabPane;

    public void initialize() {
        onNew();
    }

    public void onNew() {
        Tab tab = new Tab("Untitled");
        tab.setClosable(true);
        tab.setOnCloseRequest(event -> {
            if (tabPane.getTabs().size() == 1) {
                event.consume();
            }
        });
        tab.setContent(new TextArea());
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    public void onOpen() {
        System.out.println("Open");
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
