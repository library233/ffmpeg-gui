package org.bitbucket.unclebear.ffmpeg.gui.intermediate;

import java.util.Objects;

public class Channel {
    private final String name;

    public Channel(String name) {
        Objects.requireNonNull(name, "Channel name cannot be null");
        this.name = name.intern();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Objects.equals(name, ((Channel) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
