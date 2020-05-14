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
import org.bitbucket.unclebear.ffmpeg.gui.constant.Profile;
import org.bitbucket.unclebear.ffmpeg.gui.internal.Task;
import org.bitbucket.unclebear.ffmpeg.gui.internal.format.Format;
import org.bitbucket.unclebear.ffmpeg.gui.internal.format.impl.FormatFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    private ChoiceBox<String> profile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> formats = FXCollections.observableList(FormatFactory.getAll().stream().map(Format::getDescription).collect(Collectors.toList()));
        format.setItems(formats);
        format.setValue(formats.get(0));
        ObservableList<String> profiles = FXCollections.observableArrayList(
                Profile.BALANCED.getDescription(),
                Profile.HIGH_QUALITY.getDescription(),
                Profile.VERY_HIGH_QUALITY.getDescription(),
                Profile.SMALL_FILE.getDescription(),
                Profile.VERY_SMALL_FILE.getDescription()
        );
        profile.setItems(profiles);
        profile.setValue(profiles.get(0));
    }

    public void close() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    public void submit() {
        Task task = new Task(input.getText(), output.getText(), format.getValue(), profile.getValue());
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

    public void selectProfile() {
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
