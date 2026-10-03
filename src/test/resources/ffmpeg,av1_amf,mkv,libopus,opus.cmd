@echo off
set ffmpeg_command_args=%~n0
set ffmpeg_output_opts=-quality balanced -rc cqp -qp_i 35 -qp_p 35
set ffmpeg_output_suffix=encoded
bash %~dp0\ffmpeg.sh %* | cat
pause
