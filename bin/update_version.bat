@echo off
setlocal

REM 检查是否传入了版本号参数
if "%~1"=="" (
    echo Usage: %0 new_version
    exit /b 1
)

REM 获取传入的版本号参数
set NEW_VERSION=%~1

REM 调用 PowerShell 脚本并传递版本号参数
powershell -ExecutionPolicy Bypass -File update_version.ps1 -newVersion %NEW_VERSION%

endlocal
