package com.fiunam.users;

public class CurrentUserManager {
    public Alumno alumno;
    public Administrador admin;

    public CurrentUserManager(Alumno alumno) {
        this.alumno = alumno;
    }

    public CurrentUserManager(Administrador admin) {
        this.admin = admin;
    }

    public CurrentUserManager(Usuario user) {
        if (user instanceof Alumno){

        }
    }

    //    public Usuario getCurrentUser() {
//
//    }
}
