@echo off
echo ===================================================
echo     Compiling ShopSphere E-Commerce Application
echo ===================================================
if not exist "bin" mkdir bin
javac -d bin -cp "lib/*" src/com/shopsphere/*.java
if %ERRORLEVEL% EQU 0 (
    echo.
    echo [SUCCESS] Compilation completed with 0 errors!
) else (
    echo.
    echo [FAILED] Compilation failed. Check error messages above.
)
pause
