package test;

import com.fiunam.Logger;

public class TestLogger {
    public static void main(String[] args) {

        Logger log = new Logger(TestLogger.class);

        System.out.println("Prueba Logger");

        log.sendInfo("Mensaje de información");
        log.sendError("Mensaje de error");
        log.sendWarning("Mensaje de warning");


    }
}
