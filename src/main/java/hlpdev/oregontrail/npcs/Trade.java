package hlpdev.oregontrail.npcs;

import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import org.jetbrains.annotations.NotNull;

public interface Trade {
    @NotNull Boolean Execute(WindowBasedTextGUI textGui, Window window, Panel panel);
}
