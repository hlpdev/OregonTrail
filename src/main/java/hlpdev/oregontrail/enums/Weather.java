package hlpdev.oregontrail.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The Weather enum stores the types of weather that can occur on the oregon trail.
 * Each type of weather stores a speed multiplier, health impact multiplier, food consumption multiplier,
 * sickness multiplier, and randomness weight (used for deciding the next type of weather at random)
 */
public enum Weather {
    CLEAR("Clear", 1, 1, 1, 0.5, 7),
    SUNNY("Sunny", 1.1, 1, 0.9, 0.5, 4),
    RAIN("Rain", 0.9, 1.1, 1, 1.2, 2),
    STORMY("Stormy", 0.6, 1.1, 1.2, 1.3, 1),
    SNOW("Snow", 0.5, 1.3, 1.5, 1.5, 1),
    FOG("Fog", 0.6, 1, 1, 0.5, 1),
    HOT("Hot", 0.9, 1.1, 1.2, 1, 2),
    COLD("Cold", 0.9, 1.4, 1.5, 2, 2);

    public final String visualRepresentation;

    public final double speedMultiplier;
    public final double healthImpactMultiplier;
    public final double foodConsumptionMultiplier;
    public final double sicknessMultiplier;
    public final int randomnessWeight;

    Weather(String visualRepresentation, double speedMultiplier, double healthImpactMultiplier, double foodConsumptionMultiplier, double sicknessMultiplier, int randomnessWeight) {
        this.visualRepresentation = visualRepresentation;
        this.speedMultiplier = speedMultiplier;
        this.healthImpactMultiplier = healthImpactMultiplier;
        this.foodConsumptionMultiplier = foodConsumptionMultiplier;
        this.sicknessMultiplier = sicknessMultiplier;
        this.randomnessWeight = randomnessWeight;
    }

    public static Weather getRandomWeather() {
        List<Weather> weathers = new ArrayList<>();
        for (Weather weather : Weather.values()) {
            for (int i = 0; i < weather.randomnessWeight; i++) {
                weathers.add(weather);
            }
        }

        Random random = new Random();

        Collections.shuffle(weathers, random);
        return weathers.get(random.nextInt(weathers.size()));
    }
}
