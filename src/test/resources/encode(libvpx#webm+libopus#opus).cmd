@echo off
set ffmpeg_output_video_extension=webm
set ffmpeg_output_video_encoder=libvpx
set ffmpeg_output_audio_extension=opus
set ffmpeg_output_audio_encoder=libopus
set ffmpeg_output_suffix=encoded
bash %~dp0\ffmpeg.sh %* | cat
pause
