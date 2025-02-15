package hlpdev.oregontrail.game;

import com.googlecode.lanterna.TerminalPosition;
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

        Label label = new Label(String.format("""
                Congratulations! You reached
                Oregon City!
                
                Total Score Earned: %d
                """, totalPoints));
        label.setSize(new TerminalSize(34, 4));
        label.setPosition(new TerminalPosition(2, 1));
        panel.addComponent(label);

        Button viewHowPointsAreCalculated = new Button("View Points Breakdown");
        viewHowPointsAreCalculated.setPosition(new TerminalPosition(2, 7));
        viewHowPointsAreCalculated.setSize(new TerminalSize(25, 1));
        panel.addComponent(viewHowPointsAreCalculated);
        viewHowPointsAreCalculated.addListener((_) -> {
            window.close();
            PointsBreakdown(terminal, screen, pointsForCash, pointsForAnimalsKilled, pointsForFood, pointsForAmmunition, pointsForClothing, pointsForWagonWheels, pointsForWagonAxles, pointsForWagonTongues, pointsForMedicine, pointsForOxen, pointsForMembers, multiplierForRole, totalPoints);
        });

        Button quit = new Button("Quit");
        quit.setPosition(new TerminalPosition(2, 8));
        quit.setSize(new TerminalSize(8, 1));
        panel.addComponent(quit);
        quit.addListener((_) -> System.exit(0));

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    private static void PointsBreakdown(Terminal terminal, Screen screen, int cashPoints, int huntingPoints, int foodPoints, int ammunitionPoints, int clothingPoints, int wagonWheelPoints, int wagonAxlePoints, int wagonTonguePoints, int medicinePoints, int oxenPoints, int memberPoints, double multiplier, int total) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label breakdown = new Label(String.format("""
                Points are calculated based on what events occurred during your journey.
                
                Money                        %d points
                Animals Hunted               %d points
                Food in Storage              %d points
                Ammunition in Storage        %d points
                Clothing in Storage          %d points
                Wagon Wheels in Storage      %d points
                Wagon Axles in Storage       %d points
                Wagon Tongues in Storage     %d points
                Medicine in Storage          %d points
                Oxen in Storage              %d points
                Family Members Kept Alive    %d points
                
                Profession Multiplier        %.1fx
                
                Total Points Earned          %d points
                """, cashPoints, huntingPoints, foodPoints, ammunitionPoints, clothingPoints, wagonWheelPoints, wagonAxlePoints, wagonTonguePoints, medicinePoints, oxenPoints, memberPoints, multiplier, total));
        breakdown.setSize(new TerminalSize(72, 17));
        breakdown.setPosition(new TerminalPosition(2, 1));
        panel.addComponent(breakdown);

        Button goBack = new Button("Go Back");
        goBack.setPosition(new TerminalPosition(2, 19));
        goBack.setSize(new TerminalSize(11, 1));
        panel.addComponent(goBack);
        goBack.addListener((_) -> {
            window.close();
            Execute(terminal, screen);
        });

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }
}
