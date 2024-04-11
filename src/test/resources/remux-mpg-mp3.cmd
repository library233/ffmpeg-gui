@echo off
set ffmpeg_output_video_extension=mpg
set ffmpeg_output_video_encoder=copy
set ffmpeg_output_audio_extension=mp3
set ffmpeg_output_audio_encoder=copy
set ffmpeg_output_suffix=remuxed
bash %~dp0\encode.sh %* | cat
pause
