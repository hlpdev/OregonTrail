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
        // Loads the game's settings from a file, creates a new settings file if it doesn't already exist
        GameState = null;
        Settings.Load();

        // Create a swing terminal (essentially just a terminal emulator)
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setTerminalEmulatorFontConfiguration(SwingTerminalFontConfiguration.getDefaultOfSize(Settings.Settings.FontSize));
        terminal = terminalFactory.createSwingTerminal();
        terminal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the game if the window closes
        terminal.setVisible(true);                               // Makes the terminal window visible
        terminal.setTitle("The Oregon Trail - github.com/hlpdev/OregonTrail"); // Sets the terminal window's title
        terminal.setSize(new Dimension(825, 635));  // Sets the initial size of the terminal window
        terminal.setMinimumSize(new Dimension(825, 635));   // Sets the minimum size of the terminal window
        terminal.setCursorVisible(false); // Sets the cursor block to be invisible unless the player can select an option in any given menu
        terminal.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizes the window by default

        // Create screen
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Go to the main menu
        try {
            MainMenu.Execute(terminal, screen);
        } catch (Exception e) {
            // Show an error popup instead of terminating the process in the event of an exception thrown
            ErrorHandler.ShowError(terminal, screen, e);
        }

        // Dispose of the screen and terminal
        screen.stopScreen();
        terminal.dispose();
    }
}