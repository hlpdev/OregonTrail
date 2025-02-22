package hlpdev.oregontrail.game;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import hlpdev.oregontrail.Main;

import java.util.List;

public class DisplayInitialSupplies {
    /**
     * Displays the player's initial money (changes based on profession)
     * @param terminal
     * @param screen
     * @throws Exception
     */
    public static void Execute(Terminal terminal, Screen screen) throws Exception {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(76, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label label = new Label(String.format("""
            Before leaving Independence you
            should buy equipment and
            supplies. You have $%,.2f in
            cash, but you don't have to
            spend it all now.
            
            You can buy whatever you need at
            Matt's General Store.
            """, Main.GameState.totalMoney));
        label.setPosition(new TerminalPosition(2, 1));
        label.setSize(new TerminalSize(76, 8));
        panel.addComponent(label);

        Button continueButton = new Button("Continue");
        continueButton.setSize(new TerminalSize(18, 1));
        continueButton.setPosition(new TerminalPosition(2, 10));
        continueButton.addListener((_) -> {
            window.close();
            try {
                GeneralStore.Execute(terminal, screen);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(continueButton);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }
}
