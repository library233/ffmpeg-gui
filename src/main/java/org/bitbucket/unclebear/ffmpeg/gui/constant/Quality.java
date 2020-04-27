package org.bitbucket.unclebear.ffmpeg.gui.constant;

public enum Quality {
    Q1("Exceptional Quality"),
    Q2("Acceptable Quality"),
    Q3("Balanced"),
    Q4("Small File"),
    Q5("Tiny File");

    private final String description;

    Quality(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
