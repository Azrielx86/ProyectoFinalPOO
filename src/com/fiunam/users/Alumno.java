package com.fiunam.users;

import com.fiunam.materias.Materia;

import java.util.ArrayList;

/**
 * Crea un usuario para un alumno, con su nombre, número de cuenta,
 * semestre que cursa y su lista de materias
 */
public class Alumno extends Usuario{
    private String nombre;
    private int semestre;
    private String numCuenta;
    private ArrayList<Materia> materias;

    public Alumno() {
        this.materias = new ArrayList<>();
    }

    public Alumno(String username, String nombre, String password, int semestre, String numCuenta) {
        super(username, password);
        this.nombre = nombre;
        this.materias = new ArrayList<>();
        this.semestre = semestre;
        this.numCuenta = numCuenta;
    }

    public Alumno(String username, String password, String nombre, int semestre, String numCuenta, ArrayList<Materia> materias) {
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

    public ArrayList<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<Materia> materias) {
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
            listaMaterias.append("[").append(i+1).append("] ");
            listaMaterias.append(this.materias.get(i).getNombre());
            listaMaterias.append(" | Grupo: ");
            listaMaterias.append(this.materias.get(i).getGrupo()).append("\n");
        }

        return "[ALUMNO]\n" + this.nombre + "\n" +
                "Número de cuenta: " + this.numCuenta + "\n" +
                "Semestre: " + this.semestre + " | Materias:" + "\n" +
                listaMaterias;
    }
}
