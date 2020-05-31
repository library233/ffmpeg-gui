package org.bitbucket.unclebear.ffmpeg.gui.internal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
    private static final Logger log = LoggerFactory.getLogger(Task.class);
    private String input;
    private String output;
    private String format;
    private String profile;

    public Task() {
    }

    public Task(String input, String output, String format, String profile) {
        this.input = input;
        this.output = output;
        this.format = format;
        this.profile = profile;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public String getFormat() {
        return format;
    }

    public String getProfile() {
        return profile;
    }

    public boolean isValid() {
        File inputFile = new File(input);
        if (!inputFile.isFile()) {
            log.warn("Input is not a normal file: " + input);
            return false;
        }
        if (!inputFile.canRead()) {
            log.warn("Cannot read input file: " + input);
            return false;
        }
        File outputFile = new File(output);
        if (outputFile.exists()) {
            if (!outputFile.isFile()) {
                log.warn("Output already exists and is not a normal file: " + input);
                return false;
            }
            if (!outputFile.canWrite()) {
                log.warn("Cannot overwrite already existing output file: " + output);
                return false;
            }
        } else {
            File parent = outputFile.getParentFile();
            if (!parent.canWrite()) {
                log.warn("Cannot write: " + parent.getPath());
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return super.toString();
        }
    }
}
