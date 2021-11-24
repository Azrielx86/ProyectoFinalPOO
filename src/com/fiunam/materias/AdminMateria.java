package com.fiunam.materias;

import com.fiunam.databases.DatabaseAlumnos;
import com.fiunam.databases.DatabaseMaterias;
import com.fiunam.users.Alumno;

/**
 * Administra la creación de las materias, así como las altas y bajas de estas
 * de la lista de materias de los alumnos, y administrar el grupo de
 * alumnos de estas
 */
public class AdminMateria {
    private static int conteoMateria = 0;

    /**
     * Da de alta una inscripción
     * @param dbMaterias Base de datos de las materias
     * @param dbAlumnos Base de dato de los alumnos
     * @param idmateria ID de la materia
     * @param numCuenta Número de Cuenta del alumno
     */
    public static void altaMateria(DatabaseMaterias dbMaterias, DatabaseAlumnos dbAlumnos, String idmateria, String numCuenta){
        try {
//            TODO: Comprobación previa a que se agregue la materia.
            dbMaterias.readMateria(idmateria).getAlumnos().add(numCuenta);
            dbAlumnos.readAlumno(numCuenta).getMaterias().add(idmateria);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Da de baja una inscripción
     * @param materia Materia de la que se dará de alta
     * @param alumno Alumno que se da de baja
     */
    //FIXME: Corregir
    public static void bajaMateria(Materia materia, Alumno alumno){
//        materia.getAlumnos().remove(alumno);
//        alumno.getMaterias().remove(materia);
    }

//    public static Materia crearMateria(String nombre, int grupo, String proferor){
//        return new Materia(nombre, grupo, proferor);
//    }


}
