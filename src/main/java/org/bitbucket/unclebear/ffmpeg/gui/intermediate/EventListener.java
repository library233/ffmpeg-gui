package org.bitbucket.unclebear.ffmpeg.gui.intermediate;

@FunctionalInterface
public interface EventListener {
    void on(Event event) throws Exception;
}
