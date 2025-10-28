@echo off
set ffmpeg_command_args=%~n0
set ffmpeg_output_opts=-q:v 6 -q:a 4
set ffmpeg_output_suffix=encoded
bash %~dp0\ffmpeg.sh %* | cat
pause
