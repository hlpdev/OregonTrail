package hlpdev.oregontrail.menus;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import hlpdev.oregontrail.GameState;
import hlpdev.oregontrail.Main;
import hlpdev.oregontrail.game.Progress;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class LoadMenu {
    public static void Execute(Terminal terminal, Screen screen) {
        final WindowBasedTextGUI textGui = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow("Main Menu");
        window.setFixedSize(new TerminalSize(76, 28));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new AbsoluteLayout());

        Label title = new Label("Load Save");
        title.setPosition(new TerminalPosition(2, 1));
        title.setSize(new TerminalSize(9, 1));
        panel.addComponent(title);

        RadioBoxList<String> saveList = new RadioBoxList<>();
        saveList.setPosition(new TerminalPosition(2, 3));
        saveList.setSize(new TerminalSize(15, 10));

        Path directory = Path.of(System.getProperty("user.home"), "/hlpdev/oregontrail/");
        File[] files = directory.toFile().listFiles((dir, name) -> name.endsWith("-save.json"));
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                String saveName = fileName.substring(0, fileName.length() - "-save.json".length());
                saveList.addItem(saveName);
            }
        }
        panel.addComponent(saveList);

        Button loadSaveButton = new Button("Load Save");
        loadSaveButton.setPosition(new TerminalPosition(2, 15));
        loadSaveButton.setSize(new TerminalSize(13, 1));
        panel.addComponent(loadSaveButton);
        loadSaveButton.addListener((_) -> {
            if (saveList.getCheckedItemIndex() == -1) {
                MessageDialog.showMessageDialog(textGui, "Error", "You must select a save!", MessageDialogButton.OK);
                return;
            }

            try {
                Main.GameState = GameState.Load(saveList.getCheckedItem());
                window.close();
                Progress.Execute(terminal, screen);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Button goBackButton = new Button("Go Back");
        goBackButton.setPosition(new TerminalPosition(16, 15));
        goBackButton.setSize(new TerminalSize(11, 1));
        panel.addComponent(goBackButton);
        goBackButton.addListener((_) -> {
            window.close();
            try {
                MainMenu.Execute(terminal, screen);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        window.setComponent(panel);
        textGui.addWindowAndWait(window);
    }
}
