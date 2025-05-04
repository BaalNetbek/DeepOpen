@echo off
setlocal enabledelayedexpansion

set SCRIPT_PATH=%~f0
for %%I in ("%SCRIPT_PATH%") do set SCRIPT_DIR=%%~dpI
pushd "%SCRIPT_DIR%\.." >nul
for %%I in (.) do set CODE_DIR=%%~fI
popd >nul

if not defined CODE_DIR (
    echo %%CODE_DIR%% is empty
    exit /b 2
)
echo detected project folder %CODE_DIR%

:: Set your JDK path!
if not defined JDK_HOME (
    echo JDK_HOME not provided, using default one
    set "JDK_HOME=D:\program files (x86)\Java\jdk1.8.0_172"
)

:: Midlet specific paths
set "MIDLET_NAME=GalaxyOnFire2_jsr"
set "MIDLET_PROJECT=%CODE_DIR%\GoF2_jsr"

set "MIDLET_TMPCLASSES=%CODE_DIR%\build\target\tmpclasses"
set "MIDLET_CLASSES=%CODE_DIR%\build\target\classes"
set "TARGET_JAR=%CODE_DIR%\build\target\%MIDLET_NAME%.jar"


set "LIBS="
for /r "%CODE_DIR%\build\libs" %%J in (*.jar) do (
  if defined LIBS (
    set "LIBS=!LIBS!;%%~fJ"
  ) else (
    set "LIBS=%%~fJ"
  )
)

echo creating folders

rd /s /q "%MIDLET_TMPCLASSES%" 2>nul
rd /s /q "%MIDLET_CLASSES%"    2>nul
del     "%TARGET_JAR%"          2>nul

mkdir "%MIDLET_TMPCLASSES%" || exit /b 1
mkdir "%MIDLET_CLASSES%"   || exit /b 1

echo compiling

set "RESPONSE=%MIDLET_TMPCLASSES%\sources.txt"
del "%RESPONSE%" 2>nul
for /r "%MIDLET_PROJECT%\src" %%F in (*.java) do (
  echo %%~fF>>"%RESPONSE%"
)

"%JDK_HOME%\bin\javac" ^
  -source 1.3 -target 1.1 -g:none ^
  -bootclasspath "%LIBS%" ^
  -d "%MIDLET_TMPCLASSES%" ^
  -classpath "%MIDLET_CLASSES%" ^
  @%RESPONSE% ^
  -Xlint:-options -encoding UTF-8 || exit /b 1
echo preverify

set PREVERIFY=%CODE_DIR%\build\tools\preverify.exe
if not exist "%PREVERIFY%" (
    set PREVERIFY=%CODE_DIR%\build\tools\preverify
)

"%PREVERIFY%" -classpath "%LIBS%;%MIDLET_TMPCLASSES%" -d "%MIDLET_CLASSES%" "%MIDLET_TMPCLASSES%" || exit /b 1

echo packing jar

"%JDK_HOME%\bin\jar" cmf "%MIDLET_PROJECT%\MANIFEST.MF" "%TARGET_JAR%" ^
  -C "%MIDLET_CLASSES%" . -C "%MIDLET_PROJECT%\res" . || exit /b 1

endlocal
