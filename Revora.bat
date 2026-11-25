@echo off
setlocal

:: Anchor to the folder containing Revora.bat
cd /d "%~dp0"

where javac >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] javac not found. Install a JDK and add its bin folder to PATH.
    pause
    exit /b 1
)

echo Cleaning bin folder...
if exist "bin" (
    rmdir /s /q "bin"
)
mkdir "bin"

echo Compiling Java source files...
dir /s /b "src\*.java" > "sources.txt"
javac -encoding UTF-8 -d "bin" -sourcepath "src" @"sources.txt"
set COMPILE_STATUS=%errorlevel%
del "sources.txt"

if %COMPILE_STATUS% neq 0 (
    echo.
    echo [ERROR] Compilation failed! Fix the errors above and try again.
    pause
    exit /b %COMPILE_STATUS%
)

echo Running revora.Main...
echo ----------------------------------------
java -cp "bin" revora.Main

echo.
echo ----------------------------------------
pause