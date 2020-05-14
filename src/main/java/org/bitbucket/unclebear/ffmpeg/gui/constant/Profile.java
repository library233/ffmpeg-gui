package org.bitbucket.unclebear.ffmpeg.gui.constant;

public enum Profile {
    VERY_HIGH_QUALITY("Very High Quality"),
    HIGH_QUALITY("High Quality"),
    BALANCED("Balanced"),
    SMALL_FILE("Small File"),
    VERY_SMALL_FILE("Very Small File");

    private final String description;

    Profile(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
