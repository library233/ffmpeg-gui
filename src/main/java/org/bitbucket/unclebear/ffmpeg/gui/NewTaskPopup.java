package org.bitbucket.unclebear.ffmpeg.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.net.URL;

public class NewTaskPopup {
    static final URL FXML = NewTaskPopup.class.getResource("NewTaskPopup.fxml");
    static final String TITLE = "New Task";
    @FXML
    private Node root;

    public void close() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }
}
