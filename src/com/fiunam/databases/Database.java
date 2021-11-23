package com.fiunam.databases;

import com.fiunam.users.Alumno;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import flexjson.*;

public class Database {
    private static ArrayList<Alumno> alumnos;
    private static final String pathAlumnosDB = "./json/alumnos.json";

    public Database(){
        Database.alumnos = new ArrayList<>();
        this.initDBAlumnos();
    }

    public ArrayList<Alumno> getAlumnos() {
        return alumnos;
    }

    private void initDBAlumnos(){
        JSONDeserializer<ArrayList<Alumno>> jsonDeserializer = new JSONDeserializer<>();

        try (FileReader file = new FileReader(Database.pathAlumnosDB)){
            Database.alumnos = jsonDeserializer.deserialize(file);
        } catch (IOException io){
            System.out.println("La base no existe, creando una nueva");
            Database.createDBAlumnos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createDBAlumnos(){
        try {
            File file = new File(Database.pathAlumnosDB);
            final var newFile = file.createNewFile();

            if (!newFile){
                throw new Exception("El archivo no se pudo crear");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDBAlumnos(){
        try (FileWriter file = new FileWriter(Database.pathAlumnosDB)){
            JSONSerializer serializer = new JSONSerializer();

            file.write(serializer.prettyPrint(true).serialize(Database.alumnos));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
