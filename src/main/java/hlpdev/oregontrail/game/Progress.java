package hlpdev.oregontrail.game;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import hlpdev.oregontrail.*;
import hlpdev.oregontrail.enums.Location;
import hlpdev.oregontrail.enums.Pace;
import hlpdev.oregontrail.enums.Weather;
import hlpdev.oregontrail.npcs.Trade;
import hlpdev.oregontrail.npcs.Trading;
import hlpdev.oregontrail.records.PartyMember;
import hlpdev.oregontrail.util.Math;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Progress {
    public static void Execute(Terminal terminal, Screen screen) {
        boolean atPointOfInterest = Location.isCloseToPointOfInterest(Main.GameState.totalDistanceTraveled);
        Location pointOfInterest = atPointOfInterest ? Location.getPointOfInterest(Main.GameState.totalDistanceTraveled) : null;
        Location closestLocation = Location.getClosestLocation(Main.GameState.totalDistanceTraveled);
        Weather currentWeather = Main.GameState.weatherCondition;

        List<PartyMember> partyMembers = Main.GameState.partyMembers.stream().filter(PartyMember::isAlive).toList();
        List<Integer> partyHealths = partyMembers.stream().map(PartyMember::health).toList();
        int averageHealth = Math.average(partyHealths.toArray(new Integer[0]));

        String healthVisual;
        if (averageHealth > 80) {
            healthVisual = "Good";
        } else if (averageHealth > 60) {
            healthVisual = "Minor injuries";
        } else if (averageHealth > 30) {
            healthVisual = "Major injuries";
        } else {
            healthVisual = "Very bad";
        }

        String rationsVisual;
        if (Main.GameState.food > 200) {
            rationsVisual = "Filling";
        } else if (Main.GameState.food > 100) {
            rationsVisual = "Preparing to ration food";
        } else if (Main.GameState.food > 40) {
            rationsVisual = "Rationing food between members";
        } else if (Main.GameState.food > 0) {
            rationsVisual = "Sparse";
        } else {
            rationsVisual = "Members are starving";
        }

        String staminaVisual;
        if (Main.GameState.stamina > 70) {
            staminaVisual = "High";
        } else if (Main.GameState.stamina > 30) {
            staminaVisual = "Normal";
        } else {
            staminaVisual = "Low";
        }

        String moraleVisual;
        if (Main.GameState.morale > 70) {
            moraleVisual = "Good";
        } else if (Main.GameState.morale > 40) {
            moraleVisual = "Ok";
        } else {
            moraleVisual = "Bad";
        }

        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label titleLabel = getTitleLabel(atPointOfInterest, pointOfInterest, closestLocation);
        titleLabel.setPosition(new TerminalPosition(2, 1));
        titleLabel.setSize(new TerminalSize(30, 2));
        titleLabel.addStyle(SGR.BOLD);
        panel.addComponent(titleLabel);

        Label statusLabel = new Label(String.format("""
                  Weather: %s
                  Health: %s
                  Stamina: %s
                  Pace: %s
                  Morale: %s
                  Rations: %s
                """, currentWeather.visualRepresentation, healthVisual, staminaVisual, Main.GameState.pace.visualRepresentation, moraleVisual, rationsVisual));
        statusLabel.setPosition(new TerminalPosition(2, 4));
        statusLabel.setSize(new TerminalSize(40, 6));
        panel.addComponent(statusLabel);

        Label youMay = new Label("You may:");
        youMay.setPosition(new TerminalPosition(2, 12));
        youMay.setSize(new TerminalSize(8, 1));
        panel.addComponent(youMay);

        Button continueButton = new Button("Continue on the trail");
        continueButton.setPosition(new TerminalPosition(2, 14));
        continueButton.setSize(new TerminalSize(38, 1));
        panel.addComponent(continueButton);
        continueButton.addListener((_) -> {
            window.close();
            Continue(terminal, screen);
        });

        Button suppliesButton = new Button("Check Supplies");
        suppliesButton.setPosition(new TerminalPosition(2, 15));
        suppliesButton.setSize(new TerminalSize(38, 1));
        panel.addComponent(suppliesButton);
        suppliesButton.addListener((_) -> {
            window.close();
            CheckSupplies(terminal, screen);
        });

        Button mapButton = new Button("Look at the map");
        mapButton.setPosition(new TerminalPosition(2, 16));
        mapButton.setSize(new TerminalSize(38, 1));
        panel.addComponent(mapButton);
        mapButton.addListener((_) -> {
            window.close();
            ShowMap(terminal, screen);
        });

        Button paceButton = new Button("Change pace");
        paceButton.setPosition(new TerminalPosition(2, 17));
        paceButton.setSize(new TerminalSize(38, 1));
        panel.addComponent(paceButton);
        paceButton.addListener((_) -> {
            window.close();
            ChangePace(terminal, screen);
        });

        Button restButton = new Button("Stop to rest");
        restButton.setPosition(new TerminalPosition(2, 18));
        restButton.setSize(new TerminalSize(38, 1));
        panel.addComponent(restButton);
        restButton.addListener((_) -> {
            window.close();
            StopToRest(terminal, screen);
        });

        Button tradeButton = new Button("Attempt to trade");
        tradeButton.setPosition(new TerminalPosition(2, 19));
        tradeButton.setSize(new TerminalSize(38, 1));
        panel.addComponent(tradeButton);
        tradeButton.addListener((_) -> {
            if (!atPointOfInterest) {
                MessageDialog.showMessageDialog(textGui, "Whoops", "You're currently in the middle of no where, so you can't find anyone to trade with.", MessageDialogButton.OK);
                return;
            }

            window.close();
            AttemptToTrade(terminal, screen);
        });

        Button talkButton = new Button("Talk to people");
        talkButton.setPosition(new TerminalPosition(2, 20));
        talkButton.setSize(new TerminalSize(38, 1));
        panel.addComponent(talkButton);
        talkButton.addListener((_) -> {
            window.close();
            TalkToPeople(terminal, screen);
        });

        Button buySuppliesButton = new Button("Buy supplies");
        buySuppliesButton.setPosition(new TerminalPosition(2, 21));
        buySuppliesButton.setSize(new TerminalSize(38, 1));
        panel.addComponent(buySuppliesButton);
        buySuppliesButton.addListener((_) -> {
            if (!atPointOfInterest) {
                MessageDialog.showMessageDialog(textGui, "Whoops", "You're currently in the middle of no where, so you can't find a shop to purchase supplies at.", MessageDialogButton.OK);
                return;
            }

            window.close();
            GeneralStore.StoreMainMenu(terminal, screen, false);
        });

        Button goHuntingButton = new Button("Go hunting");
        goHuntingButton.setPosition(new TerminalPosition(2, 22));
        goHuntingButton.setSize(new TerminalSize(38, 1));
        panel.addComponent(goHuntingButton);
        goHuntingButton.addListener((_) -> {
            if (Main.GameState.ammunition <= 0) {
                MessageDialog.showMessageDialog(textGui, "Whoops", "You don't have any ammunition to go hunting with.", MessageDialogButton.OK);
                return;
            }

            window.close();
            GoHunting(terminal, screen);
        });

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    private static Label getTitleLabel(boolean atPointOfInterest, Location pointOfInterest, Location closestLocation) {
        String title;

        String month = switch (Main.GameState.departureMonth + Main.GameState.currentMonth) {
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

        if (atPointOfInterest) {
            assert pointOfInterest != null;

            title = String.format("""
                    %s
                    %s %s, 1848
                    """, pointOfInterest.name, month, Main.GameState.currentDay);
        } else {
            title = String.format("""
                    %s
                    %s %s, 1848
                    """, closestLocation.relativeLocation, month, Main.GameState.currentDay);
        }

        return new Label(title);
    }

    private static void Continue(Terminal terminal, Screen screen) {
        try {
            Main.GameState.Save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);

        if (Main.GameState.stamina == 0) {
            MessageDialog.showMessageDialog(textGui, "", "Your group's stamina has reached zero, you must rest for a few days before continuing.", MessageDialogButton.OK);
            Execute(terminal, screen);
        }

        int daysToWalk = (int)new Random().nextDouble(5 * Main.GameState.pace.speedMultiplier) + 1;

        Main.GameState.stamina = java.lang.Math.max(0, Main.GameState.stamina - daysToWalk * 5);

        Main.GameState.totalDistanceTraveled += daysToWalk * new Random().nextInt(10);

        Main.GameState.currentDay += daysToWalk;
        Main.GameState.daysElapsed += daysToWalk;

        if (Main.GameState.currentDay >= 28) {
            Main.GameState.currentDay = 1;
            Main.GameState.currentMonth += 1;
        }

        if (Main.GameState.currentMonth > 10) {
            MessageDialog.showMessageDialog(textGui, "", "It's now December, and the temperature is way to cold to continue. Your journey is over.", MessageDialogButton.OK);
            System.exit(0);
        }

        int foodToRemove = new Random().nextInt((int)(50 * Main.GameState.pace.foodConsumptionMultiplier));
        int foodPerMember = foodToRemove / Main.GameState.partyMembers.size();

        if (foodToRemove <= Main.GameState.food) {
            Main.GameState.food -= foodToRemove;
            for (PartyMember member : Main.GameState.partyMembers) {
                Main.GameState.partyMembers.set(Main.GameState.partyMembers.indexOf(member), member.newFoodConsumed(foodPerMember));
            }
        } else {
            for (PartyMember member : Main.GameState.partyMembers) {
                Main.GameState.partyMembers.set(Main.GameState.partyMembers.indexOf(member), member.newHealth(java.lang.Math.max(0, member.health() - new Random().nextInt(60))));
            }
        }

        for (PartyMember member : Main.GameState.partyMembers) {
            if (member.hasDisease()) {
                Main.GameState.partyMembers.set(Main.GameState.partyMembers.indexOf(member), member.newHealth(java.lang.Math.max(0, member.health() - new Random().nextInt(60))));
            }
        }

        for (PartyMember member : Main.GameState.partyMembers) {
            if (member.health() <= 0 && new Random().nextBoolean()) {
                Main.GameState.partyMembers.set(Main.GameState.partyMembers.indexOf(member), member.newAlive(false));
                MessageDialog.showMessageDialog(textGui, "", String.format("%s has died. You can bury them next time you rest for a few days.", member.name()));
            }
        }

        boolean atPointOfInterest = Location.isCloseToPointOfInterest(Main.GameState.totalDistanceTraveled);
        Location pointOfInterest = atPointOfInterest ? Location.getPointOfInterest(Main.GameState.totalDistanceTraveled) : null;
        Location closestLocation = Location.getClosestLocation(Main.GameState.totalDistanceTraveled);

        if (atPointOfInterest) {
            assert pointOfInterest != null;
            MessageDialog.showMessageDialog(textGui, "", String.format("You walked for %d days and arrived at %s.", daysToWalk, pointOfInterest.name), MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGui, "", String.format("You walked for %d days and are in the %s.", daysToWalk, closestLocation.relativeLocation));
        }

        if (atPointOfInterest && pointOfInterest != Location.INDEPENDENCE && pointOfInterest != Location.OREGON_CITY) {
            MessageDialog.showMessageDialog(textGui, "Attention", "You have reached a point of interest! You may buy supplies and attempt to trade!", MessageDialogButton.Continue);
        }

        DoRandomEvent(textGui, atPointOfInterest);

        Execute(terminal, screen);
    }

    private static void DoRandomEvent(WindowBasedTextGUI textGui, boolean atPointOfInterest) {
        if (!atPointOfInterest) {
            if (!Main.GameState.hasSnakeAttack && new Random().nextInt(1, 10) > 7) {
                PartyMember member = Main.GameState.partyMembers.get(new Random().nextInt(Main.GameState.partyMembers.size()));

                Main.GameState.partyMembers.set(Main.GameState.partyMembers.indexOf(member), member.newHealth(java.lang.Math.max(0, member.health() - new Random().nextInt(90))));
                MessageDialog.showMessageDialog(textGui, "", String.format("%s got bitten by a snake and has suffered major injuries! Make sure to check on them soon!", member.name()));

                Main.GameState.hasSnakeAttack = true;
                return;
            }

            if (!Main.GameState.hasBearAttack && new Random().nextInt(1, 10) > 7) {
                PartyMember member = Main.GameState.partyMembers.get(new Random().nextInt(Main.GameState.partyMembers.size()));

                Main.GameState.partyMembers.set(Main.GameState.partyMembers.indexOf(member), member.newHealth(java.lang.Math.max(0, member.health() - new Random().nextInt(60))));
                MessageDialog.showMessageDialog(textGui, "", String.format("%s got attacked by a bear and has suffered major injuries! Make sure to check on them soon!", member.name()));

                Main.GameState.hasBearAttack = true;
                return;
            }

            // Random accident
            if (new Random().nextInt(1, 10) > 7) {
                String[] part = { "axle", "tongue", "wheel" };
                String selectedPart = part[new Random().nextInt(part.length)];

                MessageDialog.showMessageDialog(textGui, "", String.format("Your wagon's %s has been damaged.", selectedPart), MessageDialogButton.OK);

                switch (selectedPart) {
                    case "axle": {
                        if (Main.GameState.wagonAxles <= 0) {
                            MessageDialog.showMessageDialog(textGui, "", String.format("You don't have any spare %ss and have been stranded. Your game is over.", selectedPart));
                            System.exit(0);
                        }

                        MessageDialog.showMessageDialog(textGui, "", String.format("You have used 1 spare %s to fix your wagon.", selectedPart));
                        Main.GameState.wagonAxles -= 1;
                        break;
                    }
                    case "tongue": {
                        if (Main.GameState.wagonTongues <= 0) {
                            MessageDialog.showMessageDialog(textGui, "", String.format("You don't have any spare %ss and have been stranded. Your game is over.", selectedPart));
                            System.exit(0);
                        }

                        MessageDialog.showMessageDialog(textGui, "", String.format("You have used 1 spare %s to fix your wagon.", selectedPart));
                        Main.GameState.wagonTongues -= 1;
                        break;
                    }
                    case "wheel": {
                        if (Main.GameState.wagonWheels <= 0) {
                            MessageDialog.showMessageDialog(textGui, "", String.format("You don't have any spare %ss and have been stranded. Your journey is over.", selectedPart));
                            System.exit(0);
                        }

                        MessageDialog.showMessageDialog(textGui, "", String.format("You have used 1 spare %s to fix your wagon.", selectedPart));
                        Main.GameState.wagonWheels -= 1;
                        break;
                    }
                }
                return;
            }

            if (!Main.GameState.hasRandomDeath && new Random().nextInt(1, 10) > 9) {
                // Random death
                PartyMember member = Main.GameState.partyMembers.get(new Random().nextInt(Main.GameState.partyMembers.size()));

                Main.GameState.partyMembers.set(Main.GameState.partyMembers.indexOf(member), member.newAlive(false).newHealth(0));
                MessageDialog.showMessageDialog(textGui, "", String.format("%s had a heart attack and died. You can bury them next time you rest for a few days.", member.name()));

                Main.GameState.hasRandomDeath = true;
                return;
            }

            // Random sickness
            if (new Random().nextInt(1, 10) > 7) {
                PartyMember member = Main.GameState.partyMembers.get(new Random().nextInt(Main.GameState.partyMembers.size()));

                Main.GameState.partyMembers.set(Main.GameState.partyMembers.indexOf(member), member.newHealth(java.lang.Math.max(0, member.health() - new Random().nextInt(60))).newDisease(true));
                MessageDialog.showMessageDialog(textGui, "", String.format("%s has fallen ill. Give them medication to reduce the risk of death.", member.name()));
                return;
            }

            if (!Main.GameState.hasBanditAttack && new Random().nextInt(1, 10) > 7) {
                MessageDialogButton initialResponse = MessageDialog.showMessageDialog(textGui, "", "A group of bandits have approached your wagon and are attempting to steal your supplies. Do you want to fight them?", MessageDialogButton.Yes, MessageDialogButton.No);

                if (initialResponse == MessageDialogButton.No) {
                    if (new Random().nextBoolean()) {
                        double moneyToSteal = new Random().nextDouble(Main.GameState.totalMoney);

                        MessageDialog.showMessageDialog(textGui, "", String.format("The bandits stole %,.2f of your cash and ran away.", moneyToSteal), MessageDialogButton.OK);
                        Main.GameState.totalMoney -= moneyToSteal;
                    } else {
                        int foodToSteal = new Random().nextInt(Main.GameState.food);

                        MessageDialog.showMessageDialog(textGui, "", String.format("The bandits stole %d lbs of your food and ran away.", foodToSteal), MessageDialogButton.OK);
                        Main.GameState.food -= foodToSteal;
                    }
                } else {
                    if (new Random().nextBoolean()) {
                        MessageDialog.showMessageDialog(textGui, "", "You shot and hit one of the bandits causing a critical injury.", MessageDialogButton.OK);
                        Main.GameState.ammunition -= 1;

                        if (new Random().nextBoolean()) {
                            MessageDialog.showMessageDialog(textGui, "", "They pulled out a rifle and fired a shot back at you and missed.", MessageDialogButton.OK);

                            double money = new Random().nextDouble(1, 500);
                            MessageDialog.showMessageDialog(textGui, "", String.format("The bandits ran away and dropped $%,.2f in cash on their way out.", money), MessageDialogButton.OK);
                            Main.GameState.totalMoney += money;
                        } else {
                            MessageDialog.showMessageDialog(textGui, "", "They pulled out a rifle and fired a shot back at you.", MessageDialogButton.OK);
                            if (new Random().nextBoolean()) {
                                MessageDialog.showMessageDialog(textGui, "", "The shot hit you in the head. Your journey is over.", MessageDialogButton.OK);
                                System.exit(0);
                            } else {
                                PartyMember member = Main.GameState.partyMembers.get(new Random().nextInt(Main.GameState.partyMembers.size()));

                                MessageDialog.showMessageDialog(textGui, "", String.format("The shot hit %s causing a critical injury.", member.name()), MessageDialogButton.OK);

                                Main.GameState.partyMembers.set(Main.GameState.partyMembers.indexOf(member), member.newHealth(java.lang.Math.max(0, member.health() - new Random().nextInt(65))));
                            }
                        }
                    } else {
                        PartyMember member = Main.GameState.partyMembers.get(new Random().nextInt(Main.GameState.partyMembers.size()));

                        MessageDialog.showMessageDialog(textGui, "", "You shot and missed while aiming at one of the bandits.", MessageDialogButton.OK);
                        MessageDialog.showMessageDialog(textGui, "", String.format("They pulled a gun out and shot %s causing a critical injury and ran away.", member.name()), MessageDialogButton.OK);

                        Main.GameState.partyMembers.set(Main.GameState.partyMembers.indexOf(member), member.newHealth(new Random().nextInt(65)));
                    }
                }

                Main.GameState.hasBanditAttack = true;
                return;
            }
        }
    }

    private static void CheckSupplies(Terminal terminal, Screen screen) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label inventory = new Label(String.format("""
                Inventory:
                
                  Money: $%,.2f
                  Food: %d lb%s
                  Ammunition: %d round%s
                  Clothing: %d set%s
                  Wagon Wheels: %d
                  Wagon Axles: %d
                  Wagon Tongues: %d
                  Medicine: %d pack%s
                  Oxen: %d
                """,
                Main.GameState.totalMoney,
                Main.GameState.food, Main.GameState.food == 1 ? "" : "s",
                Main.GameState.ammunition, Main.GameState.ammunition == 1 ? "" : "s",
                Main.GameState.clothing, Main.GameState.clothing == 1 ? "" : "s",
                Main.GameState.wagonWheels,
                Main.GameState.wagonAxles,
                Main.GameState.wagonTongues,
                Main.GameState.medicine, Main.GameState.medicine == 1 ? "" : "s",
                Main.GameState.oxen
        ));
        inventory.setPosition(new TerminalPosition(2, 1));
        inventory.setSize(new TerminalSize(30, 11));
        panel.addComponent(inventory);

        Button goBackButton = new Button("Go back");
        goBackButton.setPosition(new TerminalPosition(2, 13));
        goBackButton.setSize(new TerminalSize(11, 1));
        panel.addComponent(goBackButton);
        goBackButton.addListener((_) -> {
            window.close();
            Execute(terminal, screen);
        });

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    private static void ShowMap(Terminal terminal, Screen screen) {
        boolean atPointOfInterest = Location.isCloseToPointOfInterest(Main.GameState.totalDistanceTraveled);
        Location pointOfInterest = atPointOfInterest ? Location.getPointOfInterest(Main.GameState.totalDistanceTraveled) : null;
        Location closestLocation = Location.getClosestLocation(Main.GameState.totalDistanceTraveled);

        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        StringBuilder map = new StringBuilder();
        if (atPointOfInterest) {
            for (Location location : Location.values()) {
                if (location == pointOfInterest) {
                    map.append(location.name).append(" - YOU ARE HERE\n");
                } else {
                    map.append(location.name).append('\n');
                }
            }
        } else {
            for (Location location : Location.values()) {
                if (location == closestLocation) {
                    map.append(location.name).append("\nYOU ARE HERE\n");
                } else {
                    map.append(location.name).append('\n');
                }
            }
        }

        Label mapLabel = new Label(map.toString());
        mapLabel.setPosition(new TerminalPosition(2, 2));
        mapLabel.setSize(new TerminalSize(70, 13));
        panel.addComponent(mapLabel);

        Button goBackButton = new Button("Go back");
        goBackButton.setPosition(new TerminalPosition(2, 17));
        goBackButton.setSize(new TerminalSize(11, 1));
        panel.addComponent(goBackButton);
        goBackButton.addListener((_) -> {
            window.close();
            Execute(terminal, screen);
        });

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    private static void ChangePace(Terminal terminal, Screen screen) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label label = new Label("""
                Your travel pace can significantly
                impact your journey to Oregon.
                It is recommended not to go too fast
                as it can put more stress on you and
                your party members.
                
                Choose a pace:""");
        label.setPosition(new TerminalPosition(2, 2));
        label.setSize(new TerminalSize(40, 7));
        panel.addComponent(label);

        ComboBox<String> paceInput = new ComboBox<>();
        paceInput.setPosition(new TerminalPosition(2, 10));
        paceInput.setSize(new TerminalSize(16, 1));
        paceInput.addItem("Steady");
        paceInput.addItem("Strenuous");
        paceInput.addItem("Grueling");
        panel.addComponent(paceInput);

        Button goBackButton = new Button("Go back");
        goBackButton.setPosition(new TerminalPosition(2, 12));
        goBackButton.setSize(new TerminalSize(11, 1));
        panel.addComponent(goBackButton);
        goBackButton.addListener((_) -> {
            Main.GameState.pace = Pace.fromString(paceInput.getSelectedItem());
            window.close();
            Execute(terminal, screen);
        });

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    private static void StopToRest(Terminal terminal, Screen screen) {
        boolean atPointOfInterest = Location.isCloseToPointOfInterest(Main.GameState.totalDistanceTraveled);
        Location pointOfInterest = atPointOfInterest ? Location.getPointOfInterest(Main.GameState.totalDistanceTraveled) : null;
        Location closestLocation = Location.getClosestLocation(Main.GameState.totalDistanceTraveled);

        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);

        int daysToRest = new Random().nextInt(5) + 1;

        Main.GameState.stamina = 100;

        Main.GameState.currentDay += daysToRest;
        Main.GameState.daysElapsed += daysToRest;

        if (Main.GameState.currentDay >= 28) {
            Main.GameState.currentDay = 1;
            Main.GameState.currentMonth += 1;
        }

        int foodToRemove = new Random().nextInt(50);
        int foodPerMember = foodToRemove / Main.GameState.partyMembers.size();

        if (foodToRemove <= Main.GameState.food) {
            Main.GameState.food -= foodToRemove;
            for (PartyMember member : Main.GameState.partyMembers) {
                Main.GameState.partyMembers.set(Main.GameState.partyMembers.indexOf(member), member.newFoodConsumed(foodPerMember));
            }
        } else {
            for (PartyMember member : Main.GameState.partyMembers) {
                Main.GameState.partyMembers.set(Main.GameState.partyMembers.indexOf(member), member.newHealth(java.lang.Math.max(0, member.health() - new Random().nextInt(60))));
            }
        }

        for (PartyMember member : Main.GameState.partyMembers) {
            if (member.health() <= 0) {
                Main.GameState.partyMembers.set(Main.GameState.partyMembers.indexOf(member), member.newAlive(false));
                MessageDialog.showMessageDialog(textGui, "", String.format("%s has died. You can bury them next time you rest for a few days.", member.name()));
            }
        }

        String deathLocation;
        if (atPointOfInterest) {
            assert pointOfInterest != null;
            deathLocation = "at " + pointOfInterest.name;
        } else {
            deathLocation = "in the " + closestLocation.relativeLocation;
        }
        for (PartyMember member : Main.GameState.partyMembers) {
            if (!member.isAlive() && !member.hasBeenBuried()) {
                Main.GameState.partyMembers.set(Main.GameState.partyMembers.indexOf(member), member.newBuried(true));
                MessageDialog.showMessageDialog(textGui, "", String.format("%s has been buried %s.", member.name(), deathLocation));
            }
        }

        DoRandomEvent(textGui, atPointOfInterest);

        MessageDialog.showMessageDialog(textGui, "", String.format("You rested for %d days", daysToRest), MessageDialogButton.OK);
        Execute(terminal, screen);
    }

    private static void AttemptToTrade(Terminal terminal, Screen screen) {
        Location pointOfInterest = Objects.requireNonNull(Location.getPointOfInterest(Main.GameState.totalDistanceTraveled));

        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        if (pointOfInterest.tradesAvailable <= 0) {
            MessageDialog.showMessageDialog(textGui, "", "You can't find anyone to trade with.", MessageDialogButton.OK);
            window.close();
            Execute(terminal, screen);
        }

        Trade trade = Trading.getRandomTrade();
        Objects.requireNonNull(trade).Execute(textGui, window, panel);
        pointOfInterest.tradesAvailable -= 1;

        Execute(terminal, screen);
    }

    private static void TalkToPeople(Terminal terminal, Screen screen) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label label = new Label("""
                Which person in your group
                would you like to speak to?""");
        label.setPosition(new TerminalPosition(2, 2));
        label.setSize(new TerminalSize(40, 2));
        panel.addComponent(label);

        RadioBoxList<String> people = new RadioBoxList<>();
        people.setPosition(new TerminalPosition(2, 5));
        for (PartyMember member : Main.GameState.partyMembers) {
            if (member.isAlive()) {
                people.addItem(member.name());
            }
        }
        people.setSize(new TerminalSize(40, people.getItemCount()));
        panel.addComponent(people);

        Button continueButton = new Button("Continue");
        continueButton.setPosition(new TerminalPosition(2, 7 + people.getItemCount()));
        continueButton.setSize(new TerminalSize(12, 1));
        panel.addComponent(continueButton);
        continueButton.addListener((_) -> {
            if (people.getCheckedItemIndex() == -1) {
                MessageDialog.showMessageDialog(textGui, "Error", "You need to choose a party member to speak to!", MessageDialogButton.Retry);
                return;
            }

            window.close();
            TalkToPerson(terminal, screen, people.getCheckedItem());
        });

        Button goBack = new Button("Go back");
        goBack.setPosition(new TerminalPosition(2, 8 + people.getItemCount()));
        goBack.setSize(new TerminalSize(11, 1));
        panel.addComponent(goBack);
        goBack.addListener((_) -> {
            window.close();
            Execute(terminal, screen);
        });

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    private static void TalkToPerson(Terminal terminal, Screen screen, String name) {
        PartyMember member = Main.GameState.partyMembers.stream().filter(partyMember -> partyMember.name().equals(name)).findFirst().get();

        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(62, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        String healthVisual;
        if (member.health() > 80) {
            healthVisual = "good.";
        } else if (member.health() > 60) {
            healthVisual = "not ok, and they have\nminor injuries.";
        } else if (member.health() > 30) {
            healthVisual = "not ok, and they have\nmajor injuries.";
        } else {
            healthVisual = "critical and they\nare dying.";
        }

        Label label = new Label(String.format("""
                You speak to %s and they say that
                their health is %s
                """, member.name(), healthVisual));
        label.setPosition(new TerminalPosition(2, 2));
        label.setSize(new TerminalSize(45, 3));
        panel.addComponent(label);

        Button giveMedicine = new Button("Give Medicine");
        giveMedicine.setPosition(new TerminalPosition(2, 7));
        giveMedicine.setSize(new TerminalSize(17, 1));
        panel.addComponent(giveMedicine);
        giveMedicine.addListener((_) -> {
            if (member.health() >= 100 && !member.hasDisease()) {
                MessageDialog.showMessageDialog(textGui, "", String.format("%s is in perfect condition!", member.name()), MessageDialogButton.OK);
                return;
            }

            if (Main.GameState.medicine == 0) {
                MessageDialog.showMessageDialog(textGui, "", "You have no medicine left in your wagon!", MessageDialogButton.OK);
                return;
            }

            Main.GameState.partyMembers.set(Main.GameState.partyMembers.indexOf(member), member.newHealth(java.lang.Math.min(member.health() + 45, 100)).newDisease(false));

            Main.GameState.medicine -= 1;

            MessageDialog.showMessageDialog(textGui, "", String.format("You gave %s medicine...", member.name()), MessageDialogButton.OK);
            window.close();
            TalkToPerson(terminal, screen, member.name());
        });

        Button goBack = new Button("Go back");
        goBack.setPosition(new TerminalPosition(2, 8));
        goBack.setSize(new TerminalSize(11, 1));
        panel.addComponent(goBack);
        goBack.addListener((_) -> {
            window.close();
            Execute(terminal, screen);
        });

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }

    private static void GoHunting(Terminal terminal, Screen screen) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);

        int ammoUsed = new Random().nextInt(java.lang.Math.min(90, Main.GameState.ammunition - 1) + 1);
        Main.GameState.ammunition -= ammoUsed;

        int animalsKilled = (int) java.lang.Math.ceil(new Random().nextDouble(ammoUsed / 8.0));
        int foodAdded = animalsKilled * new Random().nextInt(5, 50);

        Main.GameState.food += foodAdded;

        MessageDialog.showMessageDialog(textGui, "", String.format("You killed %d animals and used %d rounds of ammunition. %d lbs of food has been added to your supplies.", animalsKilled, ammoUsed, foodAdded), MessageDialogButton.OK);

        Main.GameState.animalsKilled += animalsKilled;

        Execute(terminal, screen);
    }
}
