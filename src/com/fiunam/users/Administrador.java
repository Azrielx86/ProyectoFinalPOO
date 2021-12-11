package com.fiunam.users;

/**
 * Crea un usuario para los administradores con su nombre y un número de trabajador
 */
public class Administrador extends Usuario {
    private String nombre;
    private String numTrabajador;

    /**
     * Constructor vacío de un administrador
     */
    public Administrador(){

    }

    /**
     * Constructor que inicializa los datos del administrador y establece su número de cuenta
     * @param username Nombre de usuario
     * @param nombre Nombre completo
     * @param password Contraseña
     * @param numTrabajador Número de trabajador
     */
    public Administrador(String username, String nombre, String password, String numTrabajador) {
        super(username, password);
        this.nombre = nombre;
        this.numTrabajador = numTrabajador;
    }

    /**
     * Constructor que inicializa los datos del administrador
     * @param username Nombre de usuario
     * @param nombre Nombre completo
     * @param password Contraseña
     */
    public Administrador(String username, String password, String nombre) {
        super(username, password);
        this.nombre = nombre;
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
