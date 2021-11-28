package com.fiunam.databases;

import com.fiunam.Logger;
import com.fiunam.users.Alumno;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

import flexjson.*;

/**
 * Crea y maneja la información de los alumnos
 */
public class DatabaseAlumnos extends Database {
    private ArrayList<Alumno> alumnos;
    private final String pathAlumnosDB = Path.of(super.pathFiles, "alumnos.json").toString();
    private final Logger log = new Logger(DatabaseAlumnos.class);

    public DatabaseAlumnos() {
        this.alumnos = new ArrayList<>();
        this.initDB();
    }

    @Override
    protected void initDB() {
        JSONDeserializer<ArrayList<Alumno>> jsonDeserializer = new JSONDeserializer<>();

        try (FileReader file = new FileReader(this.pathAlumnosDB)) {
            this.alumnos = jsonDeserializer.deserialize(file);
        }catch (FileNotFoundException fnt){
            try {
                super.createDir();
            } catch (Exception e){
                log.sendError(e.getMessage());
            }
        } catch (JSONException io) {
            log.sendWarning("La base de datos \"ALUMNOS\" no existe, creando una nueva.");
            this.createDB();
        } catch (Exception e) {
            log.sendError(e.getMessage());
        }
    }

    @Override
    protected void createDB() {
        try {
            File file = new File(this.pathAlumnosDB);
            final var newFile = file.createNewFile();

            if (newFile) {
                log.sendWarning("El archivo ya existe");
            }
        } catch (Exception e) {
            log.sendError(e.getMessage());
        }
    }

    @Override
    public void saveDB() {
        try (FileWriter file = new FileWriter(this.pathAlumnosDB)) {
            JSONSerializer serializer = new JSONSerializer();

            file.write(serializer.prettyPrint(true).include("materias").serialize(this.alumnos));

        } catch (Exception e) {
            log.sendError(e.getMessage());
        }
    }

    @Override
    public String printDB() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.alumnos.size(); i++) {
            sb.append("[").append(i).append("] ");
            sb.append(this.alumnos.get(i)).append("\n");
        }

        return sb.toString();
    }

    /**
     * @param alumno Objeto con la información del alumno
     */
    public void agregarAlumno(Alumno alumno) {
        this.alumnos.add(alumno);
        log.sendInfo("Alumno registrado: " + alumno.toString());
    }

//    TODO : Comprobar .gets, debido al null puede causar errores.

    /**
     * Obtiene el objeto del Alumno por su número de cuenta
     *
     * @param numCuenta Número de cuenta
     * @return Alumno si existe; null en caso contrario
     */
    public Alumno readAlumno(String numCuenta) {
        for (Alumno alumno : this.alumnos) {
            if (Objects.equals(alumno.getNumCuenta(), numCuenta)) {
                return alumno;
            }
        }
        return new Alumno();
    }

    public Alumno readAlumno(String nombre, String password) {
        for (Alumno alumno : this.alumnos) {
            if (Objects.equals(alumno.getUsername(), nombre) || Objects.equals(alumno.getNombre(), nombre)) {
                if (Objects.equals(alumno.getPassword(), password)){
                    return alumno;
                }
            }
        }
        return new Alumno();
    }

    /**
     * @param numCuenta Número de cuenta del alumno que se va a eliminar
     */
    public void eliminarAlumno(String numCuenta) {
        for (int i = 0; i < this.alumnos.size(); i++) {
            if (Objects.equals(this.alumnos.get(i).getNumCuenta(), numCuenta)) {
                this.alumnos.remove(i);
                break;
            }
        }
        System.out.println("El alumno con número de cuenta \"" +
                numCuenta + "\" no existe.");
    }


}
