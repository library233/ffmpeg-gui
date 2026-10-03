@echo off
set ffmpeg_command_args=%~n0
set ffmpeg_output_opts=-b:v 0 -preset p4 -rc vbr -cq 23
set ffmpeg_output_suffix=encoded
bash %~dp0\ffmpeg.sh %* | cat
pause
