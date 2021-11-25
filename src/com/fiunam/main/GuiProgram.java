package com.fiunam.main;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.List;

public class GuiProgram {
    private static final int WIDTH = 150;
    private static final int HEIGHT = 40;

    public static void run() throws IOException {
        // FIXME : System.out.println("[INFO] Iniciando terminal");
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(GuiProgram.WIDTH, GuiProgram.HEIGHT)).createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        // FIXME : System.out.println("[INFO] Terminal Iniciada con éxito");

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.CYAN));

//        PANTALLA ALUMNO
        BasicWindow windowAlumno = new BasicWindow();
        windowAlumno.setTitle("Inscripción de alumnos");
        windowAlumno.setHints(List.of(Window.Hint.CENTERED));

        Panel guiAlumnoPanel = new Panel(new GridLayout(3));
        guiAlumnoPanel.setPreferredSize(new TerminalSize(GuiProgram.WIDTH-10, GuiProgram.HEIGHT-5));
        guiAlumnoPanel.addComponent(new EmptySpace(new TerminalSize(10, 0)));
        new Button("Salir", () -> {
            // FIXME : System.out.println("[INFO] Cerrando interfaz de alumnos");
            gui.removeWindow(gui.getActiveWindow());
//            System.exit(0);
        }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(guiAlumnoPanel);

        windowAlumno.setComponent(guiAlumnoPanel);

//        LOGIN PRINCIPAL
        // Se crea la ventana inicial, y se colocan los atributos básicos, como el centrar la ventana.
        BasicWindow loginWindow = new BasicWindow();
        loginWindow.setHints(List.of(Window.Hint.CENTERED));
        loginWindow.setFixedSize(new TerminalSize(50, 4));
        loginWindow.setTitle("Inicio de sesión");
        // Se crea el panel principal y se agregan sus componentes:
        // Label para textos, TextBox para capturar entradas, Button para accionar botones, y EmptySpace
        // para agregar espacios vacíos, también se les pone algunos colores a algunos elementos con SimpleTheme.
        Panel loginPanel = new Panel(new GridLayout(3));
        loginPanel.addComponent(new EmptySpace(new TerminalSize(10,0)));
        loginPanel.addComponent(new Label("Ingresa tus datos"));
        loginPanel.addComponent(new EmptySpace(new TerminalSize(10, 0)));

        new Label("Usuario: ").setPreferredSize(new TerminalSize(15,1)).addTo(loginPanel);
        TextBox userTxt = new TextBox(new TerminalSize(25, 1));
        userTxt.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(loginPanel);
        loginPanel.addComponent(new EmptySpace(new TerminalSize(10, 0)));

        new Label("Contraseña: ").addTo(loginPanel);
        TextBox pwdTxt = new TextBox(new TerminalSize(25, 1)).setMask('*');
        pwdTxt.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(loginPanel);
        loginPanel.addComponent(new EmptySpace(new TerminalSize(10, 0)));

        // Para los botones, se les agrega la acción en el constructor, dicha acción es un Runnable.
        new Button("Ingresar", () -> {
//            TODO : Login

            //FIXME : System.out.println("[INFO] Iniciando interfaz de alumnos");
            userTxt.setText("");
            pwdTxt.setText("");
            gui.addWindowAndWait(windowAlumno);
        }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(loginPanel);
        loginPanel.addComponent(new EmptySpace(new TerminalSize(0,0)));
        new Button("Salir", () -> {
            // FIXME : System.out.println("[INFO] Finalizando programa.");
            System.exit(0);
        }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(loginPanel);

        // Finalmente se agrega el panel en la ventana
        loginWindow.setComponent(loginPanel);



//        GUI PRINCIPAL
        gui.addWindowAndWait(loginWindow);
//        gui.setActiveWindow(loginWindow);

    }
}
