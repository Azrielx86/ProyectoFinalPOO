package com.fiunam.users;

import java.util.Objects;

/**
 * Base pasa la creación de un usuario con nombre de
 * usuario y contraseña
 */
public abstract class Usuario {
    private String username;
    private String password;

    public Usuario() {

    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario getCurrentUser(){
        return this;
    }

    public boolean changePassword(String pswd, String pswdConf){
        if (Objects.equals(pswd, pswdConf)){
            this.password = pswd;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "User: " +  this.username + " | Password:  " + "*".repeat(this.password.length());
    }
}
