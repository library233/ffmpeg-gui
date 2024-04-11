@echo off
set ffmpeg_output_video_extension=mkv
set ffmpeg_output_video_encoder=copy
set ffmpeg_output_audio_extension=mka
set ffmpeg_output_audio_encoder=copy
set ffmpeg_output_suffix=remuxed
bash %~dp0\encode.sh %* | cat
pause
