@echo off
set ffmpeg_output_video_extension=mpg
set ffmpeg_output_video_encoder=mpeg2video
set ffmpeg_output_audio_extension=mp3
set ffmpeg_output_audio_encoder=libmp3lame
set ffmpeg_output_options=-q:v 3
set ffmpeg_output_suffix=encoded
bash %~dp0\ffmpeg.sh %* | cat
pause
