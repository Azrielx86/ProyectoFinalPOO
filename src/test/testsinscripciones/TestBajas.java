package test.testsinscripciones;

import com.fiunam.databases.DatabaseAlumnos;
import com.fiunam.databases.DatabaseMaterias;
import com.fiunam.materias.AdminMateria;
import com.fiunam.materias.Materia;
import com.fiunam.users.Alumno;

public class TestBajas {
    private final static DatabaseAlumnos dbAlumnos = new DatabaseAlumnos();
    private final static DatabaseMaterias dbMaterias = new DatabaseMaterias();

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){
        Alumno alumno = dbAlumnos.readAlumno("89770684");
        Materia materia = dbMaterias.readMateria("0011");

        System.out.println(alumno + "\n");
        System.out.println(materia + "\n");

        AdminMateria.bajaMateria(dbMaterias, dbAlumnos, materia.getIdMateria(), alumno.getNumCuenta());
        dbMaterias.saveDB();
        dbAlumnos.saveDB();

        System.out.println(alumno + "\n");
        System.out.println(materia + "\n");
    }
}
