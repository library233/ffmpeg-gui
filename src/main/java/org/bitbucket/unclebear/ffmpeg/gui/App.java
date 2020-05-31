package org.bitbucket.unclebear.ffmpeg.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bitbucket.unclebear.ffmpeg.gui.internal.FFmpeg;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FFmpeg.init();
        showMainWindow(stage);
    }

    private void showMainWindow(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(MainWindow.FXML)));
        stage.setTitle(MainWindow.TITLE);
        stage.show();
    }
}
