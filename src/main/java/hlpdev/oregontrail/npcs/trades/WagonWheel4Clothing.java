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
 * Trades wagon wheels for clothing
 */
@SuppressWarnings("unused")
public final class WagonWheel4Clothing implements Trade {
    public WagonWheel4Clothing() {
        Collections.addAll(PriceVariants,
                new Pair<>(1, 5),
                new Pair<>(2, 9),
                new Pair<>(1, 4),
                new Pair<>(2, 10),
                new Pair<>(3, 15),
                new Pair<>(5, 10),
                new Pair<>(3, 9),
                new Pair<>(2, 5),
                new Pair<>(1, 6)
        );
    }

    @Override
    public void Execute(WindowBasedTextGUI textGui, Window window, Panel panel) {
        String traderName = Names.getRandomName();

        Pair<Integer, Integer> priceVariant = PriceVariants.get(new Random().nextInt(PriceVariants.size()));

        Label traderInfo = new Label(String.format("""
                %s is interested
                in trading with you!
                
                They want %d spare wagon
                wheels, in return, they
                will give you %d sets of
                clothing.
                
                Do you accept this offer?
                """, traderName, priceVariant.first(), priceVariant.second()));
        traderInfo.setPosition(new TerminalPosition(2, 1));
        traderInfo.setSize(new TerminalSize(50, 9));
        panel.addComponent(traderInfo);

        Button accept = new Button("Accept");
        accept.setPosition(new TerminalPosition(2, 11));
        accept.setSize(new TerminalSize(10, 1));
        panel.addComponent(accept);
        accept.addListener((_) -> {
            if (Main.GameState.wagonWheels < priceVariant.first()) {
                MessageDialog.showMessageDialog(textGui, "Whoops", String.format("You don't have at least %d wagon wheels!", priceVariant.first()), MessageDialogButton.OK);
                return;
            }

            Main.GameState.wagonWheels -= priceVariant.first();
            Main.GameState.clothing += priceVariant.second();

            window.close();
        });

        Button deny = new Button("Deny");
        deny.setPosition(new TerminalPosition(2, 12));
        deny.setSize(new TerminalSize(8, 1));
        panel.addComponent(deny);
        deny.addListener((_) -> {
            window.close();
        });

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }
}
