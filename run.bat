@ECHO OFF
ECHO Compilando proyecto [WINDOWS]
mkdir out\\json
Xcopy /E /I json out\\json
javac -classpath ".;lib/lanterna-3.1.1.jar;lib/flexjson-3.3.jar;" src/com/fiunam/main/*.java src/com/fiunam/materias/*.java src/com/fiunam/users/*.java src/com/fiunam/databases/*.java -d out
cd out
ECHO Iniciando programa
javaw -classpath "../lib/lanterna-3.1.1.jar;lib/flexjson-3.3.jar;" com.fiunam.main.Main
