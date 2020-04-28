package org.bitbucket.unclebear.ffmpeg.gui.constant;

public enum Quality {
    Q1("Exceptional Quality"),
    Q2("Good Quality"),
    Q3("Acceptable Quality"),
    Q4("Small File Size"),
    Q5("Tiny File Size");

    private final String description;

    Quality(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
