package test.databasetests;

import com.fiunam.databases.DatabaseMaterias;
import com.fiunam.materias.Materia;

import java.util.Objects;
import java.util.Scanner;

public class TestMateriasDatabase {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        DatabaseMaterias materiasDB = new DatabaseMaterias();

        System.out.println("Agregado de materias");
        while (true){
            System.out.print("¿Agregar materia? (s/n) >");
            String op = console.nextLine();
            if (Objects.equals(op, "n") || Objects.equals(op, "N")){
                break;
            } else {
                Materia materia = new Materia();
                System.out.print("Nombre de la materia: ");
                materia.setNombre(console.nextLine());
                System.out.print("Grupo: ");
                materia.setGrupo(Integer.parseInt(console.nextLine()));

                materiasDB.agregarMateria(materia);
            }
        }
            materiasDB.saveDB();

            System.out.println(materiasDB);

    }
}
