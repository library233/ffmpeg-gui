#!/bin/bash

main() {
    setup
    local log_file=${log_dir}/$(date '+%Y%m%d-%H%M%S-%N')-main.log
    log_info "log file created at ${log_file}"
    for item in "${@}"
    do
        process_item "${item}"
    done | tee "${log_file}"
    log_info "finished"
    exit 0
}

setup() {
    which ffmpeg ffprobe >/dev/null || exit 1
    if [[ -z ${ffmpeg_output_video_extension} || -z ${ffmpeg_output_video_encoder} ]]
    then
        ffmpeg_output_video_extension=webm
        ffmpeg_output_video_encoder=libsvtav1
    fi
    if [[ -z ${ffmpeg_output_audio_extension} || -z ${ffmpeg_output_audio_encoder} ]]
    then
        ffmpeg_output_audio_extension=opus
        ffmpeg_output_audio_encoder=libopus
    fi
    if [[ -z ${ffmpeg_output_suffix} ]]
    then
        ffmpeg_output_suffix=output
    fi
    log_dir=$(mktemp -dt ffmpeg-$(date '+%Y%m%d-%H%M%S')-XXXXXXXX)
}

process_item() {
    if [[ ! -r "${1}" ]]
    then
        file "${1}"
        return 1
    fi
    if [[ -d "${1}" ]]
    then
        process_directory "${1}"
    elif [[ -f "${1}" ]]
    then
        process_file "${1}"
    else
        return 1
    fi
}

process_directory() {
    cd "${1}" || return 1
    local output_directory="../$(basename "${1}")-${ffmpeg_output_suffix}"
    while read -d $'\0' file
    do
        process_file_to_directory "${file#./}" "${output_directory}"
    done < <(find . -type f -print0)
    wait
    cd - >/dev/null
}

process_file_to_directory() {
    mkdir -p "${2}/$(dirname "${1}")" || continue
    local extension=$(get_output_extension "${1}")
    local output="${2}/${1%.*}.${extension}"
    if [[ ${extension} == "" ]]
    then
        copy "${1}" "${2}/${1}"
    elif [[ -e "${output}" && ! -e "${output}.tmp" ]]
    then
        skip "${1}"
    elif ! process "${1}" "${output}"
    then
        remove "${output}"
        copy "${1}" "${2}/${1}"
    fi
}

process_file() {
    cd "$(dirname "${1}")" || return 1
    local file=$(basename "${1}")
    local file=${file#./}
    local extension=$(get_output_extension "${file}")
    local output="${file%.*}-${ffmpeg_output_suffix}.${extension}"
    if [[ ${extension} == "" ]]
    then
        skip "${file}"
    elif [[ -e "${output}" && ! -e "${output}.tmp" ]]
    then
        skip "${file}"
    elif ! process "${file}" "${output}"
    then
        remove "${output}"
        skip "${file}"
    fi
    cd - >/dev/null
}

get_output_extension() {
    if has_moving_video_stream "${1}"
    then
        echo "${ffmpeg_output_video_extension}"
    elif has_audio_stream "${1}"
    then
        echo "${ffmpeg_output_audio_extension}"
    else
        return 1
    fi
}

has_moving_video_stream() {
    has_stream video "${1}" && is_moving "${1}"
}

has_audio_stream() {
    has_stream audio "${1}"
}

has_stream() {
    ffprobe -loglevel error -select_streams "${1:0:1}" -show_entries stream=codec_type -of csv=p=0 "${2}" 2>/dev/null | grep -sq "${1}"
}

is_moving() {
    [[ $(ffprobe -loglevel error -select_streams v -count_packets -show_entries stream=nb_read_packets -of csv=p=0 "${1}" 2>/dev/null | grep -Eo '[0-9]+' | head -n 1) -gt 1 ]]
}

find() {
    /bin/find "${@}"
}

process() {
    log_info "saving as ${2}"
    ffmpeg -nostdin ${ffmpeg_input_options} -i "${1}" -map_metadata -1 -map 0:a? -map 0:v? -map 0:s? -c:a "${ffmpeg_output_audio_encoder}" -c:v "${ffmpeg_output_video_encoder}" $(has_moving_video_stream "${1}" || echo -vn) -y ${ffmpeg_output_options} "${2}" </dev/null >${log_dir}/$(date '+%Y%m%d-%H%M%S-%N')-process.log 2>&1
}

remove() {
    log_warn "removing ${1}"
    rm -f "${1}"
}

copy() {
    log_warn "copying to ${2}"
    touch "${2}.tmp"
    cp -np "${1}" "${2}"
}

skip() {
    log_warn "skipped ${1}"
}

log_info() {
    log info "${@}"
}

log_warn() {
    log warn "${@}"
}

log() {
    printf '%s [%s] %s\n' "$(now)" "${1^^}" "${2}"
}

now() {
    date "+%Y-%m-%d %H:%M:%S"
}

main "${@}"
