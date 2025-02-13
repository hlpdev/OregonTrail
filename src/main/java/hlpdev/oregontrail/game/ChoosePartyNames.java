package hlpdev.oregontrail.game;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import hlpdev.oregontrail.Main;
import hlpdev.oregontrail.records.PartyMember;

import java.util.List;

public class ChoosePartyNames {
    public static void Execute(Terminal terminal, Screen screen) throws Exception {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(76, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label label = new Label("""
            What are the first names of the
            four other members in your party?
            """);
        label.setPosition(new TerminalPosition(2, 1));
        label.setSize(new TerminalSize(76, 2));
        panel.addComponent(label);

        TextBox nameInput = new TextBox();
        nameInput.setText(Main.GameState.playerMember.name());
        nameInput.setPosition(new TerminalPosition(2, 4));
        nameInput.setSize(new TerminalSize(25, 1));
        nameInput.setReadOnly(true);
        panel.addComponent(nameInput);

        TextBox memberTwoInput = new TextBox();
        memberTwoInput.setPosition(new TerminalPosition(2, 5));
        memberTwoInput.setSize(new TerminalSize(25, 1));
        panel.addComponent(memberTwoInput);

        TextBox memberThreeInput = new TextBox();
        memberThreeInput.setPosition(new TerminalPosition(2, 6));
        memberThreeInput.setSize(new TerminalSize(25, 1));
        panel.addComponent(memberThreeInput);

        TextBox memberFourInput = new TextBox();
        memberFourInput.setPosition(new TerminalPosition(2, 7));
        memberFourInput.setSize(new TerminalSize(25, 1));
        panel.addComponent(memberFourInput);

        TextBox memberFiveInput = new TextBox();
        memberFiveInput.setPosition(new TerminalPosition(2, 8));
        memberFiveInput.setSize(new TerminalSize(25, 1));
        panel.addComponent(memberFiveInput);

        Button continueGame = new Button("Continue");
        continueGame.setPosition(new TerminalPosition(2, 10));
        continueGame.setSize(new TerminalSize(12, 1));
        continueGame.addListener((_) -> {
            if (memberTwoInput.getText().isBlank()) {
                MessageDialog.showMessageDialog(textGui, "Error", "Member two's name cannot be blank!", MessageDialogButton.Retry);
                return;
            }

            if (memberThreeInput.getText().isBlank()) {
                MessageDialog.showMessageDialog(textGui, "Error", "Member two's name cannot be blank!", MessageDialogButton.Retry);
                return;
            }

            if (memberFourInput.getText().isBlank()) {
                MessageDialog.showMessageDialog(textGui, "Error", "Member two's name cannot be blank!", MessageDialogButton.Retry);
                return;
            }

            if (memberFiveInput.getText().isBlank()) {
                MessageDialog.showMessageDialog(textGui, "Error", "Member two's name cannot be blank!", MessageDialogButton.Retry);
                return;
            }

            Main.GameState.partyMembers.add(new PartyMember(memberTwoInput.getText().trim(), 100, true, 0, false));
            Main.GameState.partyMembers.add(new PartyMember(memberThreeInput.getText().trim(), 100, true, 0, false));
            Main.GameState.partyMembers.add(new PartyMember(memberFourInput.getText().trim(), 100, true, 0, false));
            Main.GameState.partyMembers.add(new PartyMember(memberFiveInput.getText().trim(), 100, true, 0, false));

            window.close();
            try {
                ChooseMonth.Execute(terminal, screen);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(continueGame);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }
}
