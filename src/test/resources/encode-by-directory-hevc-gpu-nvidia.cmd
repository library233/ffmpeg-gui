@echo off
cd /d %1 && md output && cd || pause && exit /b
echo.
for %%f in (*.mp4) do (
    echo --------------------------------
    echo %%f
    ffmpeg                              ^
    -loglevel warning                   ^
    -stats                              ^
    -i "%%f"                            ^
    -hide_banner                        ^
    -map_metadata -1                    ^
    -c:v hevc_nvenc                     ^
    -c:a copy                           ^
    -n "output\%%f"
)
echo.
pause
