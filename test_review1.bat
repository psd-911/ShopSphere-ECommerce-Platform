@echo off
echo ===================================================
echo     Running Review 1 Rubric Compliance Suite
echo ===================================================
java --enable-native-access=ALL-UNNAMED -cp "bin;lib/*" com.shopsphere.Review1Test
pause
