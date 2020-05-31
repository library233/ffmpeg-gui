package org.bitbucket.unclebear.ffmpeg.gui.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bitbucket.unclebear.ffmpeg.gui.intermediate.Channel;
import org.bitbucket.unclebear.ffmpeg.gui.intermediate.Event;
import org.bitbucket.unclebear.ffmpeg.gui.intermediate.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FFmpeg {
    private static final Logger log = LoggerFactory.getLogger(FFmpeg.class);

    public static void init() {
        EventBus.subscribe(new Channel(FFmpeg.class.getCanonicalName()), FFmpeg::listen);
    }

    private static void listen(Event event) {
        try {
            start(new ObjectMapper().readValue(event.getMessage(), Task.class));
        } catch (Exception e) {
            log.error("Failed to process task: " + e.getMessage(), e);
        }
    }

    private static void start(Task task) {
        log.info(task.toString());
    }
}
