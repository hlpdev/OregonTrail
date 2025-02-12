package hlpdev.oregontrail.menus;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import hlpdev.oregontrail.util.Settings;
import hlpdev.oregontrail.util.Utilities;

import java.util.List;

public class SettingsMenu {
    public static void Execute(Terminal terminal, Screen screen) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow("Main Menu");
        window.setFixedSize(new TerminalSize(76, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label title = new Label("Settings");
        title.setPosition(new TerminalPosition(2, 1));
        title.setSize(new TerminalSize(76, 1));
        panel.addComponent(title);

        Label fontSizeLabel = new Label("Font Size: ");
        fontSizeLabel.setPosition(new TerminalPosition(2, 3));
        fontSizeLabel.setSize(new TerminalSize(11, 1));
        panel.addComponent(fontSizeLabel);

        TextBox fontSizeInput = new TextBox(Settings.Settings.FontSize.toString());
        fontSizeInput.setPosition(new TerminalPosition(13, 3));
        fontSizeInput.setSize(new TerminalSize(3, 1));
        panel.addComponent(fontSizeInput);

        Label fontSizeWarningLabel = new Label("""
                A font size too high can result in
                some parts of the game getting cut
                off on the sides of your screen.
                You cannot change this option after
                starting a game!""");
        fontSizeWarningLabel.setPosition(new TerminalPosition(25, 1));
        fontSizeWarningLabel.setSize(new TerminalSize(36, 5));
        panel.addComponent(fontSizeWarningLabel);

        Button saveChangesButton = new Button("Save Changes");
        saveChangesButton.setPosition(new TerminalPosition(2, 6));
        saveChangesButton.setSize(new TerminalSize(16, 1));
        saveChangesButton.addListener((_) -> {
            try {
                Integer.parseInt(fontSizeInput.getText());
            } catch (NumberFormatException e) {
                MessageDialog.showMessageDialog(textGui, "Error", "FontSize must be an integer", MessageDialogButton.Retry);
                return;
            }

            try {
                Settings.Settings.FontSize = Integer.parseInt(fontSizeInput.getText());
                Settings.Save();
                Utilities.Relaunch();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(saveChangesButton);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }
}
