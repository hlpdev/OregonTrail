package hlpdev.oregontrail.npcs.trades;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import hlpdev.oregontrail.Main;
import hlpdev.oregontrail.npcs.Names;
import hlpdev.oregontrail.npcs.Trade;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class WagonWheel4Clothing implements Trade {
    @Override
    public @NotNull Boolean Execute(WindowBasedTextGUI textGui, Window window, Panel panel) {
        String traderName = Names.getRandomName();

        Label traderInfo = new Label(String.format("""
                %s is interested
                in trading with you!
                
                They want 1 spare wagon
                wheel, in return, they
                will give you 5 sets of
                clothing.
                
                Do you accept this offer?
                """, traderName));
        traderInfo.setPosition(new TerminalPosition(2, 1));
        traderInfo.setSize(new TerminalSize(50, 9));
        panel.addComponent(traderInfo);

        AtomicBoolean result = new AtomicBoolean(false);

        Button accept = new Button("Accept");
        accept.setPosition(new TerminalPosition(2, 11));
        accept.setSize(new TerminalSize(10, 1));
        panel.addComponent(accept);
        accept.addListener((_) -> {
            if (Main.GameState.wagonWheels < 1) {
                MessageDialog.showMessageDialog(textGui, "Whoops", "You don't have at least 1 wagon wheel!", MessageDialogButton.OK);
                return;
            }

            Main.GameState.wagonWheels -= 1;
            Main.GameState.clothing += 5;

            result.set(true);
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
        return result.get();
    }
}
