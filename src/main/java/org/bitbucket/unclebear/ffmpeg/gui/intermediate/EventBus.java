package org.bitbucket.unclebear.ffmpeg.gui.intermediate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public enum EventBus {
    instance;

    private static final Logger log = LoggerFactory.getLogger(EventBus.class);
    private static Map<String, Queue<Object>> events = new ConcurrentHashMap<>();
    private static Map<String, List<Callable<Object>>> listeners = new ConcurrentHashMap<>();

    static {
        log.info("Initializing");
    }

    public static EventBus getInstance() {
        return instance;
    }

    public void start() {
    }

    public void emit(String channel, Object event) {
    }

    public void subscribe(String channel, Callable<Object> callable) {
    }

    public void unsubscribe(String channel) {
    }
}
