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


@SuppressWarnings("unused")
public final class WagonTongue4Money  implements Trade {
    public WagonTongue4Money() {
        Collections.addAll(PriceVariants,
                new Pair<>(1, 5),
                new Pair<>(1, 8),
                new Pair<>(1, 7),
                new Pair<>(1, 15),
                new Pair<>(1, 13),
                new Pair<>(2, 28),
                new Pair<>(2, 15),
                new Pair<>(2, 10),
                new Pair<>(2, 18),
                new Pair<>(3, 35),
                new Pair<>(3, 25),
                new Pair<>(3, 15),
                new Pair<>(3, 28),
                new Pair<>(3, 45)
        );
    }

    @Override
    public void Execute(WindowBasedTextGUI textGui, Window window, Panel panel) {
        String traderName = Names.getRandomName();

        Pair<Integer, Integer> priceVariant = PriceVariants.get(new Random().nextInt(PriceVariants.size()));

        Label traderInfo = new Label(String.format("""
                %s is interested
                in trading with you!
                
                They want %d wagon tongues,
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
            if (Main.GameState.wagonTongues < priceVariant.first()) {
                MessageDialog.showMessageDialog(textGui, "Whoops", String.format("You don't have at least %d wagon tongues!", priceVariant.first()), MessageDialogButton.OK);
                return;
            }

            Main.GameState.wagonTongues -= priceVariant.first();
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
