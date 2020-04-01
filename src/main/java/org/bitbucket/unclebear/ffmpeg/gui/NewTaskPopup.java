package org.bitbucket.unclebear.ffmpeg.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.bitbucket.unclebear.ffmpeg.gui.internal.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

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
        if (file == null) {
            return;
        }
        String path = file.getCanonicalPath();
        input.setText(path);
        ObservableList<String> formats = FXCollections.observableArrayList("WebM (AV1 Video + Opus Audio)", "MP4 (HEVC Video + AAC Audio)", "Opus Audio", "AAC Audio");
        format.setItems(formats);
    }

    public void selectFormat() throws IOException {
        String path = input.getText();
        File file = new File(path);
        if (file.isFile()) {
            output.setText(getOutputFile(file).getCanonicalPath());
        }
    }

    public void selectQuality() {}

    public void chooseOutputFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(root.getScene().getWindow());
        if (file == null) {
            return;
        }
        if (isSameAsInputFile(file)) {
            file = getOutputFile(file);
        }
        output.setText(file.getCanonicalPath());
    }

    private File getOutputFile(File inputFile) throws IOException {
        File outputFile = inputFile;
        outputFile = appendSuffixIfExist(outputFile);
        return outputFile;
    }

    private File appendSuffixIfExist(File file) throws IOException {
        int i = 0;
        while (file.isFile()) {
            String path = file.getCanonicalPath();
            String directory = file.getParent();
            String name = Optional.of(FilenameUtils.getBaseName(path))
                    .filter(StringUtils::isNotBlank)
                    .orElse("Unnamed");
            String extension = Optional.of(FilenameUtils.getExtension(path))
                    .filter(StringUtils::isNotBlank)
                    .orElse("dat");
            file = new File(FilenameUtils.normalize(directory + File.separator + name + " (" + ++i + ")." + extension));
        }
        return file;
    }

    private boolean isSameAsInputFile(File outputFile) throws IOException {
        File inputFile = new File(input.getText());
        return inputFile.isFile() && Files.isSameFile(Path.of(inputFile.toURI()), Path.of(outputFile.toURI()));
    }
}
