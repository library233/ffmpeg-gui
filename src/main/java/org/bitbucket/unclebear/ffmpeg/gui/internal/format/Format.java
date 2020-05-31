package org.bitbucket.unclebear.ffmpeg.gui.internal.format;

import java.util.List;

public interface Format {
    String getDescription();

    List<String> getParameters(Profile profile);
}
