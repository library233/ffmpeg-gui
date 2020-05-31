package org.bitbucket.unclebear.ffmpeg.gui.intermediate;

import java.util.Objects;

public class Event {
    private final String message;
    private final String source;

    public Event(String message, String source) {
        Objects.requireNonNull(message, "Event message cannot be null");
        this.message = message;
        this.source = source;
    }

    public Event(String message) {
        this(message, null);
    }

    public String getMessage() {
        return message;
    }

    public String getSource() {
        return source;
    }
}
