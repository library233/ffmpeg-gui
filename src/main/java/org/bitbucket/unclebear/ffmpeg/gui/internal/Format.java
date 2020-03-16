package org.bitbucket.unclebear.ffmpeg.gui.internal;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Format {
    String name;
    String extension;
    Map<Quality, List<String>> qualities;

    public static class Builder {
        String name;
        String extension;
        Map<Quality, List<String>> qualities = new ConcurrentHashMap<>();

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withExtension(String extension) {
            this.extension = extension;
            return this;
        }

        public Builder addQuality(Quality quality, List<String> parameters) {
            this.qualities.put(quality, parameters);
            return this;
        }

        Format build() throws IllegalAccessException {
            if (StringUtils.isAnyBlank(name, extension)) {
                throw new IllegalAccessException();
            }
            if (qualities.isEmpty()) {
                throw new IllegalAccessException();
            }
            for (Map.Entry<Quality, List<String>> entry : qualities.entrySet()) {
                List<String> strings = entry.getValue();
                if (strings.isEmpty()) {
                    throw new IllegalAccessException();
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
