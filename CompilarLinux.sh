#!/bin/bash

echo "Compilando proyecto [LINUX]"
shopt -s globstar
mkdir -p ./out/json
cp -r ./json ./out
javac -cp ".:lib/*" src/**/*.java -d out/
echo "Proyecto compilado, guardado en ./out"
# shellcheck disable=SC2164
cd out
# shellcheck disable=SC2140
echo "java -cp ".:../lib/*" com.fiunam.main.Main" > start.sh
chmod +x start.sh
echo "Ejecutar start.sh (Si se requiere, dar permisos con chmod +x)"