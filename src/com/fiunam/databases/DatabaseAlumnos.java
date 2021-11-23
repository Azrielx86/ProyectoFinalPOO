package com.fiunam.databases;

import com.fiunam.users.Alumno;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import flexjson.*;

public class DatabaseAlumnos extends Database {
    private ArrayList<Alumno> alumnos;
    private final String pathAlumnosDB = Paths.get(super.PathFiles, "alumnos.json").toString();

    public DatabaseAlumnos(){
        this.alumnos = new ArrayList<>();
        this.initDB();
    }

    public ArrayList<Alumno> getAlumnos() {
        return alumnos;
    }

    @Override
    protected void initDB(){
        JSONDeserializer<ArrayList<Alumno>> jsonDeserializer = new JSONDeserializer<>();

        try (FileReader file = new FileReader(this.pathAlumnosDB)){
            this.alumnos = jsonDeserializer.deserialize(file);
        } catch (IOException io){
            System.out.println("La base de datos \"ALUMNOS\" no existe, creando una nueva.");
            this.createDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void createDB(){
        try {
            File file = new File(this.pathAlumnosDB);
            final var newFile = file.createNewFile();

            if (!newFile){
                throw new Exception("El archivo no se pudo crear");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveDB(){
        try (FileWriter file = new FileWriter(this.pathAlumnosDB)){
            JSONSerializer serializer = new JSONSerializer();

            file.write(serializer.prettyPrint(true).serialize(this.alumnos));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String printDB(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.alumnos.size(); i++) {
            sb.append("[").append(i).append("] ");
            sb.append(this.alumnos.get(i)).append("\n");
        }

        return sb.toString();
    }
}
