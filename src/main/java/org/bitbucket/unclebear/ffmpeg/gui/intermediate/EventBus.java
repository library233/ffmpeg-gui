package org.bitbucket.unclebear.ffmpeg.gui.intermediate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public enum EventBus {
    instance;

    private static final Logger log = LoggerFactory.getLogger(EventBus.class);
    private static final Map<Channel, BlockingQueue<Event>> eventQueues = new ConcurrentHashMap<>();
    private static final Map<Channel, List<EventListener>> eventListeners = new ConcurrentHashMap<>();
    private static final Thread thread = new Thread(EventBus::walkAround);
    private static final BlockingQueue<Object> doze = new LinkedBlockingQueue<>();

    static {
        thread.setDaemon(true);
        thread.start();
    }

    public static void emit(Channel channel, Event event) {
        getEvents(channel).add(event);
        wake();
    }

    public static void subscribe(Channel channel, EventListener eventListener) {
        getEventListeners(channel).add(eventListener);
    }

    private static void walkAround() {
        while (true) {
            doze();
            eventQueues.entrySet().stream().parallel().forEach(EventBus::walkThrough);
        }
    }

    private static void wake() {
        doze.add(new Object());
    }

    private static void doze() {
        if (eventQueues.values().stream().parallel().allMatch(Collection::isEmpty)) {
            Object o = "Doze interrupted: ";
            try {
                o = doze.take();
            } catch (InterruptedException e) {
                log.error(o + e.getMessage(), e);
            }
        }
        doze.clear();
    }

    private static void walkThrough(Map.Entry<Channel, BlockingQueue<Event>> eventQueue) {
        while (true) {
            BlockingQueue<Event> events = eventQueue.getValue();
            if (events.isEmpty()) {
                break;
            }
            broadcast(eventQueue.getKey(), events);
        }
    }

    private static void broadcast(Channel channel, BlockingQueue<Event> events) {
        try {
            transmit(channel, events.take());
        } catch (InterruptedException e) {
            log.error("Interrupted while taking event: " + e.getMessage(), e);
        }
    }

    private static void transmit(Channel channel, Event event) {
        getEventListeners(channel).stream().parallel().forEach(eventListener -> dispatch(eventListener, event));
    }

    private static void dispatch(EventListener eventListener, Event event) {
        try {
            eventListener.on(event);
        } catch (Exception e) {
            log.error("Event listener throws exception: " + e.getMessage(), e);
        }
    }

    private static BlockingQueue<Event> getEvents(Channel channel) {
        if (eventQueues.containsKey(channel)) {
            return eventQueues.get(channel);
        }
        eventQueues.put(channel, new LinkedBlockingQueue<>());
        return getEvents(channel);
    }

    private static List<EventListener> getEventListeners(Channel channel) {
        if (eventListeners.containsKey(channel)) {
            return eventListeners.get(channel);
        }
        eventListeners.put(channel, new CopyOnWriteArrayList<>());
        return getEventListeners(channel);
    }
}
