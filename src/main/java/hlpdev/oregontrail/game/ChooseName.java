package hlpdev.oregontrail.game;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import hlpdev.oregontrail.Main;
import hlpdev.oregontrail.PartyMember;

import java.util.List;

public class ChooseName {
    public static void Execute(Terminal terminal, Screen screen) throws Exception {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(76, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label label = new Label("""
            What is the first name of the
            wagon leader?
            """);
        label.setPosition(new TerminalPosition(2, 1));
        label.setSize(new TerminalSize(76, 2));
        panel.addComponent(label);

        TextBox nameInput = new TextBox();
        nameInput.setPosition(new TerminalPosition(2, 4));
        nameInput.setSize(new TerminalSize(25, 1));
        panel.addComponent(nameInput);

        Button continueGame = new Button("Continue");
        continueGame.setPosition(new TerminalPosition(2, 6));
        continueGame.setSize(new TerminalSize(12, 1));
        continueGame.addListener((_) -> {
            if (nameInput.getText().isBlank()) {
                MessageDialog.showMessageDialog(textGui, "Error", "Your name cannot be blank!", MessageDialogButton.Retry);
                return;
            }

            Main.GameState.playerMember = new PartyMember(nameInput.getText().trim(), 100, true, 0, false);

            window.close();
            try {
                ChoosePartyNames.Execute(terminal, screen);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(continueGame);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }
}
