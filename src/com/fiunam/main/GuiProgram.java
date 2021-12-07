package com.fiunam.main;

import com.fiunam.logger.Logger;
import com.fiunam.databases.DatabaseAdmins;
import com.fiunam.databases.DatabaseAlumnos;
import com.fiunam.databases.DatabaseMaterias;
import com.fiunam.materias.AdminMateria;
import com.fiunam.materias.Materia;
import com.fiunam.users.Administrador;
import com.fiunam.users.Alumno;
import com.fiunam.users.Usuario;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
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
    private final static DatabaseAdmins dbadmins = new DatabaseAdmins();
    private final static SimpleTheme temaGlobal = SimpleTheme.makeTheme(true, TextColor.ANSI.BLACK,
            TextColor.ANSI.WHITE, TextColor.ANSI.WHITE, TextColor.ANSI.BLACK_BRIGHT,
            TextColor.ANSI.WHITE, TextColor.ANSI.BLACK_BRIGHT, TextColor.ANSI.WHITE);
    private static Usuario currentUser;

    public static void run() throws IOException {
        Logger log = new Logger(GuiProgram.class);
        String mensajeMenuInicial = "Selecciona una opción.\n" +
                "Utiliza <Tab> para moverte entre menús.\n" +
                "Usa las flechas " + Symbols.ARROW_UP + " y " + Symbols.ARROW_DOWN + "\n" +
                "para moverte dentro de los menús.";

        log.sendInfo("Iniciando terminal.");
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(GuiProgram.WIDTH, GuiProgram.HEIGHT)).createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        log.sendInfo("Terminal iniciada con éxito.");

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.CYAN));

//        ===================================PANTALLA DE ALUMNOS==================================
        BasicWindow windowAlumno = new BasicWindow();
        windowAlumno.setTitle("Inscripción de alumnos");
        windowAlumno.setHints(List.of(Window.Hint.CENTERED));

        Panel guiAlumnoPanel = new Panel(new GridLayout(3));

        Panel menuAlumnoPanel = new Panel(new GridLayout(1));
        guiAlumnoPanel.addComponent(menuAlumnoPanel.withBorder(Borders.singleLine("Menú principal")));

        Panel menuAlumnoAcc = new Panel(new LinearLayout());
        new Label(mensajeMenuInicial).addTo(menuAlumnoAcc);
        guiAlumnoPanel.addComponent(menuAlumnoAcc.withBorder(Borders.singleLine("Área de acciones (?)")));

        new ActionListBox(new TerminalSize(30, 5))
                .addItem("Inscripción de materias", () -> {
                    String[] areas = AdminMateria.getAreas();
                    Alumno alumnoActual = (Alumno) GuiProgram.currentUser.getCurrentUser();

                    Table<String> tablaMaterias = new Table<>("Nombre", "Profesor", "Cupo", "Clave");
                    Table<String> tablaMateriasIns = new Table<>("Nombre", "Profesor", "Cupo", "Clave");

                    tablaMaterias.setPreferredSize(new TerminalSize(50, 8));
                    tablaMateriasIns.setPreferredSize(new TerminalSize(50, 4));

                    ArrayList<Materia> listadoMaterias = GuiProgram.dbMaterias.getCopiaMaterias();
                    // Filtrado de las materias ya inscritas
                    listadoMaterias.removeIf(materia -> alumnoActual.getMaterias().contains(materia.getIdMateria()));

                    menuAlumnoAcc.removeAllComponents();
                    menuAlumnoAcc.addComponent(new Label("Inscripción de materias"));

                    Panel panelMateriasDisp = new Panel();
                    Panel panelMateriasIns = new Panel();
//                    panelMateriasDisp.addTo(menuAlumnoAcc);
                    menuAlumnoAcc.addComponent(panelMateriasDisp.withBorder(Borders.singleLine("Materias disponibles")));
                    menuAlumnoAcc.addComponent(panelMateriasIns.withBorder(Borders.singleLine("Materias por inscribir")));


                    for (Materia materia : listadoMaterias) {
                        tablaMaterias.getTableModel().addRow(materia.getNombre(),
                                materia.getProfesor(), String.valueOf(materia.cupoDisponible())
                                , materia.getIdMateria());
                    }


                    new Button("Selecciona un área", () -> {
                        ActionListDialogBuilder listadoAreas = new ActionListDialogBuilder();
                        listadoAreas.setTitle("Areas disponibles").setDescription("Selecciona un area");
                        for (String area : areas) {
                            listadoAreas.addAction(area, () -> {

                                while (tablaMaterias.getTableModel().getRowCount() > 0) {
                                    tablaMaterias.getTableModel().removeRow(0);
                                }

                                ArrayList<Materia> materiasFiltradas = dbMaterias.getCopiaMaterias(area);

                                materiasFiltradas.removeIf(materia -> alumnoActual.getMaterias().contains(materia.getIdMateria()));

                                for (Materia materiaFiltrada : materiasFiltradas) {
                                    tablaMaterias.getTableModel().addRow(
                                            materiaFiltrada.getNombre(),
                                            materiaFiltrada.getProfesor(),
                                            String.valueOf(materiaFiltrada.cupoDisponible()),
                                            materiaFiltrada.getIdMateria()
                                    );
                                }

                            });
                        }
                        listadoAreas.addAction("Ver todas", () -> {
                            while (tablaMaterias.getTableModel().getRowCount() > 0) {
                                tablaMaterias.getTableModel().removeRow(0);
                            }
                            for (Materia materia : listadoMaterias) {
                                tablaMaterias.getTableModel().addRow(materia.getNombre(),
                                        materia.getProfesor(), String.valueOf(materia.cupoDisponible()),
                                        materia.getIdMateria());
                            }
                        });
                        listadoAreas.setCanCancel(true);
                        listadoAreas.build().showDialog(gui);

                    }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE))
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER))
                            .addTo(panelMateriasDisp);

                    new EmptySpace(new TerminalSize(0, 1)).addTo(panelMateriasDisp);

                    tablaMaterias.setSelectAction(() -> {
                        try {
                            List<String> idMateria = tablaMaterias.getTableModel().getRow(tablaMaterias.getSelectedRow());
                            tablaMateriasIns.getTableModel().addRow(tablaMaterias.getTableModel().getRow(tablaMaterias.getSelectedRow()));
                            tablaMaterias.getTableModel().removeRow(tablaMaterias.getSelectedRow());
//                            AdminMateria.altaMateria(GuiProgram.dbMaterias, GuiProgram.dbAlumnos, idMateria.get(3), alumnoActual.getNumCuenta());

                        } catch (Exception e) {
                            new MessageDialogBuilder().setTitle("Advertencia").setText("Ya no hay materias disponibles")
                                    .addButton(MessageDialogButton.OK).build().showDialog(gui);
                        }
                    });

                    tablaMateriasIns.setSelectAction(() -> {
                        try {
                            tablaMaterias.getTableModel().addRow(tablaMateriasIns.getTableModel().getRow(tablaMateriasIns.getSelectedRow()));
                            tablaMateriasIns.getTableModel().removeRow(tablaMateriasIns.getSelectedRow());
                        } catch (Exception e) {
                            new MessageDialogBuilder().setTitle("Advertencia").setText("Ya no hay materias disponibles")
                                    .addButton(MessageDialogButton.OK).build().showDialog(gui);
                        }
                    });

                    tablaMaterias.setTheme(GuiProgram.temaGlobal);
                    tablaMateriasIns.setTheme(GuiProgram.temaGlobal);
                    tablaMaterias.addTo(panelMateriasDisp);
                    tablaMateriasIns.addTo(panelMateriasIns);

                    new Panel(new GridLayout(2)).addTo(menuAlumnoAcc)
                            .addComponent(new Button("Inscribir materias", () -> {

                                if (tablaMateriasIns.getTableModel().getRowCount() == 0){
                                    new MessageDialogBuilder().setTitle("Aviso").setText("No hay materias por inscribir")
                                            .addButton(MessageDialogButton.OK).build().showDialog(gui);
                                } else {
                                    for (int i = 0; i < tablaMateriasIns.getTableModel().getRowCount(); i++) {
                                        AdminMateria.altaMateria(GuiProgram.dbMaterias, GuiProgram.dbAlumnos,
                                                tablaMateriasIns.getTableModel().getRow(i).get(3), alumnoActual.getNumCuenta());
                                    }

                                    GuiProgram.dbAlumnos.saveDB();
                                    GuiProgram.dbMaterias.saveDB();

                                    new MessageDialogBuilder().setTitle("Aviso").setText("Materias inscritas con éxito")
                                            .addButton(MessageDialogButton.OK).build().showDialog(gui);

                                    menuAlumnoAcc.removeAllComponents();
                                    new Label(mensajeMenuInicial).addTo(menuAlumnoAcc);
                                    log.sendWarning("Bases de datos restauradas");
                                }
                            }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)))
                            .addComponent(new Button("Cancelar", () -> {
                                GuiProgram.dbAlumnos.reloadDB();
                                GuiProgram.dbMaterias.reloadDB();
                                menuAlumnoAcc.removeAllComponents();
                                new Label(mensajeMenuInicial);
                            }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)));

//                    ==========Baja de materias
                }).addItem("Baja de materias", () -> {
                    Alumno alumnoActual = (Alumno) GuiProgram.currentUser.getCurrentUser();

                    menuAlumnoAcc.removeAllComponents();
                    menuAlumnoAcc.addComponent(new Label("Baja de materias"));


                    Table<String> tablaMateriasInscritas = new Table<>("Nombre", "Profesor", "Cupo", "Clave");
                    Table<String> tablaMateriasBaja = new Table<>("Nombre", "Profesor", "Cupo", "Clave");

                    tablaMateriasInscritas.setPreferredSize(new TerminalSize(50, 8));
                    tablaMateriasBaja.setPreferredSize(new TerminalSize(50, 4));

                    ArrayList<Materia> listadoMaterias = GuiProgram.dbMaterias.getCopiaMaterias();
                    // Filtrado de las materias no inscritas
                    listadoMaterias.removeIf(materia -> !(alumnoActual.getMaterias().contains(materia.getIdMateria())));

                    for (Materia materia : listadoMaterias) {
                        tablaMateriasInscritas.getTableModel().addRow(materia.getNombre(),
                                materia.getProfesor(), String.valueOf(materia.cupoDisponible())
                                , materia.getIdMateria());
                    }

                    tablaMateriasInscritas.setSelectAction(() -> {
                        try {
                            List<String> idMateria = tablaMateriasInscritas.getTableModel().getRow(tablaMateriasInscritas.getSelectedRow());
                            tablaMateriasBaja.getTableModel().addRow(tablaMateriasInscritas.getTableModel().getRow(tablaMateriasInscritas.getSelectedRow()));
                            tablaMateriasInscritas.getTableModel().removeRow(tablaMateriasInscritas.getSelectedRow());
//                            AdminMateria.bajaMateria(GuiProgram.dbMaterias, GuiProgram.dbAlumnos, idMateria.get(3), alumnoActual.getNumCuenta());

                        } catch (Exception e) {
                            new MessageDialogBuilder().setTitle("Advertencia").setText("Ya no hay materias disponibles")
                                    .addButton(MessageDialogButton.OK).build().showDialog(gui);
                        }
                    });

                    tablaMateriasBaja.setSelectAction(() -> {
                        try {
                            tablaMateriasInscritas.getTableModel().addRow(tablaMateriasBaja.getTableModel().getRow(tablaMateriasBaja.getSelectedRow()));
                            tablaMateriasBaja.getTableModel().removeRow(tablaMateriasBaja.getSelectedRow());
                        } catch (Exception e) {
                            new MessageDialogBuilder().setTitle("Advertencia").setText("Ya no hay materias disponibles")
                                    .addButton(MessageDialogButton.OK).build().showDialog(gui);
                        }
                    });


                    Panel materiasInscritas = new Panel();
                    Panel bajaMaterias = new Panel();

                    tablaMateriasInscritas.setTheme(GuiProgram.temaGlobal);
                    bajaMaterias.setTheme(GuiProgram.temaGlobal);

                    materiasInscritas.addComponent(tablaMateriasInscritas);
                    bajaMaterias.addComponent(tablaMateriasBaja);

                    menuAlumnoAcc.addComponent(materiasInscritas.withBorder(Borders.singleLine("Materias inscritas")));
                    menuAlumnoAcc.addComponent(bajaMaterias.withBorder(Borders.singleLine("Materias por dar de baja")));

                    new Panel(new GridLayout(2)).addTo(menuAlumnoAcc)
                            .addComponent(new Button("Dar de baja", () -> {
                                if (tablaMateriasBaja.getTableModel().getRowCount() == 0){
                                    new MessageDialogBuilder().setTitle("Aviso").setText("No hay materias seleccionadas")
                                            .addButton(MessageDialogButton.OK).build().showDialog(gui);
                                } else {
                                    for (int i = 0; i < tablaMateriasBaja.getTableModel().getRowCount(); i++) {
                                        AdminMateria.bajaMateria(GuiProgram.dbMaterias, GuiProgram.dbAlumnos,
                                                tablaMateriasBaja.getTableModel().getRow(i).get(3), alumnoActual.getNumCuenta());
                                    }

                                    GuiProgram.dbAlumnos.saveDB();
                                    GuiProgram.dbMaterias.saveDB();

                                    new MessageDialogBuilder().setTitle("Aviso").setText("Materias dadas de baja con éxito")
                                            .addButton(MessageDialogButton.OK).build().showDialog(gui);

                                    menuAlumnoAcc.removeAllComponents();
                                    new Label(mensajeMenuInicial).addTo(menuAlumnoAcc);
                                    log.sendWarning("Bases de datos restauradas");
                                }
                            }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)))
                            .addComponent(new Button("Cancelar", () -> {
                                GuiProgram.dbAlumnos.reloadDB();
                                GuiProgram.dbMaterias.reloadDB();
                                menuAlumnoAcc.removeAllComponents();
                                new Label(mensajeMenuInicial);
                            }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)));

                }).addItem("Información del usuario", () -> {
                    menuAlumnoAcc.removeAllComponents();
                    Alumno alumnoActual = (Alumno) GuiProgram.currentUser.getCurrentUser();

                    Panel infoAlumnos = new Panel(new GridLayout(2));
                    menuAlumnoAcc.addComponent(infoAlumnos.withBorder(Borders.singleLine("Información del alumno")));

                    Panel subMenuAccionesA = new Panel(new GridLayout(2));
                    menuAlumnoAcc.addComponent(subMenuAccionesA.withBorder(Borders.singleLine("Actualización de contraseña")));

                    new Label("Nombre:")
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER))
                            .addTo(infoAlumnos);
                    new Label(alumnoActual.getNombre())
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER))
                            .addTo(infoAlumnos);
                    new Label("Número de cuenta:").addTo(infoAlumnos);
                    new Label(alumnoActual.getNumCuenta())
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER))
                            .addTo(infoAlumnos);
                    new Label("Semestre actual:")
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER))
                            .addTo(infoAlumnos);
                    new Label(String.valueOf(alumnoActual.getSemestre())).addTo(infoAlumnos);

                    new Label("Contraseña actual: ")
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER))
                            .addTo(subMenuAccionesA);
                    final TextBox pwdUpdtA = new TextBox().setMask('*');
                    pwdUpdtA.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(subMenuAccionesA);

                    new Label("Contraseña nueva: ")
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER))
                            .addTo(subMenuAccionesA);
                    final TextBox pwdUpdtB = new TextBox().setMask('*');
                    pwdUpdtB.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(subMenuAccionesA);

                    new Label("Repite la contraseña: ")
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER))
                            .addTo(subMenuAccionesA);
                    final TextBox pwdUpdtC = new TextBox().setMask('*');
                    pwdUpdtC.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(subMenuAccionesA);

                    new EmptySpace(new TerminalSize(0, 0)).addTo(subMenuAccionesA);
                    new Button("Actualizar", () -> {
                        //                    TODO : Actualización de Passwords
                        if (Objects.equals(pwdUpdtB.getText(), "") && Objects.equals(pwdUpdtC.getText(), "")) {
                            new MessageDialogBuilder().setTitle("Aviso")
                                    .setText("Debes ingresar una nueva contraseña").addButton(MessageDialogButton.Retry)
                                    .build().showDialog(gui);
                        } else if (Objects.equals(pwdUpdtA.getText(), alumnoActual.getPassword()) && alumnoActual.changePassword(pwdUpdtB.getText(), pwdUpdtC.getText())) {
                            new MessageDialogBuilder().setTitle("Aviso")
                                    .setText("Contraseña actualizada con éxito").addButton(MessageDialogButton.OK)
                                    .build().showDialog(gui);
                        } else {
                            new MessageDialogBuilder().setTitle("Aviso")
                                    .setText("Las contraseñas no coinciden").addButton(MessageDialogButton.Retry)
                                    .build().showDialog(gui);
                        }
                        pwdUpdtA.setText("");
                        pwdUpdtB.setText("");
                        pwdUpdtC.setText("");
                    }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(subMenuAccionesA);


                }).addItem("Salir", () -> {
                    log.sendInfo("Cerrando la interfaz de alumno.");
                    menuAlumnoAcc.removeAllComponents();
                    new Label(mensajeMenuInicial).addTo(menuAlumnoAcc);
                    GuiProgram.currentUser = null;
                    gui.removeWindow(gui.getActiveWindow());
                }).setTheme(GuiProgram.temaGlobal).addTo(menuAlumnoPanel);


        windowAlumno.setComponent(guiAlumnoPanel);

//        ===================================VENTANA DE ADMINS==================================
        BasicWindow windowAdmin = new BasicWindow();
        windowAdmin.setTitle("Administración de la aplicación");
        windowAdmin.setHints(List.of(Window.Hint.CENTERED));

        Panel guiAdminPanel = new Panel(new GridLayout(2));

        Panel menuAdminPanel = new Panel(new GridLayout(1));
        guiAdminPanel.addComponent(menuAdminPanel.withBorder(Borders.singleLine("Menú principal")));

        Panel menuAdminAcc = new Panel(new LinearLayout());

        new Label(mensajeMenuInicial).addTo(menuAdminAcc);
        guiAdminPanel.addComponent(menuAdminAcc.withBorder(Borders.singleLine("Área de acciones (?)")));

        new ActionListBox(new TerminalSize(30, 5))
                .addItem("Crear nueva materia", () -> {
                    menuAdminAcc.removeAllComponents();
                    Panel creacionMaterias = new Panel(new GridLayout(2));
                    menuAdminAcc.addComponent(creacionMaterias.withBorder(Borders.singleLine("Creación de materias")));

                    new Label("Ingresa los datos requeridos").addTo(creacionMaterias);
                    new EmptySpace(new TerminalSize(0, 0)).addTo(creacionMaterias);

                    new Label("Nombre de la materia: ")
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER))
                            .addTo(creacionMaterias);
                    final TextBox nombreMateria = new TextBox(new TerminalSize(20, 1));
                    nombreMateria.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(creacionMaterias);

                    new Label("Profesor de la materia: ")
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER))
                            .addTo(creacionMaterias);
                    final TextBox nombreProfMateria = new TextBox(new TerminalSize(20, 1));
                    nombreProfMateria.setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(creacionMaterias);

                    new Label("Grupo: ")
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER))
                            .addTo(creacionMaterias);
                    final TextBox grupoMateria = new TextBox(new TerminalSize(20, 1));
                    grupoMateria.setValidationPattern(Pattern.compile("[0-9]+"))
                            .setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE))
                            .addTo(creacionMaterias);

                    new EmptySpace(new TerminalSize(0, 0)).addTo(creacionMaterias);

                    Label areaSeleccionadaLabel = new Label("")
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER));

                    new Button("Selecciona un área", () -> {
                        ActionListDialogBuilder listadoAreasA = new ActionListDialogBuilder();
                        listadoAreasA.setTitle("Areas disponibles").setDescription("Selecciona un área para la materia");
                        String areaSeleccionada = AdminMateria.getAreas()[0];
                        areaSeleccionadaLabel.setText(areaSeleccionada);
                        for (String area : AdminMateria.getAreas()) {
                            listadoAreasA.addAction(area, () -> areaSeleccionadaLabel.setText(area));
                        }
                        listadoAreasA.setCanCancel(true);
                        listadoAreasA.build().showDialog(gui);
                    }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(creacionMaterias);

                    new Label("Area seleccionada: ")
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER))
                            .addTo(creacionMaterias);

                    areaSeleccionadaLabel.addTo(creacionMaterias);

                    new Panel(new GridLayout(3))
                            .addComponent(new Button("Cancelar", () -> {
                                menuAdminAcc.removeAllComponents();
                                new Label(mensajeMenuInicial).addTo(menuAdminAcc);
                            }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE))
                                    .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER)))

                            .addComponent(new EmptySpace(new TerminalSize(20, 1)))

                            .addComponent(new Button("Crear materia", () -> {
                                try {
                                    if (Objects.equals(nombreMateria.getText(), ""))
                                        throw new Exception("Falta el nombre de la materia");
                                    if (Objects.equals(nombreProfMateria.getText(), ""))
                                        throw new Exception("Falta el nombre del profesor");
                                    if (Objects.equals(grupoMateria.getText(), ""))
                                        throw new Exception("Falta el semestre");
                                    if (Objects.equals(areaSeleccionadaLabel.getText(), ""))
                                        throw new Exception("Debes seleccionar un área");

                                    GuiProgram.dbMaterias.agregarMateria(new Materia(nombreMateria.getText(),
                                            Integer.parseInt(grupoMateria.getText()), nombreProfMateria.getText(), areaSeleccionadaLabel.getText()));

                                    GuiProgram.dbMaterias.saveDB();

                                    new MessageDialogBuilder().setTitle("Aviso").setText("La materia se creó exitosamente")
                                            .addButton(MessageDialogButton.OK).build().showDialog(gui);

                                    nombreMateria.setText("");
                                    nombreProfMateria.setText("");
                                    grupoMateria.setText("");
                                    areaSeleccionadaLabel.setText("");

                                } catch (Exception e) {
                                    new MessageDialogBuilder().setTitle("Advertencia").setText(e.getMessage())
                                            .addButton(MessageDialogButton.Retry)
                                            .build().showDialog(gui);
                                }
                            }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE))
                                    .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER)))
                            .addTo(menuAdminAcc);

                })
                .addItem("Salir", () -> {
                    log.sendInfo("Cerrando la interfaz del administrador");
                    menuAdminAcc.removeAllComponents();
                    new Label("Selecciona una opción.\n" +
                            "Utiliza <Tab> para moverte entre menús.\n" +
                            "Usa las flechas " + Symbols.ARROW_UP + " y " + Symbols.ARROW_DOWN + "\n" +
                            "para moverte dentro de los menús.").addTo(menuAdminAcc);

                    GuiProgram.currentUser = null;
                    gui.removeWindow(gui.getActiveWindow());
                }).setTheme(GuiProgram.temaGlobal).addTo(menuAdminPanel);

        windowAdmin.setComponent(guiAdminPanel);

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

                Alumno alumno = new Alumno(userRegister.getText(), userNameRegister.getText(),
                        passRegister.getText(), Integer.parseInt(semesterRegister.getText()));
                GuiProgram.dbAlumnos.agregarAlumno(alumno);
                GuiProgram.dbAlumnos.saveDB();

                userNameRegister.setText("");
                userRegister.setText("");
                passRegister.setText("");
                semesterRegister.setText("");

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

                try {
                    GuiProgram.currentUser = dbAlumnos.readAlumno(userTxt.getText(), pwdTxt.getText());
                    if (Objects.equals(GuiProgram.currentUser.getUsername(), null)) {
                        GuiProgram.currentUser = dbadmins.readAdmins(userTxt.getText(), pwdTxt.getText());
                        if (Objects.equals(GuiProgram.currentUser.getUsername(), null)) throw new Exception();
                    }
                } catch (Exception e) {
                    throw new Exception("Usuario inválido");
                }


                userTxt.setText("");
                pwdTxt.setText("");
                if (GuiProgram.currentUser.getCurrentUser() instanceof Alumno) {
                    log.sendInfo("Iniciando interfaz de alumnos.");
                    log.sendInfo("Sesión iniciada por:\n%s".formatted(GuiProgram.currentUser));
                    gui.addWindowAndWait(windowAlumno);
                } else if (GuiProgram.currentUser.getCurrentUser() instanceof Administrador) {
                    log.sendInfo("Iniciando interfaz de administradores.");
                    log.sendInfo("Sesión iniciada por:\n%s".formatted(GuiProgram.currentUser));
                    gui.addWindowAndWait(windowAdmin);
                }

            } catch (Exception e) {
                new MessageDialogBuilder().setTitle("Advertencia").setText(e.getMessage())
                        .addButton(MessageDialogButton.Retry).build().showDialog(gui);
            }

        }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(loginPanel);
        new Button("Registrarse", () -> gui.addWindowAndWait(registerWindow))
                .setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(loginPanel);
        new Button("Salir", () -> {
            log.sendInfo("Finalizando programa.");
            GuiProgram.dbMaterias.saveDB();
            log.sendInfo("Materias actualizados.");
            GuiProgram.dbAlumnos.saveDB();
            log.sendInfo("Alumnos actualizados.");
            GuiProgram.dbadmins.saveDB();
            log.sendInfo("Administradores actualizados.");
            log.sendInfo("Programa finalizado.");
            System.exit(0);
        }).setTheme(new SimpleTheme(TextColor.ANSI.BLACK, TextColor.ANSI.WHITE)).addTo(loginPanel);

        // Finalmente se agrega el panel en la ventana
        loginWindow.setComponent(loginPanel);


//        GUI PRINCIPAL
        gui.addWindowAndWait(loginWindow);

    }
}
