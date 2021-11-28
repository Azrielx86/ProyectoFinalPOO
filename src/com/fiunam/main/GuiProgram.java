package com.fiunam.main;

import com.fiunam.Logger;
import com.fiunam.databases.DatabaseAlumnos;
import com.fiunam.databases.DatabaseMaterias;
import com.fiunam.users.Alumno;
import com.fiunam.users.Usuario;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Programa principal de inscripción con interfaz por consola,
 * requiere el paquete com.googlecode.lanterna
 */
public class GuiProgram {
    private static final int WIDTH = 150;
    private static final int HEIGHT = 40;
    private final static DatabaseAlumnos dbAlumnos = new DatabaseAlumnos();
    private final static DatabaseMaterias dbMaterias = new DatabaseMaterias();
    private final static SimpleTheme temaGlobal = SimpleTheme.makeTheme(true, TextColor.ANSI.BLACK,
            TextColor.ANSI.WHITE, TextColor.ANSI.BLACK, TextColor.ANSI.WHITE,
            TextColor.ANSI.WHITE, TextColor.ANSI.BLACK_BRIGHT, TextColor.ANSI.WHITE);
    private static Usuario currentUser;

    public static void run() throws IOException {
        Logger log = new Logger(GuiProgram.class);
        log.sendInfo("Iniciando terminal.");
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(GuiProgram.WIDTH, GuiProgram.HEIGHT)).createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        log.sendInfo("Terminal iniciada con éxito.");

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.CYAN));

//        ===================================PANTALLA DE REGISTRO==================================
        BasicWindow windowAlumno = new BasicWindow();
        windowAlumno.setTitle("Inscripción de alumnos");
        windowAlumno.setHints(List.of(Window.Hint.CENTERED));

        Panel guiAlumnoPanel = new Panel(new GridLayout(3));
//        guiAlumnoPanel.setPreferredSize(new TerminalSize(GuiProgram.WIDTH - 20, GuiProgram.HEIGHT - 10));

        Panel menuAlumnoPanel = new Panel(new GridLayout(1));
        guiAlumnoPanel.addComponent(menuAlumnoPanel.withBorder(Borders.singleLine("Menú principal")));

        Panel menuAcciones = new Panel(new LinearLayout());
//        menuAcciones.setPreferredSize(new TerminalSize(GuiProgram.WIDTH - 45, GuiProgram.HEIGHT-8));
        new Label("Selecciona una opción").addTo(menuAcciones);
        guiAlumnoPanel.addComponent(menuAcciones.withBorder(Borders.singleLine("Área de acciones (?)")));

        new ActionListBox(new TerminalSize(30, 5))
                .addItem("Inscripción de materias", () -> {
                    menuAcciones.removeAllComponents();
                    menuAcciones.addComponent(new Label("Inscripción de materias"));


                }).addItem("Baja de materias", () -> {
                    menuAcciones.removeAllComponents();
                    menuAcciones.addComponent(new Label("Baja de materias"));

                }).addItem("Actualizar datos", () ->{
                    menuAcciones.removeAllComponents();
                    Alumno alumnoActual = (Alumno) GuiProgram.currentUser.getCurrentUser();

                    Panel infoAlumnos = new Panel(new GridLayout(2));
                    menuAcciones.addComponent(infoAlumnos.withBorder(Borders.singleLine("Información del alumno")));

                    Panel subMenuAcciones = new Panel(new GridLayout(2));
                    menuAcciones.addComponent(subMenuAcciones.withBorder(Borders.singleLine("Actualización de datos")));

                    new Label("Nombre:").addTo(infoAlumnos);
                    new Label(alumnoActual.getNombre()).addTo(infoAlumnos);
                    new Label("Número de cuenta:").addTo(infoAlumnos);
                    new Label(alumnoActual.getNumCuenta()).addTo(infoAlumnos);
                    new Label("Semestre actual:").addTo(infoAlumnos);
                    new Label(String.valueOf(alumnoActual.getSemestre())).addTo(infoAlumnos);

                    new Label("Password: ").addTo(subMenuAcciones);
                    final TextBox pwdUpdtA = new TextBox().setMask('*');
                    pwdUpdtA.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(subMenuAcciones);


                }).addItem("Salir", () -> {
                    log.sendInfo("Cerrando la interfaz de alumno.");
                    menuAcciones.removeAllComponents();
                    new Label("Selecciona una opción").addTo(menuAcciones);
                    GuiProgram.currentUser = null;
                    gui.removeWindow(gui.getActiveWindow());
                }).setTheme(GuiProgram.temaGlobal).addTo(menuAlumnoPanel);


        windowAlumno.setComponent(guiAlumnoPanel);

//        ===================================REGISTRO DE ALUMNOS==================================
        BasicWindow registerWindow = new BasicWindow();
        registerWindow.setHints(List.of(Window.Hint.CENTERED));
        registerWindow.setFixedSize(new TerminalSize(60, 7));
        registerWindow.setTitle("Registro de Alumnos");

        Panel registerPanel = new Panel(new GridLayout(3));
        registerPanel.addComponent(new EmptySpace(new TerminalSize(10, 0)));
        new Label("Ingresa los datos").addTo(registerPanel);
        registerPanel.addComponent(new EmptySpace(new TerminalSize(10, 0)));

        new Label("Nombre: ").addTo(registerPanel);
        final TextBox userNameRegister = new TextBox(new TerminalSize(25, 1));
        userNameRegister.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(registerPanel);
        registerPanel.addComponent(new EmptySpace(new TerminalSize(10, 0)));

        new Label("Núm. Cuenta: ").addTo(registerPanel);
        final TextBox numCuentaReg = new TextBox(new TerminalSize(25, 1));
        numCuentaReg.setValidationPattern(Pattern.compile("[0-9]+"));
        numCuentaReg.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(registerPanel);
        registerPanel.addComponent(new EmptySpace(new TerminalSize(10, 0)));

        new Label("Usuario: ").addTo(registerPanel);
        final TextBox userRegister = new TextBox(new TerminalSize(25, 1));
        userRegister.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(registerPanel);
        registerPanel.addComponent(new EmptySpace(new TerminalSize(10, 0)));

        new Label("Contraseña: ").addTo(registerPanel);
        final TextBox passRegister = new TextBox(new TerminalSize(25, 1));
        passRegister.setMask('*');
        passRegister.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(registerPanel);
        registerPanel.addComponent(new EmptySpace(new TerminalSize(10, 0)));

        new Label("Semestre: ").addTo(registerPanel);
        final TextBox semesterRegister = new TextBox(new TerminalSize(25, 1));
        semesterRegister.setValidationPattern(Pattern.compile("[0-9]"));
        semesterRegister.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(registerPanel);
        registerPanel.addComponent(new EmptySpace(new TerminalSize(10, 0)));

        new Button("Registrarse", () -> {
            try {
                if (Objects.equals(userNameRegister.getText(), "")) throw new Exception();
                if (Objects.equals(userRegister.getText(), "")) throw new Exception();
                if (Objects.equals(passRegister.getText(), "")) throw new Exception();
                if (Objects.equals(semesterRegister.getText(), "")) throw new Exception();
                if (Objects.equals(numCuentaReg.getText(), "")) throw new Exception();

                Alumno alumno = new Alumno(userNameRegister.getText(), userRegister.getText(),
                        passRegister.getText(), Integer.parseInt(semesterRegister.getText()), numCuentaReg.getText());
                GuiProgram.dbAlumnos.agregarAlumno(alumno);
                GuiProgram.dbAlumnos.saveDB();

                userNameRegister.setText("");
                userRegister.setText("");
                passRegister.setText("");
                semesterRegister.setText("");
                numCuentaReg.setText("");

                new MessageDialogBuilder().setTitle("Aviso").setText("Registro completo")
                        .addButton(MessageDialogButton.OK).build().showDialog(gui);

                gui.removeWindow(gui.getActiveWindow());

            } catch (Exception e) {
                new MessageDialogBuilder().setTitle("Advertencia").setText("Debes rellenar todos los campos: ")
                        .addButton(MessageDialogButton.Retry).build().showDialog(gui);
            }


        }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(registerPanel);
        registerPanel.addComponent(new EmptySpace(new TerminalSize(10, 0)));
        new Button("Cancelar", () -> {
            userNameRegister.setText("");
            userRegister.setText("");
            passRegister.setText("");
            semesterRegister.setText("");
            numCuentaReg.setText("");
            gui.removeWindow(gui.getActiveWindow());
        }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(registerPanel);

        registerWindow.setComponent(registerPanel);

//        =======================================LOGIN PRINCIPAL======================================
        // Se crea la ventana inicial, y se colocan los atributos básicos, como el centrar la ventana.
        BasicWindow loginWindow = new BasicWindow();
        loginWindow.setHints(List.of(Window.Hint.CENTERED));
        loginWindow.setFixedSize(new TerminalSize(50, 4));
        loginWindow.setTitle("Inicio de sesión");
        // Se crea el panel principal y se agregan sus componentes:
        // Label para textos, TextBox para capturar entradas, Button para accionar botones, y EmptySpace
        // para agregar espacios vacíos, también se les pone algunos colores a algunos elementos con SimpleTheme.
        Panel loginPanel = new Panel(new GridLayout(3));
        loginPanel.addComponent(new EmptySpace(new TerminalSize(10, 0)));
        loginPanel.addComponent(new Label("Ingresa tus datos"));
        loginPanel.addComponent(new EmptySpace(new TerminalSize(10, 0)));

        new Label("Usuario: ").setPreferredSize(new TerminalSize(15, 1)).addTo(loginPanel);
        final TextBox userTxt = new TextBox(new TerminalSize(25, 1));
        userTxt.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(loginPanel);
        loginPanel.addComponent(new EmptySpace(new TerminalSize(10, 0)));

        new Label("Contraseña: ").addTo(loginPanel);
        final TextBox pwdTxt = new TextBox(new TerminalSize(25, 1)).setMask('*');
        pwdTxt.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(loginPanel);
        loginPanel.addComponent(new EmptySpace(new TerminalSize(10, 0)));

        // Para los botones, se les agrega la acción en el constructor, dicha acción es un Runnable.
        new Button("Ingresar", () -> {
            try {
                //            TODO : Login
                if (Objects.equals(userTxt.getText(), "")) throw new Exception("Debes rellenar todos los campos");
                if (Objects.equals(pwdTxt.getText(), "")) throw new Exception("Debes rellenar todos los campos");

                GuiProgram.currentUser = dbAlumnos.readAlumno(userTxt.getText(), pwdTxt.getText());
                if (Objects.equals(GuiProgram.currentUser.getUsername(), null))throw new Exception("Usuario inválido");

                log.sendInfo("Iniciando interfaz de alumnos.");
                userTxt.setText("");
                pwdTxt.setText("");
                gui.addWindowAndWait(windowAlumno);

            } catch (Exception e) {
                new MessageDialogBuilder().setTitle("Advertencia").setText(e.getMessage())
                        .addButton(MessageDialogButton.Retry).build().showDialog(gui);
            }

        }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(loginPanel);
        new Button("Registrarse", () -> gui.addWindowAndWait(registerWindow))
                .setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(loginPanel);
        new Button("Salir", () -> {
            log.sendInfo("Finalizando programa.");
            System.exit(0);
        }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(loginPanel);

        // Finalmente se agrega el panel en la ventana
        loginWindow.setComponent(loginPanel);


//        GUI PRINCIPAL
        gui.addWindowAndWait(loginWindow);
//        gui.setActiveWindow(loginWindow);

    }
}
