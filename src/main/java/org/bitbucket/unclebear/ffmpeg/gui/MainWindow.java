package org.bitbucket.unclebear.ffmpeg.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainWindow {
    static Stage stage;

    static void show() throws Exception {
        URL resource = MainWindow.class.getResource("MainWindow.fxml");
        System.out.println(resource);
        Scene scene = new Scene(FXMLLoader.load(resource));
        stage.setScene(scene);
        stage.setTitle("FFmpeg GUI");
        stage.show();
    }

    public void showNewTaskPopup() throws Exception {
        NewTaskPopup.stage = stage;
        NewTaskPopup.show();
    }
}
