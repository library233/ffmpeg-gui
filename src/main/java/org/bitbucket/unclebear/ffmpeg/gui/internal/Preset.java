package org.bitbucket.unclebear.ffmpeg.gui.internal;

import java.util.List;

public class Preset {
    public static List<Format> formats = List.of(webM());

    private static Format webM() {
        return new Format.Builder()
                .withName("WebM (AV1 Video + Opus Audio)")
                .withExtension("webm")
                .build();
    }
}
