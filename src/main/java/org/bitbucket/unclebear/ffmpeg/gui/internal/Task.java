package org.bitbucket.unclebear.ffmpeg.gui.internal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Task {
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
