package com.fiunam.logger;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;

/**
 * Clase que maneja los mensajes de la aplicación que no se pueden imprimir debido a
 * que por la interfaz no se pueden mostrar.
 */
public class Logger {
    private static final String logPath = Path.of(".", "loggerProyecto.log").toString();
    private final String className;

    public Logger(Class<?> className){
        this.className = className.toString();
        Logger.createNewFile();
    }

    /**
     * Comprueba si existe un archivo .log, si no existe, crea uno.
     */
    public static void createNewFile(){
        try {
            File file = new File(logPath);
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Envía un mensaje informativo.
     * @param message detalles
     */
    public void sendInfo(String message){
        try (FileWriter file = new FileWriter(logPath, true)){
            file.write("[  INFO   ] [" + this.className + "] : " + message + "\n");
        } catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Envía un mensaje de error.
     * @param message detalles
     */
    public void sendError(String message){
        try (FileWriter file = new FileWriter(logPath, true)){
            file.write("[  ERROR  ] [" + this.className + "] : " + message + "\n");
        } catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Envia una advertencia.
     * @param message detalles
     */
    public void sendWarning(String message){
        try (FileWriter file = new FileWriter(logPath, true)){
            file.write("[ WARNING ] [" + this.className + "] : " + message + "\n");
        } catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }

}
