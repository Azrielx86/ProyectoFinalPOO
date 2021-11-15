#!/bin/bash

echo "Compilando y ejecutando proyecto [LINUX]"
javac -classpath "lib/lanterna-3.1.1.jar" src/com/fiunam/main/Main.java -d out
echo -e "Proyecto compilado\nEjectando..."
cd out
java -classpath "../lib/lanterna-3.1.1.jar:" com.fiunam.main.Main
