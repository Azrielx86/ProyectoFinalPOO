package test.databasetests;

import com.fiunam.databases.DatabaseAlumnos;
import com.fiunam.users.Alumno;

public class TestDatabase {
    public static void main(String[] args) {
        DatabaseAlumnos databaseAlumnos = new DatabaseAlumnos();

        System.out.println(databaseAlumnos);

//        Alumno alumno1 = new Alumno();
//        alumno1.setNombre("Alumno 1");
//        Alumno alumno2 = new Alumno();
//        alumno2.setNombre("Alumno 2");
//        Alumno alumno3 = new Alumno();
//        alumno3.setNombre("Alumno 3");
//
//        databaseAlumnos.getAlumnos().add(alumno1);
//        databaseAlumnos.getAlumnos().add(alumno2);
//        databaseAlumnos.getAlumnos().add(alumno3);

        databaseAlumnos.saveDB();

        Alumno alumno = databaseAlumnos.getAlumnos().get(1);
        System.out.println("=".repeat(30));
        System.out.println(alumno);

    }
}
