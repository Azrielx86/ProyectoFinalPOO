package com.fiunam.materias;

import com.fiunam.users.Alumno;

/**
 * Administra la creación de las materias, así como las altas y bajas de estas
 * de la lista de materias de los alumnos, y administrar el grupo de
 * alumnos de estas
 */
public class AdminMateria {
    private static int conteoMateria = 0;    /**
     * Da de alta una inscripción
     * @param materia Materia de la que se dará de alta
     * @param alumno Alumno que se inscribe
     */
    //FIXME: Corregir y agrerarse ente número de cuenta e ID de la materia
    public static void altaMateria(Materia materia, Alumno alumno){
//        materia.getAlumnos().add(alumno);
//        alumno.getMaterias().add(materia);
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
