package hlpdev.oregontrail.npcs;

import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import hlpdev.oregontrail.util.Pair;

import java.util.ArrayList;

/**
 * An interface used to store random traders that they player can encounter
 */
public interface Trade {
    ArrayList<Pair<Integer, Integer>> PriceVariants = new ArrayList<>();

    void Execute(WindowBasedTextGUI textGui, Window window, Panel panel);
}
