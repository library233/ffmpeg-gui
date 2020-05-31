package org.bitbucket.unclebear.ffmpeg.gui.internal;

public class CliException extends Exception {
    private int status = -1;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
