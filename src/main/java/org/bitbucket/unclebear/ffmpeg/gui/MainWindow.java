package org.bitbucket.unclebear.ffmpeg.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

public class MainWindow {
    static final URL FXML = MainWindow.class.getResource("MainWindow.fxml");
    static final String TITLE = "FFmpeg GUI";
    @FXML
    private Node root;

    public void showNewTaskPopup() throws Exception {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(NewTaskPopup.FXML)));
        stage.setResizable(false);
        stage.setTitle(NewTaskPopup.TITLE);
        stage.initOwner(root.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
