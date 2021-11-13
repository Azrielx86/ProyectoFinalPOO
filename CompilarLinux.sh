#!/bin/bash

echo "Compilando proyecto [LINUX]"
javac -classpath "lib/lanterna-3.1.1.jar" src/com/fiunam/main/Main.java -d out
echo "Proyecto compilado, guardado en out/"
cd out
echo "java -classpath "../lib/lanterna-3.1.1.jar:" com.fiunam.main.Main" > start.sh
chmod +x start.sh
echo "Ejecutar start.sh (Si se requiere, dar permisos con chmod +x)"

