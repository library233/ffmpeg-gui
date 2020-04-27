package org.bitbucket.unclebear.ffmpeg.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.bitbucket.unclebear.ffmpeg.gui.constant.Format;
import org.bitbucket.unclebear.ffmpeg.gui.constant.Quality;
import org.bitbucket.unclebear.ffmpeg.gui.internal.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.ResourceBundle;

public class NewTaskPopup implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> formats = FXCollections.observableArrayList(
                Format.WEBM.getDescription(),
                Format.MP4.getDescription(),
                Format.OPUS.getDescription(),
                Format.AAC.getDescription()
        );
        format.setItems(formats);
        format.setValue(Format.MP4.getDescription());
        ObservableList<String> qualities = FXCollections.observableArrayList(
                Quality.Q1.getDescription(),
                Quality.Q2.getDescription(),
                Quality.Q3.getDescription(),
                Quality.Q4.getDescription(),
                Quality.Q5.getDescription()
        );
        quality.setItems(qualities);
        quality.setValue(Quality.Q3.getDescription());
    }

    public void close() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    public void submit() {
        Task task = new Task(input.getText(), output.getText(), format.getValue(), quality.getValue());
        if (task.isValid()) {
            log.info("Valid {}", task);
            close();
        } else {
            log.info("Invalid {}", task);
        }
    }

    public void chooseInputFile() throws IOException {
        File file = new FileChooser().showOpenDialog(root.getScene().getWindow());
        if (file == null) {
            return;
        }
        if (!file.isFile()) {
            return;
        }
        input.setText(file.getCanonicalPath());
        output.setText(getOutputFile(file).getCanonicalPath());
    }

    public void selectFormat() {
    }

    public void selectQuality() {
    }

    public void chooseOutputFile() throws IOException {
        File file = new FileChooser().showSaveDialog(root.getScene().getWindow());
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
