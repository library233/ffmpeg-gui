package org.bitbucket.unclebear.ffmpeg.gui.internal.format;

import java.util.List;
import java.util.Map;

class WebMVideoFormat implements Format {
    private static final Map<Profile, List<String>> parameters = Map.of(
            Profile.VERY_HIGH_QUALITY, List.of(),
            Profile.HIGH_QUALITY, List.of(),
            Profile.BALANCED, List.of(),
            Profile.SMALL_FILE_SIZE, List.of(),
            Profile.VERY_SMALL_FILE_SIZE, List.of()
    );

    @Override
    public String getDescription() {
        return "WebM Video (AV1 Video + Opus Audio)";
    }

    @Override
    public List<String> getParameters(Profile profile) {
        return parameters.get(profile);
    }
}
