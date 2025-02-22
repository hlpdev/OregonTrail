package hlpdev.oregontrail;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hlpdev.oregontrail.enums.Location;
import hlpdev.oregontrail.enums.Pace;
import hlpdev.oregontrail.enums.Profession;
import hlpdev.oregontrail.enums.Weather;
import hlpdev.oregontrail.records.PartyMember;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * The GameState class stores the general save information
 * for each journey the player starts. This class is serialized
 * into JSON and stored in the player's application data and
 * loaded when the player loads the save via the main menu.
 */
public class GameState implements Serializable {

    /**
     * <h2>General Game Information</h2>
     */
    public int currentDay;
    public int currentMonth;
    public int departureMonth;
    public int totalDistanceTraveled;
    public double totalMoney;
    public Profession profession;
    public PartyMember playerMember;

    /**
     * <h2>Party Members</h2>
     */
    public List<PartyMember> partyMembers;

    /**
     * <h2>Supplies Information</h2>
     */
    public int food;
    public int ammunition;
    public int clothing;
    public int wagonWheels;
    public int wagonAxles;
    public int wagonTongues;
    public int medicine;
    public int oxen;

    /**
     * <h2>Travel Information</h2>
     */
    public Location currentLocation;
    public Weather weatherCondition;

    /**
     * <h2>Health & Morale Information</h2>
     */
    public Pace pace;
    public int stamina;

    /**
     * <h2>Special Encounters</h2>
     */
    public boolean hasBanditAttack;
    public boolean hasBearAttack;
    public boolean hasSnakeAttack;
    public boolean hasRandomDeath;

    /**
     * <h2>Other Game-Specific & Statistical Variables</h2>
     */
    public int daysElapsed;
    public int animalsKilled;

    public void Save() throws IOException {
        Path directory = Path.of(System.getProperty("user.home"), "/hlpdev/oregontrail/");
        Path path = Path.of(directory.toString(), this.playerMember.name() + "-save.json");

        if (Files.notExists(directory)) {
            Files.createDirectories(directory);
        }

        if (Files.notExists(path)) {
            Files.createFile(path);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(path.toFile())) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameState Load(String saveName) throws IOException, ClassNotFoundException {
        Path directory = Path.of(System.getProperty("user.home"), "/hlpdev/oregontrail/");
        Path path = Path.of(directory.toString(), saveName + "-save.json");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileReader reader = new FileReader(path.toFile())) {
            return gson.fromJson(reader, GameState.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GameState() {
        this.currentDay = 1;
        this.currentMonth = 0;
        this.departureMonth = 1;
        this.totalDistanceTraveled = 0;
        this.totalMoney = 0;
        this.profession = null;
        this.playerMember = null;

        this.partyMembers = new ArrayList<>();

        this.food = 0;
        this.ammunition = 0;
        this.clothing = 0;
        this.wagonWheels = 0;
        this.wagonAxles = 0;
        this.wagonTongues = 0;
        this.medicine = 0;
        this.oxen = 0;

        this.currentLocation = Location.INDEPENDENCE;
        this.weatherCondition = Weather.CLEAR;

        this.pace = Pace.STEADY;
        this.stamina = 100;

        this.hasBanditAttack = false;
        this.hasBearAttack = false;
        this.hasSnakeAttack = false;
        this.hasRandomDeath = false;

        this.daysElapsed = 0;
        this.animalsKilled = 0;
    }
}
