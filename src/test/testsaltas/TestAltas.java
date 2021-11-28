package test.testsaltas;

import com.fiunam.databases.DatabaseAlumnos;
import com.fiunam.databases.DatabaseMaterias;
import com.fiunam.materias.AdminMateria;
import com.fiunam.materias.Materia;
import com.fiunam.users.Alumno;

public class TestAltas {
    public static void main(String[] args) {
        DatabaseMaterias dbMaterias = new DatabaseMaterias();
        DatabaseAlumnos dbAlumnos = new DatabaseAlumnos();

        Alumno alumno = dbAlumnos.readAlumno("318196960");
        Alumno alumno2 = dbAlumnos.readAlumno("3647645");
        Materia materia = dbMaterias.readMateria("0002");

        AdminMateria.altaMateria(dbMaterias, dbAlumnos, materia.getIdMateria(), alumno.getNumCuenta());
        AdminMateria.altaMateria(dbMaterias, dbAlumnos, materia.getIdMateria(), alumno2.getNumCuenta());

        materia = dbMaterias.readMateria("0005");
        AdminMateria.altaMateria(dbMaterias, dbAlumnos, materia.getIdMateria(), alumno.getNumCuenta());

        materia = dbMaterias.readMateria("0003");
        AdminMateria.altaMateria(dbMaterias, dbAlumnos, materia.getIdMateria(), alumno.getNumCuenta());
        AdminMateria.altaMateria(dbMaterias, dbAlumnos, materia.getIdMateria(), alumno2.getNumCuenta());


        System.out.println("=================");
        System.out.println(dbAlumnos.readAlumno("aaa").getNumCuenta());


//        AdminMateria.


        dbMaterias.saveDB();
        dbAlumnos.saveDB();

        System.out.println(dbMaterias);
        System.out.println(dbAlumnos);

    }
}
