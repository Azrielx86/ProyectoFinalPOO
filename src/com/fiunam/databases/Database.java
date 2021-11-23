package com.fiunam.databases;

import java.nio.file.Paths;

/**
 * Métodos base para las bases de datos de Alumnos y Materias
 */
public abstract class Database {
//    protected final String PathFiles = "./json/";
    protected final String PathFiles = Paths.get(".", "json").toString();

    /**
     * Inicializa la base de datos, en cada subclase se especifica
     * el archivo del cual se obtendrán los datos.
     */
    protected abstract void initDB();

    /**
     * En caso de no existir un archivo, se crea uno con el nombre
     * especificado en cada clase
     */
    protected abstract void createDB();

    /**
     * Guarda los cambios de la base de datos, siempre debe
     * ejecutarse al finalizar el programa
     */
    public abstract void saveDB();

    /**
     * Retorna una impresión más detallada de cada elemento
     * con un formato más legible
     * @return String con los datos
     */
    public abstract String printDB();

    /**
     * Al imprimir los datos, se retornan con el formato de
     * printDB();
     * @return String con los datos
     */
    public String toString() {
        return this.printDB();
    }

}
