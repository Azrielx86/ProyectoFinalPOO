package com.fiunam.materias;

import com.fiunam.users.Alumno;

import java.util.ArrayList;

/**
 * Crea una materia con su nombre, grupo, profesor y la lista de alumnos
 */
public class Materia {
    private String nombre;
    private int grupo;
    private String proferor;
    //TODO: Agregar métodos para iniciar un ID
    private String idMateria;
    private ArrayList<String> alumnos;

    public Materia() {
        this.alumnos = new ArrayList<>();
    }

    public Materia(String nombre, int grupo, String proferor) {
        this.alumnos = new ArrayList<>();
        this.nombre = nombre;
        this.grupo = grupo;
        this.proferor = proferor;
    }

    public Materia(String nombre, int grupo, String proferor, ArrayList<String> alumnos) {
        this.nombre = nombre;
        this.grupo = grupo;
        this.proferor = proferor;
        this.alumnos = alumnos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public String getProferor() {
        return proferor;
    }

    public void setProferor(String proferor) {
        this.proferor = proferor;
    }

    public ArrayList<String> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ArrayList<String> alumnos) {
        this.alumnos = alumnos;
    }

    @Override
    public String toString() {
        StringBuilder listaAlumos = new StringBuilder();
        for (int i = 0; i < this.alumnos.size(); i++) {
            listaAlumos.append("[").append(i+1).append("] ");
//            listaAlumos.append(this.alumnos.get(i).getUsername()).append("\n");
        // TODO : Obtener alumnos desde numero de cuenta.
        }

        return "Materia: " + this.nombre + " | " + this.grupo +"\n" +
                "Profesor: " + this.proferor + "\n" +
                "Alumnos: \n" + listaAlumos;
    }
}
