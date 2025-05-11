#!/bin/bash
set -euo pipefail
base_dir="$(cd "$(dirname "$0")"; pwd)"
journal_file="$base_dir/.github-chunk-journal.ini"
chunk_size=$((100 * 1024 * 1024))
find "$base_dir" -path "$base_dir/.*" -prune -o -type f -size +"${chunk_size}c" -print | sort | while read -r filepath; do
    relpath="${filepath#$base_dir/}"
    filesize=$(stat -c%s "$filepath")
    sha256=$(sha256sum "$filepath" | awk '{print $1}')
    chunks=$(((filesize + chunk_size - 1) / chunk_size))
    echo "Splitting $filesize bytes to $chunks chunks: $relpath"
    echo "[file]" >>"$journal_file"
    echo "path=$relpath" >>"$journal_file"
    echo "sha256=$sha256" >>"$journal_file"
    echo "chunks=$chunks" >>"$journal_file"
    for ((i=1; i<=chunks; ++i)); do
        chunkfile="$filepath.chunk.$(printf "%03d" "$i")"
        dd if="$filepath" of="$chunkfile" bs=$chunk_size skip=$((i - 1)) count=1 status=none
        chunkrel="${chunkfile#$base_dir/}"
        echo "chunk=$chunkrel" >>"$journal_file"
    done
    echo >>"$journal_file"
    rm -f "$filepath"
done
