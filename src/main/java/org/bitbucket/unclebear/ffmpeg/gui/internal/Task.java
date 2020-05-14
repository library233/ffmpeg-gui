package org.bitbucket.unclebear.ffmpeg.gui.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task {
    private static final Logger log = LoggerFactory.getLogger(Task.class);
    private final String input;
    private final String output;
    private final String format;
    private final String profile;

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
        return StringUtils.isNotBlank(input);
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
