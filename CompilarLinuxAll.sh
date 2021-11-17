#!/bin/bash

echo "Compilando proyecto [LINUX]"
shopt -s globstar
javac -cp ".:lib/lanterna-3.1.1.jar:" src/**/*.java -d out/
echo "Proyecto compilado, guardado en out/"
# shellcheck disable=SC2164
cd out
# shellcheck disable=SC2140
echo "java -cp ".:../lib/lanterna-3.1.1.jar::" com.fiunam.main.Main" > start.sh
chmod +x start.sh
echo "Ejecutar start.sh (Si se requiere, dar permisos con chmod +x)"

