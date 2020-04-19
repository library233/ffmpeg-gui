package org.bitbucket.unclebear.ffmpeg.gui.internal;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Format {
    private static final String OPTIMIZED_QUALITY = "Optimized Quality";
    private static final String BETTER_QUALITY = "Better Quality";
    private static final String BALANCED = "Balanced";
    private static final String SMALLER_SIZE = "Smaller Size";
    private static final String OPTIMIZED_SIZE = "Optimized Size";
    private String name;
    private String extension;
    private Map<String, List<String>> qualities;

    private Format() {
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }

    public Map<String, List<String>> getQualities() {
        return qualities;
    }

    static class Builder {
        private String name;
        private String extension;
        private Map<String, List<String>> qualities = new ConcurrentHashMap<>();

        Builder withName(String name) {
            this.name = name;
            return this;
        }

        Builder withExtension(String extension) {
            this.extension = extension;
            return this;
        }

        Builder addQuality(String description, boolean video, int videoBitRate, int maxWidth, int maxHeight, boolean audio, int audioBitRate, int sampleRate) {
            return this;
        }

        Format build() {
            if (StringUtils.isAnyBlank(name, extension)) {
                throw new IllegalStateException();
            }
            if (qualities.isEmpty()) {
                throw new IllegalStateException();
            }
            for (Map.Entry<String, List<String>> entry : qualities.entrySet()) {
                List<String> strings = entry.getValue();
                if (strings.isEmpty()) {
                    throw new IllegalStateException();
                }
            }
            Format format = new Format();
            format.name = name;
            format.extension = extension;
            format.qualities = qualities;
            return format;
        }
    }
}
