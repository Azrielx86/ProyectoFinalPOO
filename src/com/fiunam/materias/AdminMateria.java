package com.fiunam.materias;

import com.fiunam.users.Alumno;

public class AdminMateria {
    public static void agregarMaterias(Materia materia, Alumno alumno){
        materia.getAlumnos().add(alumno);
        alumno.getMaterias().add(materia);
    }

}
