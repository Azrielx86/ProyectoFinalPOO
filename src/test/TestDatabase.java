package test;

import com.fiunam.databases.Database;
import com.fiunam.users.Alumno;

public class TestDatabase {
    public static void main(String[] args) {
        Database database = new Database();

        System.out.println(database.getAlumnos());

//        Alumno alumno1 = new Alumno();
//        alumno1.setNombre("Alumno 1");
//        Alumno alumno2 = new Alumno();
//        alumno2.setNombre("Alumno 2");
//        Alumno alumno3 = new Alumno();
//        alumno3.setNombre("Alumno 3");
//
//        database.getAlumnos().add(alumno1);
//        database.getAlumnos().add(alumno2);
//        database.getAlumnos().add(alumno3);

        database.saveDBAlumnos();
    }
}
