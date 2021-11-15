package test.testmaterias;

import com.fiunam.materias.AdminMateria;
import com.fiunam.materias.Materia;
import com.fiunam.users.Alumno;

public class TestMaterias {
    public static void main(String[] args) {
        Alumno alumno = new Alumno("edgarchalico", "Edgar Chalico", "testXD",3, "318192932");
        Materia poo = new Materia("Programación Orientada a Objetos", 1,"Guadalupe");
        Materia edaii = new Materia("Estructuras de Datos y Algoritmos II", 4, "José");

        AdminMateria.altaMateria(poo, alumno);
        AdminMateria.altaMateria(edaii, alumno);

        System.out.println("============== Test Agregar materias ================");
        System.out.println(alumno);
        System.out.println("POO ");
        System.out.println(poo);
        System.out.println("EDA II ");
        System.out.println(edaii);


        System.out.println("============== Test Remover materias ================");

        AdminMateria.bajaMateria(poo, alumno);
        AdminMateria.bajaMateria(edaii, alumno);

        System.out.println(alumno);
        System.out.println("POO ");
        System.out.println(poo);
        System.out.println("EDA II ");
        System.out.println(edaii);



    }
}
