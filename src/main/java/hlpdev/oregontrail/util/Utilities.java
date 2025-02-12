package hlpdev.oregontrail.util;

import hlpdev.oregontrail.Main;

public abstract class Utilities {
    public static void Relaunch() throws Exception {
        Relaunch(new String[]{});
    }

    public static void Relaunch(String[] args) throws Exception {
        Main.terminal.close();
        Main.main(args);
    }
}
