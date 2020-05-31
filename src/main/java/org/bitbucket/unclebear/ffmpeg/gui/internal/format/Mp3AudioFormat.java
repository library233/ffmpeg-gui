package org.bitbucket.unclebear.ffmpeg.gui.internal.format;

import java.util.List;
import java.util.Map;

class Mp3AudioFormat implements Format {
    private static final Map<Profile, List<String>> parameters = Map.of(
            Profile.VERY_HIGH_VIDEO_QUALITY, List.of(),
            Profile.HIGH_VIDEO_QUALITY, List.of(),
            Profile.BALANCED_VIDEO_QUALITY_AND_FILE_SIZE, List.of(),
            Profile.SMALL_FILE_SIZE, List.of(),
            Profile.VERY_SMALL_FILE_SIZE, List.of()
    );

    @Override
    public String getDescription() {
        return "MP3 Audio";
    }

    @Override
    public List<String> getParameters(Profile profile) {
        return parameters.get(profile);
    }
}
