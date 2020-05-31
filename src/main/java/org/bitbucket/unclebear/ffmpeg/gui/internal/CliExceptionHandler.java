package org.bitbucket.unclebear.ffmpeg.gui.internal;

@FunctionalInterface
public interface CliExceptionHandler {
    void handle(CliException exception);
}
