package test.testsinscripciones;

import com.fiunam.databases.DatabaseAlumnos;
import com.fiunam.databases.DatabaseMaterias;
import com.fiunam.materias.AdminMateria;
import com.fiunam.materias.Materia;
import com.fiunam.users.Alumno;

public class TestAltas {
    private final static DatabaseAlumnos dbAlumnos = new DatabaseAlumnos();
    private final static DatabaseMaterias dbMaterias = new DatabaseMaterias();


    public static void main(String[] args) {
        test2();
    }

    private static void test2(){
        Alumno alumno = dbAlumnos.readAlumno("89770684");
        Materia materia = dbMaterias.readMateria("0011");

        System.out.println(alumno + "\n");
        System.out.println(materia + "\n");

        AdminMateria.altaMateria(dbMaterias, dbAlumnos, materia.getIdMateria(), alumno.getNumCuenta());
        dbMaterias.saveDB();
        dbAlumnos.saveDB();

        System.out.println(alumno + "\n");
        System.out.println(materia + "\n");

    }

    private static void test1(){
        DatabaseMaterias dbMaterias = new DatabaseMaterias();
        DatabaseAlumnos dbAlumnos = new DatabaseAlumnos();

        Alumno alumno = dbAlumnos.readAlumno("318196960");
        Alumno alumno2 = dbAlumnos.readAlumno("3647645");
        Materia materia = dbMaterias.readMateria("0002");

        AdminMateria.altaMateria(dbMaterias, dbAlumnos, materia.getIdMateria(), alumno.getNumCuenta());
        AdminMateria.altaMateria(dbMaterias, dbAlumnos, materia.getIdMateria(), alumno2.getNumCuenta());

        materia = dbMaterias.readMateria("0005");
        AdminMateria.altaMateria(dbMaterias, dbAlumnos, materia.getIdMateria(), alumno.getNumCuenta());

        materia = dbMaterias.readMateria("0003");
        AdminMateria.altaMateria(dbMaterias, dbAlumnos, materia.getIdMateria(), alumno.getNumCuenta());
        AdminMateria.altaMateria(dbMaterias, dbAlumnos, materia.getIdMateria(), alumno2.getNumCuenta());


        System.out.println("=================");
        System.out.println(dbAlumnos.readAlumno("aaa").getNumCuenta());


//        AdminMateria.


        dbMaterias.saveDB();
        dbAlumnos.saveDB();

        System.out.println(dbMaterias);
        System.out.println(dbAlumnos);
    }
}
