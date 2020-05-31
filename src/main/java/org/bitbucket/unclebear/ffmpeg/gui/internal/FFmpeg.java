package org.bitbucket.unclebear.ffmpeg.gui.internal;

import org.bitbucket.unclebear.ffmpeg.gui.intermediate.Channel;
import org.bitbucket.unclebear.ffmpeg.gui.intermediate.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FFmpeg {
    private static final Logger log = LoggerFactory.getLogger(FFmpeg.class);

    public static void init() {
        EventBus.subscribe(new Channel(FFmpeg.class.getCanonicalName()), (event) -> log.info(event.getMessage()));
    }
}
