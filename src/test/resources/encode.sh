#!/bin/sh

main() {
    which ffmpeg >/dev/null || exit 1
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
    local output="../$(basename "${1}")-${2}"
    find . -type f -print0 | while read -d $'\0' file
    do
        mkdir -p "${output}/$(dirname "${file}")" || continue
        encode "${file#./}" "${output#./}/${file#./}"
    done
    cd - >/dev/null
}

encode_file() {
    cd "$(dirname "${1}")" || return 1
    local file=$(basename "${1}")
    local output="${file%.*}-${2}.${file##*.}"
    encode "${file}" "${output}"
    cd - >/dev/null
}

encode() {
    echo "+ ${1} -> ${2}"
    ffmpeg -nostdin -hide_banner -nostats -loglevel error -i "${1}" -map_metadata -1 -c:a aac -c:v libx265 -n "${2}" >/dev/null 2>&1
}

main "${@}"
