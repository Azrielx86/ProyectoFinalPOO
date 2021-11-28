package com.fiunam.users;

/**
 * Crea un usuario para los administradores, incluye la key para la administración
 */
public class Administrador extends Usuario {
    private String nombre;
    private String numTrabajador;
    private String key;

    public Administrador(){

    }

    public Administrador(String username, String nombre, String password, String key) {
        super(username, password);
        this.nombre = nombre;
        this.key = key;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumTrabajador() {
        return numTrabajador;
    }

    public void setNumTrabajador(String numTrabajador) {
        this.numTrabajador = numTrabajador;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "[ADMIN]\n" + super.toString() + "\nKey: " + "*".repeat(this.key.length()) +
                "\nNúmero de trabajador" + this.numTrabajador;
    }
}
