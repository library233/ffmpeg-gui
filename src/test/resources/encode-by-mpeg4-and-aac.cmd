@echo off
set ffmpeg_output_video_extension=mp4
set ffmpeg_output_video_encoder=mpeg4
set ffmpeg_output_audio_extension=m4a
set ffmpeg_output_audio_encoder=aac
set ffmpeg_output_options=-q:v 3
bash %~dp0\encode.sh %* | cat
pause
