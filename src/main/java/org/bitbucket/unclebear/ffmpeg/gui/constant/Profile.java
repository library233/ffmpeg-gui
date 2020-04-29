package org.bitbucket.unclebear.ffmpeg.gui.constant;

public enum Profile {
    P1("Excellent Quality"),
    P2("Good Quality"),
    P3("Acceptable Quality"),
    P4("Small Size"),
    P5("Tiny Size");

    private final String description;

    Profile(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
