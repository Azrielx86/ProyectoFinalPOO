package com.fiunam.users;

import java.util.ArrayList;

/**
 * Crea un usuario para un alumno, con su nombre, número de cuenta,
 * semestre que cursa y su lista de materias
 */
public class Alumno extends Usuario {
    private String nombre;
    private int semestre;
    private String numCuenta;
    private ArrayList<String> materias;

    public Alumno() {
        this.materias = new ArrayList<>();
    }

    public Alumno(String username, String nombre, String password, int semestre) {
        super(username, password);
        this.nombre = nombre;
        this.materias = new ArrayList<>();
        this.semestre = semestre;
    }

    public Alumno(String username, String password, String nombre, int semestre, String numCuenta, ArrayList<String> materias) {
        super(username, password);
        this.nombre = nombre;
        this.semestre = semestre;
        this.numCuenta = numCuenta;
        this.materias = materias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public ArrayList<String> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<String> materias) {
        this.materias = materias;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    @Override
    public String toString() {
        StringBuilder listaMaterias = new StringBuilder();
        for (int i = 0; i < this.materias.size(); i++) {
            listaMaterias.append("(").append(i + 1).append(") - ");
            listaMaterias.append(this.materias.get(i)).append("\n");
        }

        return "ALUMNO: " + this.nombre + " | Password: " + "*".repeat(super.getPassword().length()) + "\n" +
                "Número de cuenta: " + this.numCuenta + "\n" +
                "Semestre: " + this.semestre + "\nLista de Materias:" + "\n" +
                listaMaterias;
    }
}
