package hlpdev.oregontrail.menus;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import hlpdev.oregontrail.GameState;
import hlpdev.oregontrail.Main;
import hlpdev.oregontrail.game.ChooseRole;

import java.util.List;

public class MainMenu {

    /**
     * Displays the main menu to the player when the game is started
     * @param terminal
     * @param screen
     * @throws Exception
     */
    public static void Execute(Terminal terminal, Screen screen) throws Exception {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow("Main Menu");
        window.setFixedSize(new TerminalSize(76, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label title = new Label("""
            ▗▄▄▄▖▗▖ ▗▖▗▄▄▄▖  ▗▄▖ ▗▄▄▖ ▗▄▄▄▖ ▗▄▄▖ ▗▄▖ ▗▖  ▗▖ ▗▄▄▄▖▗▄▄▖  ▗▄▖ ▗▄▄▄▖▗▖  \s
              █  ▐▌ ▐▌▐▌    ▐▌ ▐▌▐▌ ▐▌▐▌   ▐▌   ▐▌ ▐▌▐▛▚▖▐▌   █  ▐▌ ▐▌▐▌ ▐▌  █  ▐▌  \s
              █  ▐▛▀▜▌▐▛▀▀▘ ▐▌ ▐▌▐▛▀▚▖▐▛▀▀▘▐▌▝▜▌▐▌ ▐▌▐▌ ▝▜▌   █  ▐▛▀▚▖▐▛▀▜▌  █  ▐▌  \s
              █  ▐▌ ▐▌▐▙▄▄▖ ▝▚▄▞▘▐▌ ▐▌▐▙▄▄▖▝▚▄▞▘▝▚▄▞▘▐▌  ▐▌   █  ▐▌ ▐▌▐▌ ▐▌▗▄█▄▖▐▙▄▄▖
            
                                            Java Remake
                                         by Hunter Pollock
            """);
        title.setPosition(new TerminalPosition(2, 1));
        title.setSize(new TerminalSize(76, 7));
        panel.addComponent(title);

        RadioBoxList<String> menu = new RadioBoxList<>();
        menu.addItem("Start Game");
        menu.addItem("Load Game");
        menu.addItem("Settings");
        menu.addItem("Exit");
        menu.setPosition(new TerminalPosition(30, 13));
        menu.setSize(new TerminalSize(20, 5));

        menu.addListener((index, oldIndex) -> {
            switch (index) {
                case 0: {
                    window.close();
                    Main.GameState = new GameState();
                    try {
                        ChooseRole.Execute(terminal, screen);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case 1: {
                    window.close();
                    try {
                        LoadMenu.Execute(terminal, screen);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case 2: {
                    window.close();
                    try {
                        SettingsMenu.Execute(terminal, screen);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case 3: {
                    window.close();
                    break;
                }
            }
        });

        panel.addComponent(menu);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

}
