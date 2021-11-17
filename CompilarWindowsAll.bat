@ECHO OFF
ECHO Compilando proyecto [WINDOWS]
shopt -s globstar
javac -classpath "lib/lanterna-3.1.1.jar" src/**/*.java -d out
cd out
ECHO javaw -classpath "../lib/lanterna-3.1.1.jar;" com.fiunam.main.Main > start.bat
ECHO Proyecto compilado en out/
PAUSE
