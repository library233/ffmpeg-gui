@echo off
set ffmpeg_command_args=%~n0
set ffmpeg_output_suffix=remuxed
bash %~dp0\ffmpeg.sh %* | cat
pause
