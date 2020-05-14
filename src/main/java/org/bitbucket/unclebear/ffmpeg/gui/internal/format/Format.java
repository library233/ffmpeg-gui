package org.bitbucket.unclebear.ffmpeg.gui.internal.format;

import org.bitbucket.unclebear.ffmpeg.gui.constant.Profile;

import java.util.List;

public interface Format {
    String getDescription();

    List<String> getParameters(Profile profile);
}
