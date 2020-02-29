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
        initMainWindow(stage);
    }

    private void initMainWindow(Stage stage) throws java.io.IOException {
        stage.setTitle("FFmpeg GUI");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("MainWindow.fxml"))));
        stage.show();
    }
}
