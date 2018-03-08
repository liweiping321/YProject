@echo off
set "source=./proto"
set "target=./lua"
set "lua=./lua/proto"
del /q /s .\lua\*
for /f "delims=" %%a in ('dir /b /a-d "%source%\*.proto"') do (
  echo %source%/%%a
  protoc.exe %source%/%%a --plugin=protoc-gen-lua=".\\ProtocGenLua\\plugin\\protoc-gen-lua.bat" --lua_out=%target%
)

setlocal enabledelayedexpansion
for /f "tokens=*" %%b in ('dir/b /a-d "%lua%\*.lua"') do (
(for /f "tokens=*" %%i in (%lua%\%%b) do (
set s=%%i
set s=!s:proto/=!
set s=!s: _= !
echo !s!))>temp.txt
move /y temp.txt "%lua%\%%b" ) 

echo "batch over!"
pause