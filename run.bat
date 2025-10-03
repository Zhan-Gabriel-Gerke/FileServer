@echo off
REM ------------------------------
REM FileServer Build & Run Script
REM ------------------------------

REM Create output folder if it doesn't exist
if not exist out mkdir out

REM Compile Server
echo Compiling server...
dir /s /b server\src\main\java\com\example\server\*.java > sources.txt
javac -encoding UTF-8 -d out -cp "lib/*" @sources.txt
if errorlevel 1 (
    echo Server compilation failed!
    pause
    exit /b
)

REM Compile Client
echo Compiling client...
dir /s /b client\src\main\java\com\example\client\*.java > client_sources.txt
javac -encoding UTF-8 -d out -cp "lib/*" @client_sources.txt
if errorlevel 1 (
    echo Client compilation failed!
    pause
    exit /b
)

REM Start Server in background
echo Starting server...
start "Server" cmd /k java -cp "out;lib/*" com.example.server.MainServer

REM Wait a bit for server to start
timeout /t 2 /nobreak > nul

REM Start Client
echo Starting client...
java -cp "out;lib/*" com.example.client.MainClient

pause
