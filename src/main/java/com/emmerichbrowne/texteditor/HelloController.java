package com.emmerichbrowne.texteditor;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class HelloController {

    @FXML
    private TextArea textArea;

    public void onNew() {
        textArea.clear();
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
