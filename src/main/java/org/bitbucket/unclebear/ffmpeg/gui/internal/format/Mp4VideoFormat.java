package org.bitbucket.unclebear.ffmpeg.gui.internal.format;

import java.util.List;
import java.util.Map;

class Mp4VideoFormat implements Format {
    private static final Map<Profile, List<String>> parameters = Map.of(
            Profile.VERY_HIGH_VIDEO_QUALITY, List.of(
                    "-c:v", "libx265",
                    "-crf", "28",
                    "-vf", "scale=-2:1080",
                    "-r", "48",
                    "-c:a", "aac",
                    "-b:a", "192k",
                    "-ar", "48000",
                    "-ac", "2"
            ),
            Profile.HIGH_VIDEO_QUALITY, List.of(
                    "-c:v", "libx265",
                    "-crf", "28",
                    "-vf", "scale=-2:720",
                    "-r", "24",
                    "-c:a", "aac",
                    "-b:a", "128k",
                    "-ar", "32000",
                    "-ac", "2"
            ),
            Profile.BALANCED_VIDEO_QUALITY_AND_FILE_SIZE, List.of(
                    "-c:v", "libx265",
                    "-crf", "30",
                    "-vf", "scale=-2:576",
                    "-r", "24",
                    "-c:a", "aac",
                    "-b:a", "96k",
                    "-ar", "24000",
                    "-ac", "2"
            ),
            Profile.SMALL_FILE_SIZE, List.of(
                    "-c:v", "libx265",
                    "-crf", "30",
                    "-vf", "scale=-2:480",
                    "-r", "20",
                    "-c:a", "aac",
                    "-b:a", "48k",
                    "-ar", "24000",
                    "-ac", "1"
            ),
            Profile.VERY_SMALL_FILE_SIZE, List.of(
                    "-c:v", "libx265",
                    "-crf", "32",
                    "-vf", "scale=-2:360",
                    "-r", "20",
                    "-c:a", "aac",
                    "-b:a", "32k",
                    "-ar", "16000",
                    "-ac", "1"
            )
    );

    @Override
    public String getDescription() {
        return "MP4 Video (HEVC Video + AAC Audio)";
    }

    @Override
    public List<String> getParameters(Profile profile) {
        return parameters.get(profile);
    }
}
