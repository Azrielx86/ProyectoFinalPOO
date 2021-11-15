@ECHO OFF
ECHO Compilando proyecto [WINDOWS]
javac -classpath "lib/lanterna-3.1.1.jar" src/com/fiunam/main/Main.java -d out
cd out
ECHO javaw -classpath "../lib/lanterna-3.1.1.jar;" com.fiunam.main.Main > start.bat
ECHO Proyecto compilado en out/
PAUSE