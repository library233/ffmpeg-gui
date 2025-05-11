@echo off
set ffmpeg_command_args=%~n0
set ffmpeg_output_opts=-q:v 3 -q:a 0
set ffmpeg_output_suffix=encoded
bash %~dp0\ffmpeg.sh %* | cat
pause
