@echo off
set ffmpeg_output_video_extension=mp4
set ffmpeg_output_video_encoder=copy
set ffmpeg_output_audio_extension=m4a
set ffmpeg_output_audio_encoder=copy
set ffmpeg_output_suffix=remuxed
bash %~dp0\ffmpeg.sh %* | cat
pause
