package com.fiunam.materias;

import com.fiunam.Logger;
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
    private static String[] areas = {"Ciencias Básicas", "Ciencias de la ingeniería", "Ingeniería aplicada", "Ciencias Sociales", "Otras asignaturas"};
    private static final Logger log = new Logger(AdminMateria.class);

    public static String[] getAreas() {
        return areas;
    }

    public static void setAreas(String[] areas) {
        AdminMateria.areas = areas;
    }

    /**
     * Da de alta una inscripción
     * @param dbMaterias Base de datos de las materias
     * @param dbAlumnos Base de dato de los alumnos
     * @param idmateria ID de la materia
     * @param numCuenta Número de Cuenta del alumno
     */
    public static void altaMateria(DatabaseMaterias dbMaterias, DatabaseAlumnos dbAlumnos, String idmateria, String numCuenta){
        try {
            if (dbMaterias.readMateria(idmateria) != null && dbAlumnos.readAlumno(numCuenta) != null){
                if (!dbMaterias.readMateria(idmateria).getAlumnos().contains(numCuenta) && !dbAlumnos.readAlumno(numCuenta).getMaterias().contains(idmateria)){
                    dbMaterias.readMateria(idmateria).getAlumnos().add(numCuenta);
                    dbAlumnos.readAlumno(numCuenta).getMaterias().add(idmateria);
                    log.sendInfo("Materia " + dbMaterias.readMateria(idmateria) + " inscrita por el alumno " + dbAlumnos.readAlumno(numCuenta) + ".");
                } else{
                    AdminMateria.log.sendInfo("La materia ya está inscrita");
                }
            } else {
                AdminMateria.log.sendInfo("El Alumno o la materia no existen");
            }
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
