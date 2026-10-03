@echo off
set ffmpeg_command_args=%~n0
set ffmpeg_output_opts=-preset medium -global_quality 23
set ffmpeg_output_suffix=encoded
bash %~dp0\ffmpeg.sh %* | cat
pause
