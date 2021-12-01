package com.fiunam.databases;

import com.fiunam.Logger;
import com.fiunam.materias.Materia;
import flexjson.JSONDeserializer;
import flexjson.JSONException;
import flexjson.JSONSerializer;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class DatabaseMaterias extends Database {
    private final Logger log = new Logger(DatabaseMaterias.class);
    private ArrayList<Materia> materias;
    private final String pathMateriasDB = Paths.get(super.pathFiles, "materias.json").toString();
    private int idMaterias;

    public DatabaseMaterias() {
        this.materias = new ArrayList<>();
        this.initDB();
    }

    public ArrayList<Materia> getMaterias() {
        return materias;
    }

    @Override
    protected void initDB() {
        JSONDeserializer<ArrayList<Materia>> jsonDeserializer = new JSONDeserializer<>();

        try (FileReader file = new FileReader(this.pathMateriasDB)) {
            this.materias = jsonDeserializer.deserialize(file);
            this.idMaterias = Integer.parseInt(materias.get(materias.size()-1).getIdMateria());
        } catch(FileNotFoundException fne){
            try {
                super.createDir();
            } catch (Exception e){
                log.sendError(e.getMessage());
            }
        } catch (JSONException io) {
            log.sendWarning("La base de datos \"MATERIAS\" no existe, creando una nueva.");
        } catch (Exception e) {
            log.sendError(e.getMessage());
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
            log.sendError(e.getMessage());
        }
    }

    @Override
    public void saveDB() {
        try (FileWriter file = new FileWriter(this.pathMateriasDB)) {
            JSONSerializer serializer = new JSONSerializer();

            file.write(serializer.prettyPrint(true).include("alumnos").serialize(this.materias));

        } catch (Exception e) {
            log.sendError(e.getMessage());
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

    /**
     * Agrega un alumno a la base de datos
     * @param materia Objeto de una materia
     */
    public void agregarMateria(Materia materia) {
        materia.setIdMateria(String.valueOf(++this.idMaterias));
        this.materias.add(materia);
    }

    public Materia readMateria(String idMateria) {
        for (Materia materia : this.materias) {
            if (Objects.equals(materia.getIdMateria(), idMateria)) {
                return materia;
            }
        }
        return new Materia();
    }

    public void eliminarMateria(String idMateria) {
        for (int i = 0; i < this.materias.size(); i++) {
            if (Objects.equals(this.materias.get(i).getIdMateria(), idMateria)) {
                this.materias.remove(i);
                break;
            }
        }
        System.out.println("La materia con id \"" +
                idMateria + "\" no existe.");
    }


}
