package hlpdev.oregontrail;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import hlpdev.oregontrail.menus.MainMenu;
import hlpdev.oregontrail.util.OperatingSystem;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static GameState GameState;

    public static void main(String[] args) throws Exception {
        GameState = null;

        short fontSize = 8;
        if (OperatingSystem.isWindows()) {
            fontSize = 16;
        }

        // Create a swing terminal (essentially just a terminal emulator)
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setTerminalEmulatorFontConfiguration(SwingTerminalFontConfiguration.getDefaultOfSize(fontSize));
        SwingTerminalFrame terminal = terminalFactory.createSwingTerminal();
        terminal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        terminal.setVisible(true);
        terminal.setTitle("The Oregon Trail (Java Remake)");
        terminal.setSize(new Dimension(825, 635));
        terminal.setMinimumSize(new Dimension(825, 635));
        terminal.setCursorVisible(false);
        terminal.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create screen
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Go to the main menu
        MainMenu.Execute(terminal, screen);

        // Dispose of the screen and terminal
        screen.stopScreen();
        terminal.dispose();
    }
}