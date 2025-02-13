package hlpdev.oregontrail.records;

import java.io.Serializable;

public record PartyMember(String name, int health, boolean isAlive, int foodConsumed, boolean hasDisease, boolean hasBeenBuried) implements Serializable {
    public PartyMember newHealth(int health) {
        return new PartyMember(name, health, isAlive, foodConsumed, hasDisease, hasBeenBuried);
    }

    public PartyMember newAlive(boolean isAlive) {
        return new PartyMember(name, health, isAlive, foodConsumed, hasDisease, hasBeenBuried);
    }

    public PartyMember newFoodConsumed(int foodConsumed) {
        return new PartyMember(name, health, isAlive, foodConsumed, hasDisease, hasBeenBuried);
    }

    public PartyMember newDisease(boolean hasDisease) {
        return new PartyMember(name, health, isAlive, foodConsumed, hasDisease, hasBeenBuried);
    }

    public PartyMember newBuried(boolean hasBeenBuried) {
        return new PartyMember(name, health, isAlive, foodConsumed, hasDisease, hasBeenBuried);
    }
}
