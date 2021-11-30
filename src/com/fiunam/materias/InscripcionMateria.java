package com.fiunam.materias;

import com.fiunam.users.Alumno;

import java.util.ArrayList;
import java.util.Objects;

public class InscripcionMateria {
    public ArrayList<Materia> materiasDispoinbles;
    public ArrayList<Materia> materiasListadas;
    public Alumno alumno;

    public InscripcionMateria(ArrayList<Materia> materiasDispoinbles, ArrayList<Materia> materiasListadas, Alumno alumno) {
        this.materiasDispoinbles = materiasDispoinbles;
        this.materiasListadas = materiasListadas;
        this.alumno = alumno;
    }

    public InscripcionMateria(ArrayList<Materia> materiasDispoinbles, Alumno alumno) {
        this.materiasDispoinbles = materiasDispoinbles;
        this.alumno = alumno;
    }

    public void listarMateria(String idMateria){
        for (int i = 0; i < this.materiasDispoinbles.size(); i++) {
            if (Objects.equals(this.materiasDispoinbles.get(i).getIdMateria(), idMateria)){
                this.materiasListadas.add(this.materiasDispoinbles.get(i));
            }
        }
    }

}
