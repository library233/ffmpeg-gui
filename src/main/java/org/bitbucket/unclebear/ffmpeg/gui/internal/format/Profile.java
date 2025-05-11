package org.bitbucket.unclebear.ffmpeg.gui.internal.format;

import java.util.List;

public enum Profile {
    VERY_HIGH_QUALITY("Very high quality"),
    HIGH_QUALITY("High quality"),
    BALANCED("Balanced"),
    SMALL_FILE_SIZE("Small file size"),
    VERY_SMALL_FILE_SIZE("Very small file size");

    private static final List<Profile> ALL = List.of(
            BALANCED,
            HIGH_QUALITY,
            VERY_HIGH_QUALITY,
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
