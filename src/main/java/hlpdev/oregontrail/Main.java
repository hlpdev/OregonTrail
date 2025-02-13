package hlpdev.oregontrail;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import hlpdev.oregontrail.menus.MainMenu;
import hlpdev.oregontrail.util.ErrorHandler;
import hlpdev.oregontrail.util.Settings;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static GameState GameState;
    public static SwingTerminalFrame terminal;

    public static void main(String[] args) throws Exception {
        GameState = null;
        Settings.Load();

        // Create a swing terminal (essentially just a terminal emulator)
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setTerminalEmulatorFontConfiguration(SwingTerminalFontConfiguration.getDefaultOfSize(Settings.Settings.FontSize));
        terminal = terminalFactory.createSwingTerminal();
        terminal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        terminal.setVisible(true);
        terminal.setTitle("The Oregon Trail - github.com/hlpdev/OregonTrail");
        terminal.setSize(new Dimension(825, 635));
        terminal.setMinimumSize(new Dimension(825, 635));
        terminal.setCursorVisible(false);
        terminal.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create screen
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Go to the main menu
        try {
            MainMenu.Execute(terminal, screen);
        } catch (Exception e) {
            ErrorHandler.ShowError(terminal, screen, e);
        }

        // Dispose of the screen and terminal
        screen.stopScreen();
        terminal.dispose();
    }
}