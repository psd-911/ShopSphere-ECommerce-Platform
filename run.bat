@echo off
echo ===================================================
echo     Launching ShopSphere E-Commerce Application
echo ===================================================
java --enable-native-access=ALL-UNNAMED -cp "bin;lib/*" com.shopsphere.App
pause
