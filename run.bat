@echo off

echo Compiling Java source files into bin directory...
if not exist "bin" mkdir bin

javac -d bin -sourcepath src src/com/reva/sms/model/*.java src/com/reva/sms/service/*.java src/com/reva/sms/main/*.java

if %ERRORLEVEL% neq 0 (
    echo [ERROR] Compilation failed!
    pause
    exit /b %ERRORLEVEL%
)

echo [SUCCESS] Compilation succeeded! Launching Student Management System...
echo ============================================================================
java -cp bin com.reva.sms.main.Main
pause
