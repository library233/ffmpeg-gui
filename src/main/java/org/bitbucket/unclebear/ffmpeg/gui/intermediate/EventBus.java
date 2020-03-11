package org.bitbucket.unclebear.ffmpeg.gui.intermediate;

import java.util.concurrent.Callable;

public enum EventBus {
    instance;

    public EventBus getInstance() {
        return instance;
    }

    public void emit(String channel, Object event) {
    }

    public void subscribe(String channel, Callable<Object> callable) {
    }
}
