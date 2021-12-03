package test.testjson;

import com.fiunam.materias.AdminMateria;
import com.fiunam.materias.Materia;
import com.fiunam.users.Alumno;
import flexjson.*;

import java.io.FileReader;
import java.io.FileWriter;

public class TestMateriasJson {
    public static void main(String[] args) {
        JSONSerializer serializer = new JSONSerializer();
        Alumno alumno = new Alumno("edgarchalico", "Edgar Chalico", "testXD",3);
        Materia poo = new Materia("Programación Orientada a Objetos", 1,"Guadalupe", "");
        Materia edaii = new Materia("Estructuras de Datos y Algoritmos II", 4, "José", "");

//        AdminMateria.altaMateria(poo, alumno);
//        AdminMateria.altaMateria(edaii, alumno);

        System.out.println("============== Test Agregar materias ================");
        System.out.println(alumno);
        System.out.println("POO ");
        System.out.println(poo);
        System.out.println("EDA II ");
        System.out.println(edaii);

        System.out.println(alumno.getMaterias());

        try (FileWriter file = new FileWriter("test.json")){
            file.write(serializer.prettyPrint(true).include("materias").serialize(alumno));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
