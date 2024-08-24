@echo off
set ffmpeg_args=%~n0
set ffmpeg_output_options=-ac 1 -ar 8k
set ffmpeg_output_suffix=encoded
bash %~dp0\ffmpeg.sh %* | cat
pause
