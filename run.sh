#!/bin/bash

echo "Compilando e iniciando proyecto [LINUX]"
shopt -s globstar
mkdir -p ./out/json
cp -r ./json ./out
cp -r json/ out/json/
javac -cp ".:lib/*" src/**/*.java -d out/
echo "Proyecto compilado"
# shellcheck disable=SC2164
cd out
echo "Iniciando programa"
# shellcheck disable=SC2140
java -cp ".:../lib/*" com.fiunam.main.Main