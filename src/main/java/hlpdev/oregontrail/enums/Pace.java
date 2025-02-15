package hlpdev.oregontrail.enums;

import java.util.Arrays;

/**
 * The Pace enum stores which paces the player can travel at
 * Each pace stores a visual representation, a speed multiplier, a health impact multiplier,
 * a food consumption multiplier, and a sickness chance
 * <p>
 * I made this early on and some fields are not fully used
 */
public enum Pace {
    STEADY("Steady", 1, 1, 1, 0.01),
    STRENUOUS("Strenuous", 1.5, 1.2, 1.2, 0.05),
    GRUELING("Grueling", 2, 1.8, 1.8, 0.1);

    public final String visualRepresentation;
    public final double speedMultiplier;
    public final double healthImpactMultiplier;
    public final double foodConsumptionMultiplier;
    public final double sicknessChance;

    Pace(String visualRepresentation, double speedMultiplier, double healthImpactMultiplier, double foodConsumptionMultiplier, double sicknessChance) {
        this.visualRepresentation = visualRepresentation;
        this.speedMultiplier = speedMultiplier;
        this.healthImpactMultiplier = healthImpactMultiplier;
        this.foodConsumptionMultiplier = foodConsumptionMultiplier;
        this.sicknessChance = sicknessChance;
    }

    public static Pace fromString(String pace) {
        //noinspection OptionalGetWithoutIsPresent
        return Arrays.stream(Pace.values()).filter(p -> p.visualRepresentation.equals(pace)).findFirst().get();
    }
}
