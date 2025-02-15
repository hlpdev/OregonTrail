package hlpdev.oregontrail.npcs.trades;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import hlpdev.oregontrail.Main;
import hlpdev.oregontrail.npcs.Names;
import hlpdev.oregontrail.npcs.Trade;
import hlpdev.oregontrail.util.Pair;

import java.util.Collections;
import java.util.Random;

/**
 * Trades clothing for food
 */
@SuppressWarnings("unused")
public final class Clothing4Food  implements Trade {
    public Clothing4Food() {
        Collections.addAll(PriceVariants,
                new Pair<>(1, 100),
                new Pair<>(1, 50),
                new Pair<>(1, 75),
                new Pair<>(1, 150),
                new Pair<>(1, 200),
                new Pair<>(2, 150),
                new Pair<>(2, 200),
                new Pair<>(2, 250),
                new Pair<>(2, 225),
                new Pair<>(3, 300),
                new Pair<>(3, 250),
                new Pair<>(3, 200),
                new Pair<>(3, 350),
                new Pair<>(3, 400)
        );
    }

    @Override
    public void Execute(WindowBasedTextGUI textGui, Window window, Panel panel) {
        String traderName = Names.getRandomName();

        Pair<Integer, Integer> priceVariant = PriceVariants.get(new Random().nextInt(PriceVariants.size()));

        Label traderInfo = new Label(String.format("""
                %s is interested
                in trading with you!
                
                They want %d sets of,
                clothing, in return, they
                will give you %d lbs of food.
                
                Do you accept this offer?
                """, traderName, priceVariant.first(), priceVariant.second()));
        traderInfo.setPosition(new TerminalPosition(2, 1));
        traderInfo.setSize(new TerminalSize(50, 8));
        panel.addComponent(traderInfo);

        Button accept = new Button("Accept");
        accept.setPosition(new TerminalPosition(2, 10));
        accept.setSize(new TerminalSize(10, 1));
        panel.addComponent(accept);
        accept.addListener((_) -> {
            if (Main.GameState.clothing < priceVariant.first()) {
                MessageDialog.showMessageDialog(textGui, "Whoops", String.format("You don't have at least %d sets of clothing!", priceVariant.first()), MessageDialogButton.OK);
                return;
            }

            Main.GameState.clothing -= priceVariant.first();
            Main.GameState.food += priceVariant.second();

            window.close();
        });

        Button deny = new Button("Deny");
        deny.setPosition(new TerminalPosition(2, 11));
        deny.setSize(new TerminalSize(8, 1));
        panel.addComponent(deny);
        deny.addListener((_) -> {
            window.close();
        });

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }
}
