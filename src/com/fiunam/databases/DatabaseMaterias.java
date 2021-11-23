package com.fiunam.databases;

import com.fiunam.materias.Materia;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DatabaseMaterias extends Database {
    private ArrayList<Materia> materias;
    private final String pathMateriasDB = Paths.get(super.pathFiles, "materias.json").toString();

    public DatabaseMaterias() {
        this.materias = new ArrayList<>();
        this.initDB();
    }

    public ArrayList<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<Materia> materias) {
        this.materias = materias;
    }

    @Override
    protected void initDB() {
        JSONDeserializer<ArrayList<Materia>> jsonDeserializer = new JSONDeserializer<>();

        try (FileReader file = new FileReader(this.pathMateriasDB)) {
            this.materias = jsonDeserializer.deserialize(file);
        } catch (IOException io) {
            System.out.println("La base de datos \"MATERIAS\" no existe, creando una nueva.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void createDB() {
        try {
            File file = new File(this.pathMateriasDB);
            final var newFile = file.createNewFile();

            if (!newFile) {
                throw new Exception("El archivo no se pudo crear");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveDB() {
        try (FileWriter file = new FileWriter(this.pathMateriasDB)){
            JSONSerializer serializer = new JSONSerializer();

            file.write(serializer.prettyPrint(true).serialize(this.materias));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String printDB() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.materias.size(); i++) {
            sb.append("[").append(i).append("] ");
            sb.append(this.materias.get(i)).append("\n");
        }

        return sb.toString();
    }
}
