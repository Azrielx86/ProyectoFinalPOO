package com.fiunam.users;

public class Administrador extends Usuario {
    private String nombre;
    private String key;

    public Administrador(){

    }

    public Administrador(String username, String nombre, String password, String key) {
        super(username, password);
        this.nombre = nombre;
        this.key = key;
    }

    @Override
    public String toString() {
        return "[ADMIN]\n" + super.toString() + "\nKey: " + "*".repeat(this.key.length());
    }
}
