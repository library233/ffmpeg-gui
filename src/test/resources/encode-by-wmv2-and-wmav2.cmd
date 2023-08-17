@echo off
set ffmpeg_output_video_extension=wmv
set ffmpeg_output_video_encoder=wmv2
set ffmpeg_output_audio_extension=wma
set ffmpeg_output_audio_encoder=wmav2
set ffmpeg_output_options=-q:v 3
bash %~dp0\encode.sh %* | cat
pause
