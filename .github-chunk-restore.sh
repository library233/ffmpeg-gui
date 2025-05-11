#!/bin/bash
set -euo pipefail
base_dir="$(cd "$(dirname "$0")"; pwd)"
journal_file="$base_dir/.github-chunk-journal.ini"
[[ -f "$journal_file" ]] || { echo "Missing journal file: $journal_file" >&2; exit 1; }
cd "$base_dir"
path=
sha256=
chunk_list=()
while IFS= read -r line || [[ -n "$line" ]]; do
    case "$line" in
        "[file]")
            if [[ -n "$path" ]]; then
                mkdir -p "$(dirname "$path")"
                echo "Restoring from ${#chunk_list[@]} chunks: $path" >&2
                cat "${chunk_list[@]}" >"$path"
                checksum=$(sha256sum "$path" | awk '{print $1}')
                if [[ "$checksum" != "$sha256" ]]; then
                    echo "Checksum mismatch: $path" >&2
                    exit 1
                fi
                rm -f "${chunk_list[@]}"
                chunk_list=()
            fi
            ;;
        path=*)
            path="${line#path=}"
            ;;
        sha256=*)
            sha256="${line#sha256=}"
            ;;
        chunk=*)
            chunk="${line#chunk=}"
            chunk_list+=("$chunk")
            ;;
        "")
            if [[ -n "$path" ]]; then
                mkdir -p "$(dirname "$path")"
                echo "Restoring from ${#chunk_list[@]} chunks: $path" >&2
                cat "${chunk_list[@]}" >"$path"
                checksum=$(sha256sum "$path" | awk '{print $1}')
                if [[ "$checksum" != "$sha256" ]]; then
                    echo "Checksum mismatch: $path" >&2
                    exit 1
                fi
                rm -f "${chunk_list[@]}"
                path=""
                sha256=""
                chunk_list=()
            fi
            ;;
    esac
done <"$journal_file"
if [[ -n "$path" ]]; then
    mkdir -p "$(dirname "$path")"
    echo "Restoring from ${#chunk_list[@]} chunks: $path" >&2
    cat "${chunk_list[@]}" >"$path"
    checksum=$(sha256sum "$path" | awk '{print $1}')
    if [[ "$checksum" != "$sha256" ]]; then
        echo "Checksum mismatch: $path" >&2
        exit 1
    fi
    rm -f "${chunk_list[@]}"
fi
rm -f "$journal_file"
