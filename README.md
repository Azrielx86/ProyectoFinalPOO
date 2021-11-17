# Proyecto Final Programación Orientada a Objetos
Moreno Chalico Edgar Ulises

Proyecto final para la materia de Programación Orientada a Objetos

Semestre 2022-1

## Sistema de Inscripciones a cursos
Programa para administrar las inscripciones de los alumnos,
creación y manejo de materias, y almacenarlos en archivos JSON 
(En sustitución de alguna base de datos, ya que no es requisito 
para este proyecto).

## Dependencias
Para manejar las dependencias, se recomienda instalarlas con Maven o 
usar las incluidas en lib.
* lanterna (com.googlecode.lanterna)

## Compilación
Para la compilación, se recomienda usar los scripts
para Linux y Windows incluidos, o los siguientes comandos desde la raíz del proyecto.

Comandos para Linux

    javac -cp ".:lib/lanterna-3.1.1.jar:" src/**/*.java -d out
    cd out
    java -cp "../lib/lanterna-3.1.1.jar:" com.fiunam.main.Main

Comandos para Windows

    javac -cp ".;lib/lanterna-3.1.1.jar" src/**/*.java -d out
    cd out
    javaw -cp "../lib/lanterna-3.1.1.jar;" com.fiunam.main.Main

## Ejecución desde scripts
Para únicamente compilar, usar el script de compilación.

    ./CompilarLinuxAll.sh
    ./CompilarWindowsAll.bat

Dentro de la carpeta out se crean los scripts para ejecutar después el programa.

    ./start.sh
    ./start.bat

Para compilar y ejecutar. (por eliminar(?))

    ./run.sh
    ./run.bat

Para crear el .jar directamente.

    (pendiente)

Para Linux probablemente sea necesario dar permisos de ejecución con el siguiente comando.

    chmod +x <script>