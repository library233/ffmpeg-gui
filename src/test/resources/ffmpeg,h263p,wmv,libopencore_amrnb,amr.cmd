@echo off
set ffmpeg_command_args=%~n0
set ffmpeg_output_opts=-ac 1 -ar 8k
set ffmpeg_output_suffix=encoded
bash %~dp0\ffmpeg.sh %* | cat
pause
