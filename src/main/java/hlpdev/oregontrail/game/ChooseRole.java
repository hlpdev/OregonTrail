package hlpdev.oregontrail.game;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import hlpdev.oregontrail.Main;
import hlpdev.oregontrail.Profession;

import java.util.List;

public class ChooseRole {

    public static void Execute(Terminal terminal, Screen screen) throws Exception {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(76, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label choices = new Label("""
            Many kinds of people made the trip to Oregon.
            
            You may:
            
            1. Be a banker from Boston
            2. Be a carpenter from Ohio
            3. Be a farmer from Illinois
            """);
        choices.setPosition(new TerminalPosition(2, 1));
        choices.setSize(new TerminalSize(76, 15));
        panel.addComponent(choices);

        Button differences = new Button("Find out the differences between these choices");
        differences.setPosition(new TerminalPosition(2, 9));
        differences.setSize(new TerminalSize(50, 1));
        differences.addListener((_) -> {
            window.close();
            try {
                Differences.Execute(terminal, screen);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(differences);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.addItem("Banker from Boston");
        comboBox.addItem("Carpenter from Ohio");
        comboBox.addItem("Farmer from Illinois");
        comboBox.setPosition(new TerminalPosition(2, 11));
        comboBox.setSize(new TerminalSize(25, 5));
        panel.addComponent(comboBox);

        Button continueGame = new Button("Continue");
        continueGame.setPosition(new TerminalPosition(2, 13));
        continueGame.setSize(new TerminalSize(12, 1));
        continueGame.addListener((_) -> {
            window.close();
            switch (comboBox.getSelectedItem()) {
                case "Banker from Boston": {
                    Main.GameState.profession = Profession.BANKER;
                    Main.GameState.totalMoney = 1600;
                    break;
                }
                case "Carpenter from Ohio": {
                    Main.GameState.profession = Profession.CARPENTER;
                    Main.GameState.totalMoney = 800;
                    break;
                }
                case "Farmer from Illinois": {
                    Main.GameState.profession = Profession.FARMER;
                    Main.GameState.totalMoney = 400;
                    break;
                }
            }

            try {
                ChooseName.Execute(terminal, screen);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(continueGame);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    public static class Differences {

        public static void Execute(Terminal terminal, Screen screen) throws Exception {
            final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
            final Window window = new BasicWindow();
            window.setFixedSize(new TerminalSize(76, 28));
            window.setHints(List.of(Window.Hint.CENTERED));

            Panel panel = new Panel(new AbsoluteLayout());

            Label info = new Label("""
            Traveling to Oregon isn't easy!
            But if you're a banker, you'll
            have more money for supplies
            and services than a carpenter
            or a farmer.
            
            However, the harder you have
            to try, the more points you
            deserve! Therefore, the
            farmer earns the greatest
            number of points and the
            banker earns the least.
            """);
            info.setPosition(new TerminalPosition(2, 1));
            info.setSize(new TerminalSize(76, 15));
            panel.addComponent(info);

            Button continueGame = new Button("Continue");
            continueGame.setPosition(new TerminalPosition(2, 15));
            continueGame.setSize(new TerminalSize(12, 1));
            continueGame.addListener((_) -> {
                window.close();
                try {
                    ChooseRole.Execute(terminal, screen);
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
