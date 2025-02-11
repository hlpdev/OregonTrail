package hlpdev.oregontrail;

import java.io.Serializable;

public record PartyMember(String name, int health, boolean isAlive, int foodConsumed, boolean hasDisease) implements Serializable {
    public PartyMember newHealth(int health) {
        return new PartyMember(name, health, isAlive, foodConsumed, hasDisease);
    }

    public PartyMember newAlive(boolean isAlive) {
        return new PartyMember(name, health, isAlive, foodConsumed, hasDisease);
    }

    public PartyMember newFoodConsumed(int foodConsumed) {
        return new PartyMember(name, health, isAlive, foodConsumed, hasDisease);
    }

    public PartyMember newDisease(boolean hasDisease) {
        return new PartyMember(name, health, isAlive, foodConsumed, hasDisease);
    }
}
