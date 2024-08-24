@echo off
set ffmpeg_args=%~n0
set ffmpeg_output_options=-q:v 3
set ffmpeg_output_suffix=encoded
bash %~dp0\ffmpeg.sh %* | cat
pause
