package org.bitbucket.unclebear.ffmpeg.gui.constant;

public enum Format {
    V_WEBM("WebM Video (AV1 Video + Opus Audio)"),
    V_OGG("Ogg Video (Theora Video + Vorbis Audio)"),
    V_MP4("MP4 Video (HEVC Video + AAC Audio)"),
    V_MP4_LEGACY("MP4 Legacy Video (AVC Video + AAC Audio)"),
    V_MKV("MKV Video (VP9 Video + Opus Audio)"),
    V_MKV_LEGACY("MKV Legacy Video (VP8 Video + Opus Audio)"),
    A_OPUS("Opus Audio"),
    A_OGG("Ogg Vorbis Audio"),
    A_AAC("AAC Audio");

    private final String description;

    Format(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
