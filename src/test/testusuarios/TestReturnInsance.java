package test.testusuarios;

import com.fiunam.users.Alumno;
import com.fiunam.users.Usuario;

public class TestReturnInsance {
    public static void main(String[] args) {
        Alumno alumno = new Alumno("edgarsfeic", "Edgars Feic", "rlsdfhe3434", 2, "3445435");

        Usuario currentUser = alumno;

        System.out.println(currentUser.getCurrentUser());

        Alumno alumnoCopy = (Alumno) currentUser.getCurrentUser();

        System.out.println(alumnoCopy.getNumCuenta());
        System.out.println(alumnoCopy.getNombre());

    }
}
