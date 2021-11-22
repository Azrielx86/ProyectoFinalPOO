package test.testjson;

import com.fiunam.materias.Materia;
import com.fiunam.users.Alumno;
import flexjson.JSONDeserializer;

import java.io.FileReader;
import java.util.ArrayList;

public class LoadAlumno {
    public static void main(String[] args) {
        Alumno alumno = new Alumno();
        ArrayList<Materia> materias = new ArrayList<>();
        try (FileReader file = new FileReader("test.json")){
            JSONDeserializer<ArrayList<Materia>> jsondesm = new JSONDeserializer<>();
            JSONDeserializer<Alumno> jsondes = new JSONDeserializer<>();

            alumno = jsondes.deserialize(file);
            materias = jsondesm.deserialize(file);

            System.out.println(alumno);
            System.out.println(materias);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
