@echo off
set ffmpeg_output_video_extension=webm
set ffmpeg_output_video_encoder=libsvtav1
set ffmpeg_output_audio_extension=opus
set ffmpeg_output_audio_encoder=libopus
set ffmpeg_output_suffix=encoded
bash %~dp0\encode.sh %* | cat
pause
