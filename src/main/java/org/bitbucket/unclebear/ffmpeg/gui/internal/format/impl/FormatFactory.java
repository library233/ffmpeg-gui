package org.bitbucket.unclebear.ffmpeg.gui.internal.format.impl;

import org.bitbucket.unclebear.ffmpeg.gui.constant.Profile;
import org.bitbucket.unclebear.ffmpeg.gui.internal.format.Format;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FormatFactory {
    private static Map<Class<? extends Format>, Format> formats = Map.of(
            Mp4Video.class, new Mp4Video(),
            WebMVideo.class, new WebMVideo(),
            MkvVideo.class, new MkvVideo(),
            OggVideo.class, new OggVideo(),
            Mp4LegacyVideo.class, new Mp4LegacyVideo(),
            MkvLegacyVideo.class, new MkvLegacyVideo(),
            AacAudio.class, new AacAudio(),
            Mp3Audio.class, new Mp3Audio(),
            OpusAudio.class, new OpusAudio(),
            OggVorbisAudio.class, new OggVorbisAudio()
    );
    public static List<String> parameterPrefix = List.of(
            "-loglevel", "repeat+level+warning",
            "-i"
    );
    public static List<String> parameterSuffix = List.of(
            "-hide_banner",
            "-map_metadata", "-1",
            "-y"
    );

    private FormatFactory() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static List<String> getParameters(Class<Format> format, Profile profile) {
        if (!formats.containsKey(format)) {
            throw new IllegalArgumentException(format.getCanonicalName());
        }
        List<String> parameters = formats.get(format).getParameters(profile);
        if (parameters == null || parameters.isEmpty()) {
            throw new IllegalArgumentException(String.format("%s (%s)", format.getCanonicalName(), profile.getDescription()));
        }
        List<String> result = new ArrayList<>();
        result.addAll(parameterPrefix);
        result.addAll(parameters);
        result.addAll(parameterSuffix);
        return result;
    }
}
