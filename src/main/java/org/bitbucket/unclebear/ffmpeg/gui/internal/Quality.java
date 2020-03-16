package org.bitbucket.unclebear.ffmpeg.gui.internal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public enum Quality {
    OPTIMIZE_QUALITY(9, "Optimize Quality"),
    BETTER_QUALITY(7, "Better Quality"),
    WELL_BALANCED(5, "Balanced"),
    SMALLER_FILE_SIZE(3, "Smaller Size"),
    OPTIMIZE_FILE_SIZE(1, "Optimize Size");

    int level;
    String name;

    Quality(int level, String name) {
        this.level = level;
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
