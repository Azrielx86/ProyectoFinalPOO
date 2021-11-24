package test.databasetests;

import com.fiunam.databases.DatabaseAlumnos;
import com.fiunam.users.Alumno;

import java.util.Objects;
import java.util.Scanner;

public class TestDatabase {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        DatabaseAlumnos databaseAlumnos = new DatabaseAlumnos();

        while(true){
            System.out.println("Añadir otro? (s/n)");
            String op = console.nextLine();
            if (Objects.equals(op, "n") || Objects.equals(op, "N")){
                break;
            }

            Alumno alumno = new Alumno();
            System.out.print("Nombre: ");
            alumno.setNombre(console.nextLine());
            System.out.print("Número de cuenta: ");
            alumno.setNumCuenta(console.nextLine());

            databaseAlumnos.agregarAlumno(alumno);
        }

//        Eliminado de un alumno
        databaseAlumnos.eliminarAlumno("n");

//        Lectura de alumnos, cuando existe y cuando no
        System.out.println(databaseAlumnos.readAlumno("3181----0"));
        System.out.println(databaseAlumnos.readAlumno("Num que no existe"));

        databaseAlumnos.saveDB();

        System.out.println(databaseAlumnos);

    }
}
