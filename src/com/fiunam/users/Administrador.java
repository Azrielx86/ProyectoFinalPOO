package com.fiunam.users;

/**
 * Crea un usuario para los administradores con su nombre y un número de trabajador
 */
public class Administrador extends Usuario {
    private String nombre;
    private String numTrabajador;

    public Administrador(){

    }

    public Administrador(String username, String nombre, String password, String numTrabajador) {
        super(username, password);
        this.nombre = nombre;
        this.numTrabajador = numTrabajador;
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

    @Override
    public String toString() {
        return "[ADMIN]\n" + super.toString() + "\n" +
                "Número de trabajador: " + this.numTrabajador;
    }
}
