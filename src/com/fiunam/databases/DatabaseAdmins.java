package com.fiunam.databases;

import com.fiunam.logger.Logger;
import com.fiunam.users.Administrador;
import flexjson.JSONDeserializer;
import flexjson.JSONException;
import flexjson.JSONSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class DatabaseAdmins extends Database{
    private ArrayList<Administrador> admins;
    private final String pathAdminsDB = Path.of(super.pathFiles, "administradores.json").toString();
    private final Logger log = new Logger(DatabaseAdmins.class);
    private final Administrador adminPorDefecto = new Administrador("admin", "Admin", "admin", "0000001");

    public DatabaseAdmins() {
        this.admins = new ArrayList<>();
        admins.add(this.adminPorDefecto);
        this.initDB();
        this.saveDB();
    }

    public ArrayList<Administrador> getAdmins() {
        return admins;
    }

    @Override
    protected void initDB() {
        JSONDeserializer<ArrayList<Administrador>> jsonDeserializer = new JSONDeserializer<>();

        try (FileReader file = new FileReader(this.pathAdminsDB)) {
            this.admins = jsonDeserializer.deserialize(file);
        } catch (FileNotFoundException fe) {
            log.sendWarning("La base de datos \"ADMINISTRADORES\" no existe, esperando datos para crear una nueva.");
            this.createDB();
        } catch (Exception e) {
            log.sendError(Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    protected void createDB() {
        try {
            Database.createDir();
            File file = new File(this.pathAdminsDB);
            if (!file.createNewFile()) throw new Exception("Error al crear el archivo " + this.pathAdminsDB);
        } catch (Exception e) {
            log.sendError(Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void saveDB() {
        try (FileWriter file = new FileWriter(this.pathAdminsDB)) {
            JSONSerializer serializer = new JSONSerializer();

            file.write(serializer.prettyPrint(true).include("materias").serialize(this.admins));

        } catch (Exception e) {
            log.sendError(Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public String printDB() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.admins.size(); i++) {
            sb.append("[").append(i).append("] ");
            sb.append(this.admins.get(i)).append("\n");
        }

        return sb.toString();
    }

    public void agregarAdmin(Administrador administrador) {
        administrador.setNumTrabajador(this.generarNumTrabajador());
        this.admins.add(administrador);
        log.sendInfo("Administrador registrado: " + administrador);
    }

    /**
     * Obtiene el objeto del Administrador por su número de trabajador
     *
     * @param numTrabajador número del trabajador
     * @return Admin si existe; Admin null en caso contrario
     */
    public Administrador readAdmins(String numTrabajador) {
        for (Administrador admin : this.admins) {
            if (Objects.equals(admin.getNumTrabajador(), numTrabajador)) {
                return admin;
            }
        }
        return new Administrador();
    }

    public Administrador readAdmins(String nombre, String password) {
        for (Administrador admin : this.admins) {
            if (Objects.equals(admin.getUsername(), nombre) || Objects.equals(admin.getNombre(), nombre)) {
                if (Objects.equals(admin.getPassword(), password)) {
                    return admin;
                }
            }
        }
        return new Administrador();
    }

    /**
     * @param numTrabajador Número de trabajador del administrador que se va a eliminar
     */
    public void eliminarAdministrador(String numTrabajador) {
        for (int i = 0; i < this.admins.size(); i++) {
            if (Objects.equals(this.admins.get(i).getNumTrabajador(), numTrabajador)) {
                this.admins.remove(i);
                break;
            }
        }
        log.sendWarning("El administrador con número de trabajador \"" +
                numTrabajador + "\" no existe.");
    }

    /**
     * @return Número de trabajador de 8 dígitos generado para el administrador
     */
    private String generarNumTrabajador() {
        Random rand = new Random();
        String numGenerado;
        while (true) {
            numGenerado = String.valueOf(rand.nextInt(99999999));
            for (Administrador administrador : this.admins) {
                if (Objects.equals(numGenerado, administrador.getNumTrabajador())) {
                    numGenerado = String.valueOf(rand.nextInt(99999999));
                } else {
                    return numGenerado;
                }
            }
        }
    }

    /**
     * Para la primera ejecución del programa se deben de establecer los valores
     * del admin por defecto. (TODO)
     */
    private void establecerAdminPorDefeto(String password){
        this.adminPorDefecto.setPassword(password);
    }

}
