package hlpdev.oregontrail.game;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import hlpdev.oregontrail.Main;
import hlpdev.oregontrail.npcs.Names;

import java.util.List;

public class GeneralStore {
    private static int purchasingFood = 0;
    private static int purchasingAmmunition = 0;
    private static int purchasingClothing = 0;
    private static int purchasingWagonWheels = 0;
    private static int purchasingWagonAxles = 0;
    private static int purchasingWagonTongues = 0;
    private static int purchasingMedicine = 0;
    private static int purchasingOxen = 0;

    /**
     * Display's the store as well as buttons to buy a specific product
     * @param terminal
     * @param screen
     */
    public static void Execute(Terminal terminal, Screen screen) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(76, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label label = new Label("""
            Hello, I'm Matt. So you're going
            to Oregon! I can fix you up with
            what you need:                                      ____________
                                                  _,           |            |
              - a team of oxen to pull         ,'*\\            |            |
                your wagon                     `'\\ #-----_     |____________|
                                                  `.#===========\\ _      _ /
              - clothing for both                  H`**'H        /.\\____/.\\
                summer and winter                  H    H        \\_/    \\_/
            """);

        label.setPosition(new TerminalPosition(2, 1));
        label.setSize(new TerminalSize(67, 9));
        panel.addComponent(label);

        Button continueButton = new Button("Continue");
        continueButton.setSize(new TerminalSize(18, 1));
        continueButton.setPosition(new TerminalPosition(2, 11));
        continueButton.addListener((_) -> {
            window.close();
            try {
                GeneralStore.PartTwo(terminal, screen);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(continueButton);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    private static void PartTwo(Terminal terminal, Screen screen) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label label = new Label("""
            Hello, I'm Matt. So you're going
            to Oregon! I can fix you up with
            what you need:                                      ____________
                                                  _,           |            |
              - plenty of food for the         ,'*\\            |            |
                trip                           `'\\ #-----_     |____________|
                                                  `.#===========\\ _      _ /
              - ammunition for your                H`**'H        /.\\____/.\\
                rifles                             H    H        \\_/    \\_/
            
              - spare parts for your
                wagon
            """);

        label.setPosition(new TerminalPosition(2, 1));
        label.setSize(new TerminalSize(67, 12));
        panel.addComponent(label);

        Button continueButton = new Button("Continue");
        continueButton.setSize(new TerminalSize(18, 1));
        continueButton.setPosition(new TerminalPosition(2, 14));
        continueButton.addListener((_) -> {
            window.close();
            try {
                StoreMainMenu(terminal, screen, true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(continueButton);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    public static void StoreMainMenu(Terminal terminal, Screen screen, boolean independence) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        String month = switch (Main.GameState.departureMonth) {
            case 2 -> "March";
            case 3 -> "April";
            case 4 -> "May";
            case 5 -> "June";
            case 6 -> "July";
            case 7 -> "August";
            case 8 -> "September";
            case 9 -> "October";
            case 10 -> "November";
            case 11 -> "December";
            default -> "";
        };

        Label topBar = new Label("""
            ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
            """);
        topBar.setPosition(new TerminalPosition(2, 1));
        topBar.setSize(new TerminalSize(62, 1));
        panel.addComponent(topBar);

        String titleLabel;
        if (independence) {
            titleLabel = String.format("""
                                Matt's General Store
                               Independence, Missouri
                                    %s 1, 1848
            """, month);
        } else {
            titleLabel = String.format("""
                          %s's General Store
                               %s
                                    %s %d, 1848
            """, Names.getRandomName(), Main.GameState.currentLocation.name, month, Main.GameState.currentDay);
        }

        Label title = new Label(titleLabel);
        title.setPosition(new TerminalPosition(2, 2));
        title.setSize(new TerminalSize(62, 3));
        title.addStyle(SGR.BOLD);
        panel.addComponent(title);

        Label bottomBar = new Label("""
            ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀
            """);
        bottomBar.setPosition(new TerminalPosition(2, 5));
        bottomBar.setSize(new TerminalSize(62, 1));
        panel.addComponent(bottomBar);

        Label storeItems = new Label(String.format("""
                        1. Oxen                     $%,.2f
                        2. Food                     $%,.2f
                        3. Clothing                 $%,.2f
                        4. Ammunition               $%,.2f
                        5. Spare parts              $%,.2f
                        6. Medicine                 $%,.2f
            """,
                purchasingOxen * 20.0f,
                purchasingFood * 0.10f,
                purchasingClothing * 10.0f,
                purchasingAmmunition * 0.10f,
                (purchasingWagonWheels + purchasingWagonAxles + purchasingWagonTongues) * 10.0f,
                purchasingMedicine * 5.0f));
        storeItems.setPosition(new TerminalPosition(2, 6));
        storeItems.setSize(new TerminalSize(62, 6));
        panel.addComponent(storeItems);

        Label bottomBar2 = new Label("""
            ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
            """);
        bottomBar2.setPosition(new TerminalPosition(2, 12));
        bottomBar2.setSize(new TerminalSize(62, 1));
        panel.addComponent(bottomBar2);

        Label totalBill = new Label(String.format("""
                            Total bill:             $%,.2f
            """, purchasingOxen * 20.0f +
                purchasingFood * 0.10f +
                purchasingClothing * 10.0f +
                purchasingAmmunition * 0.10f +
                (purchasingWagonWheels + purchasingWagonAxles + purchasingWagonTongues) * 10.0f +
                purchasingMedicine * 5.0f));
        totalBill.setPosition(new TerminalPosition(2, 13));
        totalBill.setSize(new TerminalSize(62, 1));
        panel.addComponent(totalBill);

        Label balance = new Label(String.format("""
                       Amount you have:             $%,.2f
            """, Main.GameState.totalMoney));
        balance.setPosition(new TerminalPosition(2, 15));
        balance.setSize(new TerminalSize(62, 1));
        panel.addComponent(balance);

        Button buyOxenButton = new Button("Buy Oxen");
        buyOxenButton.setPosition(new TerminalPosition(2, 17));
        buyOxenButton.setSize(new TerminalSize(19, 1));
        buyOxenButton.addListener((_) -> {
            window.close();
            try {
                BuyOxen(terminal, screen, independence);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(buyOxenButton);

        Button removeOxenButton = new Button("Remove Oxen");
        removeOxenButton.setPosition(new TerminalPosition(22, 17));
        removeOxenButton.setSize(new TerminalSize(22, 1));
        removeOxenButton.addListener((_) -> {
            purchasingOxen = 0;
            window.close();
            GeneralStore.StoreMainMenu(terminal, screen, independence);
        });
        panel.addComponent(removeOxenButton);

        Button buyFoodButton = new Button("Buy Food");
        buyFoodButton.setPosition(new TerminalPosition(2, 18));
        buyFoodButton.setSize(new TerminalSize(19, 1));
        buyFoodButton.addListener((_) -> {
            window.close();
            try {
                BuyFood(terminal, screen, independence);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(buyFoodButton);

        Button removeFoodButton = new Button("Remove Food");
        removeFoodButton.setPosition(new TerminalPosition(22, 18));
        removeFoodButton.setSize(new TerminalSize(22, 1));
        removeFoodButton.addListener((_) -> {
            purchasingFood = 0;
            window.close();
            GeneralStore.StoreMainMenu(terminal, screen, independence);
        });
        panel.addComponent(removeFoodButton);

        Button buyClothingButton = new Button("Buy Clothing");
        buyClothingButton.setPosition(new TerminalPosition(2, 19));
        buyClothingButton.setSize(new TerminalSize(19, 1));
        buyClothingButton.addListener((_) -> {
            window.close();
            try {
                BuyClothing(terminal, screen, independence);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(buyClothingButton);

        Button removeClothingButton = new Button("Remove Clothing");
        removeClothingButton.setPosition(new TerminalPosition(22, 19));
        removeClothingButton.setSize(new TerminalSize(22, 1));
        removeClothingButton.addListener((_) -> {
            purchasingClothing = 0;
            window.close();
            GeneralStore.StoreMainMenu(terminal, screen, independence);
        });
        panel.addComponent(removeClothingButton);

        Button buyAmmunitionButton = new Button("Buy Ammunition");
        buyAmmunitionButton.setPosition(new TerminalPosition(2, 20));
        buyAmmunitionButton.setSize(new TerminalSize(19, 1));
        buyAmmunitionButton.addListener((_) -> {
            window.close();
            try {
                BuyAmmunition(terminal, screen, independence);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(buyAmmunitionButton);

        Button removeAmmunitionButton = new Button("Remove Ammunition");
        removeAmmunitionButton.setPosition(new TerminalPosition(22, 20));
        removeAmmunitionButton.setSize(new TerminalSize(22, 1));
        removeAmmunitionButton.addListener((_) -> {
            purchasingAmmunition = 0;
            window.close();
            GeneralStore.StoreMainMenu(terminal, screen, independence);
        });
        panel.addComponent(removeAmmunitionButton);

        Button buySparePartsButton = new Button("Buy Spare Parts");
        buySparePartsButton.setPosition(new TerminalPosition(2, 21));
        buySparePartsButton.setSize(new TerminalSize(19, 1));
        buySparePartsButton.addListener((_) -> {
            window.close();
            try {
                BuySpareParts(terminal, screen, independence);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(buySparePartsButton);

        Button removeSparePartsButton = new Button("Remove Spare Parts");
        removeSparePartsButton.setPosition(new TerminalPosition(22, 21));
        removeSparePartsButton.setSize(new TerminalSize(22, 1));
        removeSparePartsButton.addListener((_) -> {
            purchasingWagonWheels = 0;
            purchasingWagonAxles = 0;
            purchasingWagonTongues = 0;
            window.close();
            GeneralStore.StoreMainMenu(terminal, screen, independence);
        });
        panel.addComponent(removeSparePartsButton);

        Button buyMedicineButton = new Button("Buy Medicine");
        buyMedicineButton.setPosition(new TerminalPosition(2, 22));
        buyMedicineButton.setSize(new TerminalSize(19, 1));
        buyMedicineButton.addListener((_) -> {
            window.close();
            try {
                BuyMedicine(terminal, screen, independence);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(buyMedicineButton);

        Button removeMedicineButton = new Button("Remove Medicine");
        removeMedicineButton.setPosition(new TerminalPosition(22, 22));
        removeMedicineButton.setSize(new TerminalSize(22, 1));
        removeMedicineButton.addListener((_) -> {
            purchasingMedicine = 0;
            window.close();
            GeneralStore.StoreMainMenu(terminal, screen, independence);
        });
        panel.addComponent(removeMedicineButton);

        Button continueButton = new Button("Continue");
        continueButton.setSize(new TerminalSize(18, 1));
        continueButton.setPosition(new TerminalPosition(2, 25));
        continueButton.addListener((_) -> {
            if (purchasingOxen == 0 && independence) {
                MessageDialog.showMessageDialog(textGui, "Warning", "Don't forget, you'll need oxen to pull your wagon.", MessageDialogButton.Retry);
                return;
            }

            if (purchasingOxen * 20.0f +
                    purchasingFood * 0.10f +
                    purchasingClothing * 10.0f +
                    purchasingAmmunition * 0.10f +
                    (purchasingWagonWheels + purchasingWagonAxles + purchasingWagonTongues) * 10.0f +
                    purchasingMedicine * 5.0f > Main.GameState.totalMoney) {
                MessageDialog.showMessageDialog(textGui, "Warning", "You can't afford this bill!", MessageDialogButton.Retry);
                return;
            }

            Main.GameState.totalMoney -= purchasingOxen * 20.0f +
                    purchasingFood * 0.10f +
                    purchasingClothing * 10.0f +
                    purchasingAmmunition * 0.10f +
                    (purchasingWagonWheels + purchasingWagonAxles + purchasingWagonTongues) * 10.0f +
                    purchasingMedicine * 5.0f;

            Main.GameState.oxen += purchasingOxen;
            Main.GameState.food += purchasingFood;
            Main.GameState.clothing += purchasingClothing;
            Main.GameState.ammunition += purchasingAmmunition;
            Main.GameState.wagonWheels += purchasingWagonWheels;
            Main.GameState.wagonAxles += purchasingWagonAxles;
            Main.GameState.wagonTongues += purchasingWagonTongues;
            Main.GameState.medicine += purchasingMedicine;

            purchasingOxen = 0;
            purchasingFood = 0;
            purchasingClothing = 0;
            purchasingAmmunition = 0;
            purchasingWagonWheels = 0;
            purchasingWagonAxles = 0;
            purchasingWagonTongues = 0;
            purchasingMedicine = 0;

            window.close();
            try {
                if (independence) {
                    Leaving(terminal, screen);
                } else {
                    Progress.Execute(terminal, screen);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(continueButton);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    private static void BuyOxen(Terminal terminal, Screen screen, boolean independence) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label label = new Label("""
            There are 2 oxen in a yoke;
            I recommend at least 3 yoke.
            I charge $40 a yoke.
            
            How many yoke do you want?
            """);
        label.setPosition(new TerminalPosition(2, 1));
        label.setSize(new TerminalSize(30, 5));
        panel.addComponent(label);

        TextBox input = new TextBox();
        input.setPosition(new TerminalPosition(2, 6));
        input.setSize(new TerminalSize(6, 1));
        panel.addComponent(input);

        Button continueButton = new Button("Continue");
        continueButton.setPosition(new TerminalPosition(2, 8));
        continueButton.setSize(new TerminalSize(12, 1));
        continueButton.addListener((_) -> {
            if (input.getText().isBlank()) {
                MessageDialog.showMessageDialog(textGui, "Error", "The amount you purchase cannot be blank!", MessageDialogButton.Retry);
                return;
            }

            try {
                Integer.parseInt(input.getText());
            } catch (NumberFormatException e) {
                MessageDialog.showMessageDialog(textGui, "Error", "The amount you purchase must be an integer!", MessageDialogButton.Retry);
                return;
            }

            purchasingOxen += Integer.parseInt(input.getText()) * 2;
            window.close();

            try {
                GeneralStore.StoreMainMenu(terminal, screen, independence);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(continueButton);

        Label totalBill = new Label(String.format("""
           Bill so far:  $%,.2f
           """, purchasingOxen * 20.0f +
                purchasingFood * 0.10f +
                purchasingClothing * 10.0f +
                purchasingAmmunition * 0.10f +
                (purchasingWagonWheels + purchasingWagonAxles + purchasingWagonTongues) * 10.0f +
                purchasingMedicine * 5.0f));
        totalBill.setPosition(new TerminalPosition(2, 14));
        totalBill.setSize(new TerminalSize(30, 1));
        panel.addComponent(totalBill);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    private static void BuyFood(Terminal terminal, Screen screen, boolean independence) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label label = new Label("""
            I recommend you take at
            least 200 pounds of food
            for each person in your
            family. I see that you have
            5 people in all. You'll need
            flour, sugar, bacon, and
            coffee. My price is 10
            cents a pound.
            
            How many pounds of food do you want?
            """);
        label.setPosition(new TerminalPosition(2, 1));
        label.setSize(new TerminalSize(30, 9));
        panel.addComponent(label);

        TextBox input = new TextBox();
        input.setPosition(new TerminalPosition(2, 10));
        input.setSize(new TerminalSize(6, 1));
        panel.addComponent(input);

        Button continueButton = new Button("Continue");
        continueButton.setPosition(new TerminalPosition(2, 12));
        continueButton.setSize(new TerminalSize(12, 1));
        continueButton.addListener((_) -> {
            if (input.getText().isBlank()) {
                MessageDialog.showMessageDialog(textGui, "Error", "The amount you purchase cannot be blank!", MessageDialogButton.Retry);
                return;
            }

            try {
                Integer.parseInt(input.getText());
            } catch (NumberFormatException e) {
                MessageDialog.showMessageDialog(textGui, "Error", "The amount you purchase must be an integer!", MessageDialogButton.Retry);
                return;
            }

            purchasingFood += Integer.parseInt(input.getText());
            window.close();

            try {
                GeneralStore.StoreMainMenu(terminal, screen, independence);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(continueButton);

        Label totalBill = new Label(String.format("""
           Bill so far:  $%,.2f
           """, purchasingOxen * 20.0f +
                purchasingFood * 0.10f +
                purchasingClothing * 10.0f +
                purchasingAmmunition * 0.10f +
                (purchasingWagonWheels + purchasingWagonAxles + purchasingWagonTongues) * 10.0f +
                purchasingMedicine * 5.0f));
        totalBill.setPosition(new TerminalPosition(2, 18));
        totalBill.setSize(new TerminalSize(30, 1));
        panel.addComponent(totalBill);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    private static void BuyClothing(Terminal terminal, Screen screen, boolean independence) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label label = new Label("""
            You'll need warm clothing in
            the mountains. I recommend
            taking at least 2 sets of
            clothes per person. Each
            set is $10.00
            
            How many sets of clothes do
            you want?
            """);
        label.setPosition(new TerminalPosition(2, 1));
        label.setSize(new TerminalSize(30, 7));
        panel.addComponent(label);

        TextBox input = new TextBox();
        input.setPosition(new TerminalPosition(2, 8));
        input.setSize(new TerminalSize(6, 1));
        panel.addComponent(input);

        Button continueButton = new Button("Continue");
        continueButton.setPosition(new TerminalPosition(2, 10));
        continueButton.setSize(new TerminalSize(12, 1));
        continueButton.addListener((_) -> {
            if (input.getText().isBlank()) {
                MessageDialog.showMessageDialog(textGui, "Error", "The amount you purchase cannot be blank!", MessageDialogButton.Retry);
                return;
            }

            try {
                Integer.parseInt(input.getText());
            } catch (NumberFormatException e) {
                MessageDialog.showMessageDialog(textGui, "Error", "The amount you purchase must be an integer!", MessageDialogButton.Retry);
                return;
            }

            purchasingClothing += Integer.parseInt(input.getText());
            window.close();

            try {
                GeneralStore.StoreMainMenu(terminal, screen, independence);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(continueButton);

        Label totalBill = new Label(String.format("""
           Bill so far:  $%,.2f
           """, purchasingOxen * 20.0f +
                purchasingFood * 0.10f +
                purchasingClothing * 10.0f +
                purchasingAmmunition * 0.10f +
                (purchasingWagonWheels + purchasingWagonAxles + purchasingWagonTongues) * 10.0f +
                purchasingMedicine * 5.0f));
        totalBill.setPosition(new TerminalPosition(2, 16));
        totalBill.setSize(new TerminalSize(30, 1));
        panel.addComponent(totalBill);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    private static void BuyAmmunition(Terminal terminal, Screen screen, boolean independence) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label label = new Label("""
            I sell ammunition in boxes
            of 20 bullets. Each box
            costs $2.00.
            
            How many boxes do you
            want?
            """);
        label.setPosition(new TerminalPosition(2, 1));
        label.setSize(new TerminalSize(30, 7));
        panel.addComponent(label);

        TextBox input = new TextBox();
        input.setPosition(new TerminalPosition(2, 8));
        input.setSize(new TerminalSize(6, 1));
        panel.addComponent(input);

        Button continueButton = new Button("Continue");
        continueButton.setPosition(new TerminalPosition(2, 10));
        continueButton.setSize(new TerminalSize(12, 1));
        continueButton.addListener((_) -> {
            if (input.getText().isBlank()) {
                MessageDialog.showMessageDialog(textGui, "Error", "The amount you purchase cannot be blank!", MessageDialogButton.Retry);
                return;
            }

            try {
                Integer.parseInt(input.getText());
            } catch (NumberFormatException e) {
                MessageDialog.showMessageDialog(textGui, "Error", "The amount you purchase must be an integer!", MessageDialogButton.Retry);
                return;
            }

            purchasingAmmunition += Integer.parseInt(input.getText()) * 20;
            window.close();

            try {
                GeneralStore.StoreMainMenu(terminal, screen, independence);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(continueButton);

        Label totalBill = new Label(String.format("""
           Bill so far:  $%,.2f
           """, purchasingOxen * 20.0f +
                purchasingFood * 0.10f +
                purchasingClothing * 10.0f +
                purchasingAmmunition * 0.10f +
                (purchasingWagonWheels + purchasingWagonAxles + purchasingWagonTongues) * 10.0f +
                purchasingMedicine * 5.0f));
        totalBill.setPosition(new TerminalPosition(2, 16));
        totalBill.setSize(new TerminalSize(30, 1));
        panel.addComponent(totalBill);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    private static void BuySpareParts(Terminal terminal, Screen screen, boolean independence) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label label = new Label("""
            It's a good idea to have a
            few spare parts for your
            wagon. Here are the prices:
            
               wagon wheel   - $10 each
               wagon axle    - $10 each
               wagon tongue  - $10 each
            """);
        label.setPosition(new TerminalPosition(2, 1));
        label.setSize(new TerminalSize(30, 7));
        panel.addComponent(label);

        Label input1Label = new Label("How many wagon wheels?");
        input1Label.setPosition(new TerminalPosition(2, 9));
        input1Label.setSize(new TerminalSize(30, 1));
        panel.addComponent(input1Label);

        TextBox input1 = new TextBox();
        input1.setPosition(new TerminalPosition(34, 9));
        input1.setSize(new TerminalSize(6, 1));
        panel.addComponent(input1);

        Label input2Label = new Label("How many wagon axles?");
        input2Label.setPosition(new TerminalPosition(2, 10));
        input2Label.setSize(new TerminalSize(30, 1));
        panel.addComponent(input2Label);

        TextBox input2 = new TextBox();
        input2.setPosition(new TerminalPosition(34, 10));
        input2.setSize(new TerminalSize(6, 1));
        panel.addComponent(input2);

        Label input3Label = new Label("How many wagon tongues?");
        input3Label.setPosition(new TerminalPosition(2, 11));
        input3Label.setSize(new TerminalSize(30, 1));
        panel.addComponent(input3Label);

        TextBox input3 = new TextBox();
        input3.setPosition(new TerminalPosition(34, 11));
        input3.setSize(new TerminalSize(6, 1));
        panel.addComponent(input3);

        Button continueButton = new Button("Continue");
        continueButton.setPosition(new TerminalPosition(2, 13));
        continueButton.setSize(new TerminalSize(12, 1));
        continueButton.addListener((_) -> {
            if (input1.getText().isBlank() || input2.getText().isBlank() || input3.getText().isBlank()) {
                MessageDialog.showMessageDialog(textGui, "Error", "The amount you purchase cannot be blank!", MessageDialogButton.Retry);
                return;
            }

            try {
                Integer.parseInt(input1.getText());
                Integer.parseInt(input2.getText());
                Integer.parseInt(input3.getText());
            } catch (NumberFormatException e) {
                MessageDialog.showMessageDialog(textGui, "Error", "The amount you purchase must be an integer!", MessageDialogButton.Retry);
                return;
            }

            purchasingWagonWheels += Integer.parseInt(input1.getText());
            purchasingWagonAxles += Integer.parseInt(input2.getText());
            purchasingWagonTongues += Integer.parseInt(input3.getText());
            window.close();

            try {
                GeneralStore.StoreMainMenu(terminal, screen, independence);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(continueButton);

        Label totalBill = new Label(String.format("""
           Bill so far:  $%,.2f
           """, purchasingOxen * 20.0f +
                purchasingFood * 0.10f +
                purchasingClothing * 10.0f +
                purchasingAmmunition * 0.10f +
                (purchasingWagonWheels + purchasingWagonAxles + purchasingWagonTongues) * 10.0f +
                purchasingMedicine * 5.0f));
        totalBill.setPosition(new TerminalPosition(2, 19));
        totalBill.setSize(new TerminalSize(30, 1));
        panel.addComponent(totalBill);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    private static void BuyMedicine(Terminal terminal, Screen screen, boolean independence) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label label = new Label("""
            It's probably a pretty
            good idea to have some
            medicine to spare during
            your journey. There won't
            be any help coming any time
            soon when you're out on the
            trail.
            
            I charge $5 per package of
            medicine.
            
            How many packages of medicine
            would you like?
            """);
        label.setPosition(new TerminalPosition(2, 1));
        label.setSize(new TerminalSize(30, 13));
        panel.addComponent(label);

        TextBox input = new TextBox();
        input.setPosition(new TerminalPosition(2, 14));
        input.setSize(new TerminalSize(6, 1));
        panel.addComponent(input);

        Button continueButton = new Button("Continue");
        continueButton.setPosition(new TerminalPosition(2, 16));
        continueButton.setSize(new TerminalSize(12, 1));
        continueButton.addListener((_) -> {
            if (input.getText().isBlank()) {
                MessageDialog.showMessageDialog(textGui, "Error", "The amount you purchase cannot be blank!", MessageDialogButton.Retry);
                return;
            }

            try {
                Integer.parseInt(input.getText());
            } catch (NumberFormatException e) {
                MessageDialog.showMessageDialog(textGui, "Error", "The amount you purchase must be an integer!", MessageDialogButton.Retry);
                return;
            }

            purchasingMedicine += Integer.parseInt(input.getText());
            window.close();

            try {
                GeneralStore.StoreMainMenu(terminal, screen, independence);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(continueButton);

        Label totalBill = new Label(String.format("""
           Bill so far:  $%,.2f
           """, purchasingOxen * 20.0f +
                purchasingFood * 0.10f +
                purchasingClothing * 10.0f +
                purchasingAmmunition * 0.10f +
                (purchasingWagonWheels + purchasingWagonAxles + purchasingWagonTongues) * 10.0f +
                purchasingMedicine * 5.0f));
        totalBill.setPosition(new TerminalPosition(2, 22));
        totalBill.setSize(new TerminalSize(30, 1));
        panel.addComponent(totalBill);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    private static void Leaving(Terminal terminal, Screen screen) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label label = new Label("""
            Well then, you're ready
            to start. Good luck!
            You have a long and
            difficult journey ahead
            of you.
            """);
        label.setPosition(new TerminalPosition(2, 1));
        label.setSize(new TerminalSize(30, 5));
        panel.addComponent(label);

        Button continueButton = new Button("Continue");
        continueButton.setPosition(new TerminalPosition(2, 8));
        continueButton.setSize(new TerminalSize(12, 1));
        continueButton.addListener((_) -> {
            window.close();
            try {
                Goodbye(terminal, screen);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(continueButton);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    private static void Goodbye(Terminal terminal, Screen screen) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        String month = switch (Main.GameState.departureMonth) {
            case 2 -> "March";
            case 3 -> "April";
            case 4 -> "May";
            case 5 -> "June";
            case 6 -> "July";
            default -> "";
        };

        String town = String.format("""
                                                                                   |>>>
                                           _                      _                |
                            ____________ .' '.    _____/----/-\\ .' './========\\   / \\
                           //// ////// /V_.-._\\  |.-.-.|===| _ |-----| u    u |  /___\\
                          // /// // ///==\\ u |.  || | ||===||||| |T| |   ||   | .| u |_ _ _ _ _ _
                         ///////-\\////====\\==|:::::::::::::::::::::::::::::::::::|u u| U U U U U
                         |----/\\u |--|++++|..|'''''''''''::::::::::::::''''''''''|+++|+-+-+-+-+-+
                         |u u|u | |u ||||||..|              '::::::::'           |===|>=== _ _ ==
                         |===|  |u|==|++++|==|              .::::::::.           | T |....| V |..
                         |u u|u | |u ||HH||         \\|/    .::::::::::.
                         |===|_.|u|_.|+HH+|_              .::::::::::::.              _
                                        __(_)___         .::::::::::::::.         ___(_)__
                        ---------------/  / \\  /|       .:::::;;;:::;;:::.       |\\  / \\  \\-------
                        ______________/_______/ |      .::::::;;:::::;;:::.      | \\_______\\________
                        |       |     [===  =] /|     .:::::;;;::::::;;;:::.     |\\ [==  = ]   |
                        |_______|_____[ = == ]/ |    .:::::;;;:::::::;;;::::.    | \\[ ===  ]___|____
                             |       |[  === ] /|   .:::::;;;::::::::;;;:::::.   |\\ [=  ===] |
                        _____|_______|[== = =]/ |  .:::::;;;::::::::::;;;:::::.  | \\[ ==  =]_|______
                         |       |    [ == = ] /| .::::::;;:::::::::::;;;::::::. |\\ [== == ]      |
                        _|_______|____[=  == ]/ |.::::::;;:::::::::::::;;;::::::.| \\[  === ]______|_
                           |       |  [ === =] /.::::::;;::::::::::::::;;;:::::::.\\ [===  =]   |
                        ___|_______|__[ == ==]/.::::::;;;:::::::::::::::;;;:::::::.\\[=  == ]___|_____
                        
                        ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                                                         Independence
                                                         %s 1, 1848
                        
                             Your game will save from now on every time you travel on the trail.
                              You may close the game and load it from your current position on
                               the trail at any time. Be aware that if you exit during random
                                               events that they will not save.
                        """, month);

        Label townArt = new Label(town);
        townArt.setPosition(new TerminalPosition(2, 1));
        townArt.setSize(new TerminalSize(80, 31));
        panel.addComponent(townArt);

        Button continueButton = new Button("Continue");
        continueButton.setPosition(new TerminalPosition(2, 35));
        continueButton.setSize(new TerminalSize(12, 1));
        continueButton.addListener((_) -> {
            window.close();
            try {
                Main.GameState.Save();
                Progress.Execute(terminal, screen);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        panel.addComponent(continueButton);

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }
}
