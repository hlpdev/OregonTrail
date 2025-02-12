package hlpdev.oregontrail.game;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import hlpdev.oregontrail.Main;

import java.io.IOException;
import java.util.List;

public class ChooseMonth {
    public static void Execute(Terminal terminal, Screen screen) throws Exception {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(76, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label label = new Label("""
            It is 1848. Your jumping off
            place for Oregon is Independence,
            Missouri. You must decide which 
            month to leave Independence.
            """);
        label.setPosition(new TerminalPosition(2, 1));
        label.setSize(new TerminalSize(76, 4));
        panel.addComponent(label);

        Button adviceButton = new Button("Ask for advice");
        adviceButton.setSize(new TerminalSize(18, 1));
        adviceButton.setPosition(new TerminalPosition(2, 6));
        adviceButton.addListener((_) -> {
            window.close();
            try {
                ChooseMonth.Advice.Execute(terminal, screen);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(adviceButton);

        ComboBox<String> monthInput = new ComboBox<>();
        monthInput.addItem("March");
        monthInput.addItem("April");
        monthInput.addItem("May");
        monthInput.addItem("June");
        monthInput.addItem("July");
        monthInput.setSize(new TerminalSize(22, 1));
        monthInput.setPosition(new TerminalPosition(2, 8));
        panel.addComponent(monthInput);

        Button continueButton = new Button("Continue");
        continueButton.setSize(new TerminalSize(18, 1));
        continueButton.setPosition(new TerminalPosition(2, 10));
        continueButton.addListener((_) -> {
            Main.GameState.departureMonth = 2 + monthInput.getSelectedIndex();

            window.close();
            try {
                DisplayInitialSupplies.Execute(terminal, screen);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(continueButton);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    public static class Advice {
        public static void Execute(Terminal terminal, Screen screen) throws Exception {
            final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
            final Window window = new BasicWindow();
            window.setFixedSize(new TerminalSize(76, 28));
            window.setHints(List.of(Window.Hint.CENTERED));

            Panel panel = new Panel(new AbsoluteLayout());

            Label label = new Label("""
            You attend a public meeting held
            for "folks with the California -
            Oregon fever." You're told:
            
            If you leave too early, there
            won't be any grass for your
            oxen to eat. If you leave too
            late, you may not get to Oregon
            before winter comes. If you
            leave at just the right time,
            there will be green grass and
            the weather will still be cool.
            """);
            label.setPosition(new TerminalPosition(2, 1));
            label.setSize(new TerminalSize(76, 12));
            panel.addComponent(label);

            Button continueGame = new Button("Continue");
            continueGame.setPosition(new TerminalPosition(2, 14));
            continueGame.setSize(new TerminalSize(12, 1));
            continueGame.addListener((_) -> {
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
}
