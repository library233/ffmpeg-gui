package org.bitbucket.unclebear.ffmpeg.gui.constant;

public enum Format {
    WEBM("WebM (AV1 Video + Opus Audio)"),
    MP4("MP4 (HEVC Video + AAC Audio)"),
    OPUS("Opus Audio"),
    AAC("AAC Audio");

    private final String description;

    Format(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
