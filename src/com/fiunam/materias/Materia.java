package com.fiunam.materias;

import java.util.ArrayList;

/**
 * Crea una materia con su nombre, grupo, profesor y la lista de alumnos
 */
public class Materia {
    private String nombre;
    private int grupo;
    private String profesor;
    private String idMateria;
    private ArrayList<String> alumnos;
    private static final int MAX_ALUMNOS = 20;
    private String area;

    /**
     * Constructor vacío, inicializa el ArrayList de alumnos,
     * es necesario aunque no haya elementos en la lista.
     */
    public Materia() {
        this.alumnos = new ArrayList<>();
    }

    /**
     * Constructor que recibe los datos de la materia
     * @param nombre Nombre de la materia
     * @param grupo Grupo
     * @param profesor Profesor que imparte la materia
     * @param area Area a la que pertenece
     */
    public Materia(String nombre, int grupo, String profesor, String area) {
        this.alumnos = new ArrayList<>();
        this.nombre = nombre;
        this.grupo = grupo;
        this.profesor = profesor;
        this.area = area;
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

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public ArrayList<String> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ArrayList<String> alumnos) {
        this.alumnos = alumnos;
    }

    public String getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(String idMateria) {
        this.idMateria = String.format("%04d", Integer.parseInt(idMateria));
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int cupoDisponible() {
        return Materia.MAX_ALUMNOS - this.alumnos.size();
    }

    @Override
    public String toString() {
        StringBuilder listaAlumos = new StringBuilder();
        for (int i = 0; i < this.alumnos.size(); i++) {
            listaAlumos.append("(").append(i+1).append(") - ");
            listaAlumos.append(this.alumnos.get(i)).append("\n");
        }

        return "Materia: " + this.nombre + " | " + this.grupo + " | ID: " +
                this.idMateria + "\n" +
                "Profesor: " + this.profesor + "\n" +
                "Lista de Alumnos: \n" + listaAlumos;
    }
}
