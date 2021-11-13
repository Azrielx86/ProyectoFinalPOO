package com.fiunam.main;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        BasicWindow window = new BasicWindow();
        window.setHints(List.of(Window.Hint.CENTERED));
        Panel panel = new Panel(new GridLayout(2)).addComponent(new Label("Ventana Login"));
        panel.addComponent(new EmptySpace(new TerminalSize(10, 10)));
        window.setComponent(panel);

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.CYAN));
        gui.addWindow(window);
        gui.waitForWindowToClose(window);
        gui.setActiveWindow(window);

    }
}
