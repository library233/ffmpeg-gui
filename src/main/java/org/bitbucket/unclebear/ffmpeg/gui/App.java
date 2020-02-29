package org.bitbucket.unclebear.ffmpeg.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        showMainWindow(stage);
    }

    private void showMainWindow(Stage stage) throws Exception {
        MainWindow.stage = stage;
        MainWindow.show();
    }
}
