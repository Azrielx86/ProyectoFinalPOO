package com.fiunam.main;

import com.fiunam.logger.Logger;

import java.util.Arrays;

/**
 * Comienza la ejecución del programa, en caso de que
 * lanterna (de GuiProgram) no pueda iniciar, envía el error
 * al archivo .log
 */
public class Main {
    public static void main(String[] args) {
        Logger log = new Logger(Main.class);
        try {
            GuiProgram.start();
        } catch (Exception e) {
            log.sendError(Arrays.toString(e.getStackTrace()) + " | " + e.getMessage());
        } finally {
            log.sendInfo("Programa finalizado.");
        }
    }
}
