package org.bitbucket.unclebear.ffmpeg.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewTaskPopup {
    static Stage stage;

    static void show() throws Exception {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(NewTaskPopup.class.getResource("NewTaskPopup.fxml"))));
        stage.setTitle("New Task");
        stage.initOwner(MainWindow.stage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
