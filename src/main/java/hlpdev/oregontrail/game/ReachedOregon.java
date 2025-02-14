package hlpdev.oregontrail.game;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import hlpdev.oregontrail.Main;

import java.util.List;

public class ReachedOregon {
    public static void Execute(Terminal terminal, Screen screen) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        int pointsForCash = (int)Math.floor(Main.GameState.totalMoney * 2.0);
        int pointsForAnimalsKilled = Main.GameState.animalsKilled * 5;
        int pointsForFood = (int)Math.floor(Main.GameState.food * 0.08);
        int pointsForAmmunition = (int)Math.floor(Main.GameState.ammunition * 0.01);
        int pointsForClothing = Main.GameState.clothing * 2;
        int pointsForWagonWheels = Main.GameState.wagonWheels * 10;
        int pointsForWagonAxles = Main.GameState.wagonAxles * 10;
        int pointsForWagonTongues = Main.GameState.wagonTongues * 10;
        int pointsForMedicine = Main.GameState.medicine * 20;
        int pointsForOxen = Main.GameState.oxen * 50;
        int pointsForMembers = Main.GameState.partyMembers.size() * 300;

        double multiplierForRole = switch (Main.GameState.profession) {
            case BANKER -> 1.0;
            case CARPENTER -> 1.5;
            case FARMER -> 2.0;
        };

        int totalPoints = (int)Math.floor((pointsForCash +
                pointsForAnimalsKilled +
                pointsForFood +
                pointsForAmmunition +
                pointsForClothing +
                pointsForWagonWheels +
                pointsForWagonAxles +
                pointsForWagonTongues +
                pointsForMedicine +
                pointsForOxen +
                pointsForMembers
        ) * multiplierForRole);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }
}
