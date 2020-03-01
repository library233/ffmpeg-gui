package org.bitbucket.unclebear.ffmpeg.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow {
    static Stage stage;

    static void show() throws Exception {
        Scene scene = new Scene(FXMLLoader.load(MainWindow.class.getResource("MainWindow.fxml")));
        stage.setScene(scene);
        stage.setTitle("FFmpeg GUI");
        stage.show();
    }

    public void showNewTaskPopup() throws Exception {
        NewTaskPopup.stage = stage;
        NewTaskPopup.show();
    }
}
