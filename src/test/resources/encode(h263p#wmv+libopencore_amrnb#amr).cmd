@echo off
set ffmpeg_output_video_extension=wmv
set ffmpeg_output_video_encoder=h263p
set ffmpeg_output_audio_extension=amr
set ffmpeg_output_audio_encoder=libopencore_amrnb
set ffmpeg_output_options=-ac 1 -ar 8k
set ffmpeg_output_suffix=encoded
bash %~dp0\ffmpeg.sh %* | cat
pause
