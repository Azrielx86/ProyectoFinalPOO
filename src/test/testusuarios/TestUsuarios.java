package test.testusuarios;

import com.fiunam.materias.Materia;
import com.fiunam.users.Administrador;
import com.fiunam.users.Alumno;

import java.util.ArrayList;

public class TestUsuarios {
    public static void main(String[] args) {
        System.out.println("=".repeat(10) + " Test Alumnos " + "=".repeat(10));

        ArrayList<Materia> materias = new ArrayList<>();
//        materias.add(new Materia("EDA II", 1, "Jose"));
//        materias.add(new Materia("POO", 4, "Guadalupe"));

//        Alumno alumno = new Alumno("alan", "passGeneric", "Alan XD", 3, "453637457", materias);
//        System.out.println(alumno);

        System.out.println();

        Administrador admin = new Administrador("edgaradmin", "EdgarCH", "admin", "fi-unam");
        System.out.println(admin);

    }
}
