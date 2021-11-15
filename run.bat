@ECHO OFF
ECHO Compilando y ejecutando proyecto [WINODOWS]
javac -classpath "lib/lanterna-3.1.1.jar" src/com/fiunam/main/Main.java -d out
cd out
javaw -classpath "../lib/lanterna-3.1.1.jar;" com.fiunam.main.Main