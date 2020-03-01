package org.bitbucket.unclebear.ffmpeg.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bitbucket.unclebear.ffmpeg.gui.internal.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class NewTaskPopup {
    static final URL FXML = NewTaskPopup.class.getResource("NewTaskPopup.fxml");
    static final String TITLE = "New Task";
    private static final Logger log = LoggerFactory.getLogger(NewTaskPopup.class);
    @FXML
    private Node root;
    @FXML
    private TextField input;
    @FXML
    private TextField output;
    @FXML
    private ChoiceBox<String> format;
    @FXML
    private ChoiceBox<String> quality;

    public void close() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    public void submit() {
        Task task = new Task(input.getText(), output.getText(), format.getValue(), quality.getValue());
        if (task.isValid()) {
            log.info("{} is valid", task);
            close();
        } else {
            log.info("{} is invalid", task);
        }
    }
}
