package org.bitbucket.unclebear.ffmpeg.gui.intermediate;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public enum EventBus {
    instance;

    private static Map<String, Queue<Object>> events = new ConcurrentHashMap<>();
    private static Map<String, List<Callable<Object>>> listeners = new ConcurrentHashMap<>();

    public EventBus getInstance() {
        return instance;
    }

    public void emit(String channel, Object event) {
    }

    public void subscribe(String channel, Callable<Object> callable) {
    }

    public void unsubscribe(String channel) {
    }
}
