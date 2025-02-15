package hlpdev.oregontrail.util;

import hlpdev.oregontrail.Main;

public abstract class Utilities {
    /**
     * Relaunches the game
     * @throws Exception
     */
    public static void Relaunch() throws Exception {
        Relaunch(new String[]{});
    }

    /**
     * Relaunches the game with command line arguments
     * @param args The command line arguments to pass to the new process
     * @throws Exception
     */
    public static void Relaunch(String[] args) throws Exception {
        Main.terminal.close();
        Main.main(args);
    }
}
