package hlpdev.oregontrail.npcs;

import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import hlpdev.oregontrail.util.Pair;

import java.util.ArrayList;

public interface Trade {
    ArrayList<Pair<Integer, Integer>> PriceVariants = new ArrayList<>();

    void Execute(WindowBasedTextGUI textGui, Window window, Panel panel);
}
