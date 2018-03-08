@echo off
set "source=./proto"
set "target=./cs"
del /q /s .\cs\*
for /f "delims=" %%a in ('dir /b /a-d "%source%\*.proto"') do (
  echo %source%/%%a
  protogen -i:%source%/%%a -o:%target%/%%a -p:detectMissing
)

ren .\cs\*.proto *.cs
echo "batch over!"
pause