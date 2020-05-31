package org.bitbucket.unclebear.ffmpeg.gui.internal.format;

import java.util.List;

public enum Profile {
    VERY_HIGH_VIDEO_QUALITY("Very High Video Quality"),
    HIGH_VIDEO_QUALITY("High Video Quality"),
    BALANCED_VIDEO_QUALITY_AND_FILE_SIZE("Balanced Video Quality and File Size"),
    SMALL_FILE_SIZE("Small File Size"),
    VERY_SMALL_FILE_SIZE("Very Small File Size");

    private static final List<Profile> ALL = List.of(
            BALANCED_VIDEO_QUALITY_AND_FILE_SIZE,
            HIGH_VIDEO_QUALITY,
            VERY_HIGH_VIDEO_QUALITY,
            SMALL_FILE_SIZE,
            VERY_SMALL_FILE_SIZE
    );
    private final String description;

    Profile(String description) {
        this.description = description;
    }

    public static List<Profile> getAll() {
        return ALL;
    }

    public String getDescription() {
        return description;
    }
}
