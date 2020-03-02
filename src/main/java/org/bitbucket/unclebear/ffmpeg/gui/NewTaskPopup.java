package org.bitbucket.unclebear.ffmpeg.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.bitbucket.unclebear.ffmpeg.gui.internal.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

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

    public void chooseInputFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        String path = file.getCanonicalPath();
        input.setText(path);
        output.setText(getOutputFile(file).getCanonicalPath());
    }

    public void chooseOutputFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(root.getScene().getWindow());
        if (isSameAsInputFile(file)) {
            file = getOutputFile(file);
        }
        output.setText(file.getCanonicalPath());
    }

    private File getOutputFile(File inputFile) throws IOException {
        int i = 0;
        File outputFile = inputFile;
        while (outputFile.isFile()) {
            String path = outputFile.getCanonicalPath();
            String extension = FilenameUtils.getExtension(path);
            String string = FilenameUtils.removeExtension(path);
            outputFile = new File(string + " (" + ++i + ")." + extension);
        }
        return outputFile;
    }

    private boolean isSameAsInputFile(File outputFile) throws IOException {
        File inputFile = new File(input.getText());
        return inputFile.isFile() && Files.isSameFile(Path.of(inputFile.toURI()), Path.of(outputFile.toURI()));
    }
}
