package hlpdev.oregontrail.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The Settings class stores any configurable settings for the game
 */
public class Settings implements Serializable {
    public static Settings Settings = null;

    /**
     * Loads the player's settings from a file, creates a new one if one doesn't already exist
     * Uses JSON (Google's GSON) deserialization
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void Load() throws IOException, ClassNotFoundException {
        Path directory = Path.of(System.getProperty("user.home"), "/hlpdev/oregontrail/");
        Path path = Path.of(directory.toString(), "settings.bin");

        if (Files.notExists(path)) {
            Settings = new Settings();
            Settings.FontSize = 10;

            Save();
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path.toString()))) {
            Settings = (Settings)inputStream.readObject();
        }
    }

    /**
     * Saves the Settings to a file in the user's home directory using JSON (Google's GSON) serialization.
     * @throws IOException
     */
    public static void Save() throws IOException {
        Path directory = Path.of(System.getProperty("user.home"), "/hlpdev/oregontrail/");
        Path path = Path.of(directory.toString(), "settings.bin");

        if (Files.notExists(directory)) {
            Files.createDirectories(directory);
        }

        if (Files.notExists(path)) {
            Files.createFile(path);
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path.toString()))) {
            outputStream.writeObject(Settings);
        }
    }

    public Integer FontSize;
}
