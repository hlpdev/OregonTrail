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
 * Trades oxen for money
 */
@SuppressWarnings("unused")
public final class Oxen4Money  implements Trade {
    public Oxen4Money() {
        Collections.addAll(PriceVariants,
                new Pair<>(1, 30),
                new Pair<>(2, 25),
                new Pair<>(1, 22),
                new Pair<>(1, 15),
                new Pair<>(1, 24),
                new Pair<>(2, 28),
                new Pair<>(2, 45),
                new Pair<>(2, 60),
                new Pair<>(2, 35),
                new Pair<>(3, 55),
                new Pair<>(3, 65),
                new Pair<>(3, 40),
                new Pair<>(3, 70),
                new Pair<>(3, 80)
        );
    }

    @Override
    public void Execute(WindowBasedTextGUI textGui, Window window, Panel panel) {
        String traderName = Names.getRandomName();

        Pair<Integer, Integer> priceVariant = PriceVariants.get(new Random().nextInt(PriceVariants.size()));

        Label traderInfo = new Label(String.format("""
                %s is interested
                in trading with you!
                
                They want %d oxen,
                in return, they
                will give you $%,.2f.
                
                Do you accept this offer?
                """, traderName, priceVariant.first(), (double)priceVariant.second()));
        traderInfo.setPosition(new TerminalPosition(2, 1));
        traderInfo.setSize(new TerminalSize(50, 8));
        panel.addComponent(traderInfo);

        Button accept = new Button("Accept");
        accept.setPosition(new TerminalPosition(2, 10));
        accept.setSize(new TerminalSize(10, 1));
        panel.addComponent(accept);
        accept.addListener((_) -> {
            if (Main.GameState.oxen < priceVariant.first()) {
                MessageDialog.showMessageDialog(textGui, "Whoops", String.format("You don't have at least %d oxen!", priceVariant.first()), MessageDialogButton.OK);
                return;
            }

            Main.GameState.oxen -= priceVariant.first();
            Main.GameState.totalMoney += priceVariant.second();

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
