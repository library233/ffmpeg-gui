@echo off
set ffmpeg_output_video_extension=mpeg
set ffmpeg_output_video_encoder=mpeg1video
set ffmpeg_output_audio_extension=mp2
set ffmpeg_output_audio_encoder=mp2
set ffmpeg_output_options=-q:v 3
set ffmpeg_output_suffix=encoded
bash %~dp0\ffmpeg.sh %* | cat
pause
