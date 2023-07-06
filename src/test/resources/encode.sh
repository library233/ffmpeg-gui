#!/bin/bash

main() {
    which ffmpeg ffprobe >/dev/null || exit 1
    for item in "${@}"
    do
        encode_item "${item}"
    done
    exit 0
}

encode_item() {
    if [[ ! -r "${1}" ]]
    then
        file "${1}"
        return 1
    fi
    local suffix=encoded
    if [[ -d "${1}" ]]
    then
        encode_directory "${1}" "${suffix}"
    elif [[ -f "${1}" ]]
    then
        encode_file "${1}" "${suffix}" &
        wait_for_next
    else
        return 1
    fi
}

encode_directory() {
    cd "${1}" || return 1
    local output_directory="../$(basename "${1}")-${2}"
    while read -d $'\0' file
    do
        encode_file_to_directory "${file#./}" "${output_directory}" &
        wait_for_next
    done < <(find . -type f -print0)
    wait
    cd - >/dev/null
}

encode_file_to_directory() {
    mkdir -p "${2}/$(dirname "${1}")" || continue
    local extension=$(get_output_extension "${1}")
    local output="${2}/${1%.*}.${extension}"
    if [[ ${extension} == "" ]]
    then
        copy "${1}" "${2}/${1}"
    elif [[ -e "${output}" ]]
    then
        skip "${1}"
    elif ! encode "${1}" "${output}"
    then
        remove "${output}"
        copy "${1}" "${2}/${1}"
    fi
}

wait_for_next() {
    while [[ $(jobs -p | wc -l) -ge $(nproc) ]]
    do
        sleep 1s
    done
}

encode_file() {
    cd "$(dirname "${1}")" || return 1
    local file=$(basename "${1}")
    local file=${file#./}
    local extension=$(get_output_extension "${file}")
    local output="${file%.*}-${2}.${extension}"
    if [[ ${extension} == "" ]]
    then
        skip "${file}"
    elif [[ -e "${output}" ]]
    then
        skip "${file}"
    elif ! encode "${file}" "${output}"
    then
        remove "${output}"
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

find () {
    /bin/find "${@}"
}

is_video () {
    is_type video "${1}" && is_non_static "${1}"
}

is_audio() {
    is_type audio "${1}"
}

is_type() {
    [[ "${1}" == $(ffprobe -loglevel error -select_streams "${1:0:1}" -show_entries stream=codec_type -of csv=p=0 "${2}" 2>/dev/null | sed 's \s  g') ]]
}

is_non_static() {
    [[ $(ffprobe -loglevel error -select_streams v -count_packets -show_entries stream=nb_read_packets -of csv=p=0 "${2}" 2>/dev/null | sed 's \s  g') -gt 1 ]]
}

encode() {
    printf '%s -- encoding "%s" as "%s"\n' "$(now)" "${1}" "${2}"
    ffmpeg -nostdin -hide_banner -nostats -loglevel error -i "${1}" -map_metadata -1 -c:a aac -c:v libx265 -y "${2}" </dev/null >/dev/null 2>&1
}

remove() {
    printf '%s -- removing "%s"\n' "$(now)" "${1}"
    rm -f "${1}"
}

copy() {
    printf '%s -- copying "%s" as "%s"\n' "$(now)" "${1}" "${2}"
    cp -np "${1}" "${2}"
}

skip() {
    printf '%s -- skipped "%s"\n' "$(now)" "${1}"
}

now() {
    date "+%Y-%m-%d %H:%M:%S"
}

main "${@}"
