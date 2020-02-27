package org.bitbucket.unclebear.ffmpeg.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("FFmpeg GUI");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("app.fxml")), 640, 480));
        stage.show();
    }
}
