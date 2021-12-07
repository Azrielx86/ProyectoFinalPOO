package com.fiunam.materias;

import com.fiunam.logger.Logger;
import com.fiunam.databases.DatabaseAlumnos;
import com.fiunam.databases.DatabaseMaterias;
import com.fiunam.users.Alumno;

import java.util.Arrays;

/**
 * Administra la creación de las materias, así como las altas y bajas de estas
 * de la lista de materias de los alumnos, y administrar el grupo de
 * alumnos de estas
 */
public class AdminMateria {
    private static String[] areas = {"Ciencias Básicas", "Ciencias de la ingeniería", "Ingeniería aplicada", "Ciencias Sociales", "Otras asignaturas"};
    private static final Logger log = new Logger(AdminMateria.class);

    public static String[] getAreas() {
        return areas;
    }

    public static void setAreas(String[] areas) {
        AdminMateria.areas = areas;
    }

    /**
     * Da de alta una inscripción, agrega el número de cuenta del alumno a la lista de alumnos de la materia, y a su ves
     * se agrega el ID de la materia a la lista de materias del alumno.
     *
     * @param dbMaterias Base de datos de las materias
     * @param dbAlumnos  Base de dato de los alumnos
     * @param idmateria  ID de la materia
     * @param numCuenta  Número de Cuenta del alumno
     */
    public static void altaMateria(DatabaseMaterias dbMaterias, DatabaseAlumnos dbAlumnos, String idmateria, String numCuenta) {
        try {
            Alumno alumno = dbAlumnos.readAlumno(numCuenta);
            Materia materia = dbMaterias.readMateria(idmateria);

            if (alumno.getNumCuenta() != null && materia.getIdMateria() != null) {
                if (!alumno.getMaterias().contains(idmateria) && !materia.getAlumnos().contains(numCuenta)) {
                    materia.getAlumnos().add(alumno.getNumCuenta());
                    alumno.getMaterias().add(materia.getIdMateria());

                    log.sendInfo("Materia " + materia.getNombre() + " (" +
                            materia.getIdMateria() + ") dada de alta por el alumno " + alumno.getNombre() + " (" + alumno.getNumCuenta() + ").");
                } else {
                    AdminMateria.log.sendInfo("La materia ya está inscrita");
                }
            } else {
                AdminMateria.log.sendInfo("El Alumno o la materia no existen");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Da de baja una inscripción, elimina el número de cuenta del alumno de lista de alumnos de la materia, y a su ves
     * se elimina el ID de la materia de la lista de materias del alumno.
     *
     * @param dbMaterias Lista de materias
     * @param dbAlumnos  Lista de alumnos
     * @param idmateria  ID de la materia
     * @param numCuenta  Número de cuenta del alumno
     */
    public static void bajaMateria(DatabaseMaterias dbMaterias, DatabaseAlumnos dbAlumnos, String idmateria, String numCuenta) {
        try {
            Alumno alumno = dbAlumnos.readAlumno(numCuenta);
            Materia materia = dbMaterias.readMateria(idmateria);

            if (alumno.getNumCuenta() != null && materia.getIdMateria() != null) {
                if (alumno.getMaterias().contains(idmateria) && materia.getAlumnos().contains(numCuenta)) {
                    alumno.getMaterias().remove(materia.getIdMateria());
                    materia.getAlumnos().remove(alumno.getNumCuenta());

                    log.sendInfo("Materia " + materia.getNombre() + " (" +
                            materia.getIdMateria() + ") dada de baja por el alumno " + alumno.getNombre() + " (" + alumno.getNumCuenta() + ").");

                } else {
                    AdminMateria.log.sendInfo("La materia no está inscrita");
                }
            } else {
                AdminMateria.log.sendInfo("El Alumno o la materia no existen");
            }
        } catch (Exception e) {
            log.sendError(Arrays.toString(e.getStackTrace()));
        }
    }


}
