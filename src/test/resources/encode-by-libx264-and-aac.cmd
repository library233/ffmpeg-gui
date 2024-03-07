@echo off
set ffmpeg_output_video_extension=mp4
set ffmpeg_output_video_encoder=libx264
set ffmpeg_output_audio_extension=m4a
set ffmpeg_output_audio_encoder=aac
bash %~dp0\encode.sh %* | cat
pause
