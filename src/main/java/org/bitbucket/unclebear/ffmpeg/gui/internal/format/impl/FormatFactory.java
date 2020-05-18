package org.bitbucket.unclebear.ffmpeg.gui.internal.format.impl;

import org.bitbucket.unclebear.ffmpeg.gui.internal.format.Format;

import java.util.List;

public class FormatFactory {
    public static List<String> parametersPrefix = List.of(
            "-loglevel", "repeat+level+warning",
            "-i"
    );
    public static List<String> parametersSuffix = List.of(
            "-hide_banner",
            "-map_metadata", "-1",
            "-y"
    );

    private FormatFactory() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static List<Format> getAll() {
        return List.of(
                new Mp4Video(),
                new WebMVideo(),
                new MkvVideo(),
                new OggVideo(),
                new Mp4LegacyVideo(),
                new MkvLegacyVideo(),
                new AacAudio(),
                new Mp3Audio(),
                new OpusAudio(),
                new OggVorbisAudio()
        );
    }
}
