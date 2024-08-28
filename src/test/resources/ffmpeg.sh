#!/bin/bash

main() {
    setup
    log_debug "ffmpeg_command_args=${ffmpeg_command_args}"
    log_debug "ffmpeg_input_opts=${ffmpeg_input_opts}"
    log_debug "ffmpeg_output_opts=${ffmpeg_output_opts}"
    log_debug "ffmpeg_output_video_encoder=${ffmpeg_output_video_encoder}"
    log_debug "ffmpeg_output_video_extension=${ffmpeg_output_video_extension}"
    log_debug "ffmpeg_output_audio_encoder=${ffmpeg_output_audio_encoder}"
    log_debug "ffmpeg_output_audio_extension=${ffmpeg_output_audio_extension}"
    log_debug "ffmpeg_output_suffix=${ffmpeg_output_suffix}"
    log_debug "log_dir=${log_dir}"
    total=$(find "${@}" -type f -printf . | wc -c)
    log_debug "processing ${total} task$([[ ${total} -gt 1 ]] && echo s)"
    count=0
    local started=$(date +%s)
    for item in "${@}"
    do
        process_item "${item}"
    done
    local duration=$(($(date +%s) - ${started}))
    log_debug "finished ${total} task$([[ ${total} -gt 1 ]] && echo s) in ${duration} second$([[ ${duration} -gt 1 ]] && echo s)"
    log_debug "produced $(for level in info warn error; do printf "%s * %d + " ${level^^} $(grep -Ec "^[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}  ?${level^^} " "${log_file}" 2>/dev/null); done | sed 's/ + $//g')"
    exit 0
}

setup() {
    which ffmpeg ffprobe >/dev/null || exit 1
    log_dir=$(mktemp -dt ffmpeg-$(date '+%Y%m%d-%H%M%S')-XXXXXXXX)
    log_file=${log_dir}/main-$(date '+%Y%m%d-%H%M%S-%N').log
    if [[ -n ${ffmpeg_command_args} ]]
    then
        local args=(${ffmpeg_command_args//,/ })
        ffmpeg_output_video_encoder=${args[1]}
        ffmpeg_output_video_extension=${args[2]}
        ffmpeg_output_audio_encoder=${args[3]}
        ffmpeg_output_audio_extension=${args[4]}
    fi
    if [[ -z ${ffmpeg_output_video_encoder} || -z ${ffmpeg_output_video_extension} ]]
    then
        ffmpeg_output_video_encoder=libsvtav1
        ffmpeg_output_video_extension=webm
    fi
    if [[ -z ${ffmpeg_output_audio_encoder} || -z ${ffmpeg_output_audio_extension} ]]
    then
        ffmpeg_output_audio_encoder=libopus
        ffmpeg_output_audio_extension=opus
    fi
    if [[ -z ${ffmpeg_output_suffix} ]]
    then
        ffmpeg_output_suffix=output
    fi
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
    else
        process "${1}" "${output}" || remove "${output}"
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
    else
        process "${file}" "${output}" || remove "${output}"
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
    log_info "$((++count))/${total} saving \"${1}\" as \"${2}\""
    touch "${2}.tmp"
    ffmpeg -nostdin ${ffmpeg_input_opts} -i "${1}" -map_metadata -1 -map 0:v? -map 0:a? -map 0:s? -c:v "${ffmpeg_output_video_encoder}" $(has_moving_video_stream "${1}" || echo -vn) -c:a "${ffmpeg_output_audio_encoder}" -c:s copy -y ${ffmpeg_output_opts} "${2}" </dev/null >${log_dir}/process-$(date '+%Y%m%d-%H%M%S-%N')-$((${RANDOM} % 30000 + 10000)).log 2>&1 && rm -f "${2}.tmp"
}

copy() {
    log_warn "$((++count))/${total} copying \"${1}\" to \"${2}\""
    touch "${2}.tmp"
    cp -np "${1}" "${2}" && rm -f "${2}.tmp"
}

skip() {
    log_warn "$((++count))/${total} skipped \"${1}\""
}

remove() {
    log_error "removing \"${1}\""
    rm -f "${1}"
}

log_debug() {
    log debug "${@}"
}

log_info() {
    log info "${@}"
}

log_warn() {
    log warn "${@}"
}

log_error() {
    log error "${@}"
}

log() {
    printf '%s %5s %s\n' "$(now)" "${1^^}" "${2}" | tee -a "${log_file}"
}

now() {
    date "+%m-%d %H:%M"
}

main "${@}"
