@echo off
:loop
cls
curl http://localhost:8080/game/print
timeout /t 1 /nobreak >nul
goto loop