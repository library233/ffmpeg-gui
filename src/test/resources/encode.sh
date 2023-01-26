#!/bin/sh

main() {
    which ffmpeg ffprobe >/dev/null || exit 1
    for arg in "${@}"
    do
        encode_all "${arg}"
    done
    exit 0
}

encode_all() {
    if [[ ! -r "${1}" ]]
    then
        file "${1}"
        return 1
    fi
    local suffix=encoded-$(date +%Y%m%d%H%M%S)
    if [[ -d "${1}" ]]
    then
        encode_directory "${1}" "${suffix}"
    elif [[ -f "${1}" ]]
    then
        encode_file "${1}" "${suffix}"
    else
        return 1
    fi
}

encode_directory() {
    cd "${1}" || return 1
    local output_directory="../$(basename "${1}")-${2}"
    find . -type f -print0 | while read -d $'\0' file
    do
        local file=${file#./}
        mkdir -p "${output_directory}/$(dirname "${file}")" || continue
        local extension=$(get_output_extension "${file}")
        if [[ ${extension} != "" ]]
        then
            encode "${file}" "${output_directory}/${file%.*}.${extension}"
        else
            copy "${file}" "${output_directory}/${file}"
        fi
    done
    cd - >/dev/null
}

encode_file() {
    cd "$(dirname "${1}")" || return 1
    local file=$(basename "${1}")
    local file=${file#./}
    local extension=$(get_output_extension "${file}")
    if [[ ${extension} != "" ]]
    then
        local output="${file%.*}-${2}.${extension}"
        encode "${file}" "${output}"
    else
        skip "${file}"
    fi
    cd - >/dev/null
}

get_output_extension() {
    if is_video "${1}"
    then
        echo mp4
    elif is_audio "${1}"
    then
        echo aac
    else
        return 1
    fi
}

is_video () {
    if is_type video "${1}" && is_non_static "${1}"
    then
        return 0
    fi
    return 1
}

is_audio() {
    if is_type audio "${1}"
    then
        return 0
    fi
    return 1
}

is_type() {
    if [[ "${1}" == $(ffprobe -loglevel error -select_streams "${1:0:1}" -show_entries stream=codec_type -of csv=p=0 "${2}" 2>/dev/null | sed 's \s  g') ]]
    then
        return 0
    fi
    return 1
}

is_non_static() {
    if [[ $(ffprobe -loglevel error -select_streams v -count_packets -show_entries stream=nb_read_packets -of csv=p=0 "${2}" 2>/dev/null | sed 's \s  g') -gt 1 ]]
    then
        return 0
    fi
    return 1
}

encode() {
    printf '%s -- encoding "%s" as "%s"\n' "$(now)" "${1}" "${2}"
    ffmpeg -nostdin -hide_banner -nostats -loglevel error -i "${1}" -map_metadata -1 -c:a aac -c:v libx265 -n "${2}"
}

copy() {
    printf '%s -- copying "%s" as "%s"\n' "$(now)" "${1}" "${2}"
    cp "${1}" "${2}"
}

skip() {
    printf '%s -- skipped "%s"\n' "$(now)" "${1}"
}

now() {
    date "+%Y-%m-%d %H:%M:%S"
}

main "${@}"
