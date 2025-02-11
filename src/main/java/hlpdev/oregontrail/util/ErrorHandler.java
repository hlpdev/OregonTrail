package hlpdev.oregontrail.util;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;

public class ErrorHandler {
    public static void ShowError(Terminal terminal, Screen screen, Exception e) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow();
        window.setFixedSize(new TerminalSize(76, 28));

        StringBuilder stackTrace = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            stackTrace.append(element.toString()).append("\n");
        }

        String error = String.format("""
               
               %s
               
               %s
               
               """, e.getMessage(), stackTrace);

        MessageDialog.showMessageDialog(textGui, "A Fatal Error Occurred", error, MessageDialogButton.Abort);
        System.exit(e.hashCode());

        textGui.addWindowAndWait(window);
    }
}
