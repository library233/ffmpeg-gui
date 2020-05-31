package org.bitbucket.unclebear.ffmpeg.gui.internal.format;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FormatFactory {
    private static final List<Format> ALL = List.of(
            new Mp4VideoFormat(),
            new WebMVideoFormat(),
            new MkvVideoFormat(),
            new OggVideoFormat(),
            new LegacyMp4VideoFormat(),
            new LegacyMkvVideoFormat(),
            new AacAudioFormat(),
            new Mp3AudioFormat(),
            new OpusAudioFormat(),
            new OggVorbisAudioFormat()
    );
    private static final List<String> PARAMETER_PREFIX = List.of(
            "-loglevel", "repeat+level+warning",
            "-i"
    );
    private static final List<String> PARAMETER_SUFFIX = List.of(
            "-hide_banner",
            "-map_metadata", "-1",
            "-y"
    );

    private FormatFactory() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static List<Format> getAll() {
        return ALL;
    }

    public static List<String> getParameters(String formatDescription, String profileDescription) {
        Format format = ALL.stream().parallel()
                .filter(it -> StringUtils.equals(formatDescription, it.getDescription()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(formatDescription));
        Profile profile = Profile.getAll().stream().parallel()
                .filter(it -> StringUtils.equals(profileDescription, it.getDescription()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(profileDescription));
        List<String> parameters = format.getParameters(profile);
        if (parameters == null || parameters.isEmpty()) {
            throw new IllegalArgumentException(String.format("%s (%s)", format, profile.getDescription()));
        }
        List<String> result = new ArrayList<>();
        result.addAll(PARAMETER_PREFIX);
        result.addAll(parameters);
        result.addAll(PARAMETER_SUFFIX);
        return result;
    }
}
