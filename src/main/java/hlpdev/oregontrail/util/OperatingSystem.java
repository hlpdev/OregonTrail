package hlpdev.oregontrail.util;

public class OperatingSystem {
    private static final String OS = System.getProperty("os.name", "unknown").toLowerCase();

    public static boolean isWindows() {
        return OS.contains("win");
    }

    public static boolean isMac() {
        return OS.contains("mac");
    }

    public static boolean isUnix() {
        return OS.contains("nix") || OS.contains("nux") || OS.contains("aix");
    }
}
