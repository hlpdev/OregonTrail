package hlpdev.oregontrail.records;

import java.io.Serializable;

/**
 * This record is used to store the information about "AI" party members during a playthrough
 * @param name The name of the member
 * @param health The health of the member
 * @param isAlive Whether the member is alive or not
 * @param foodConsumed How much food the member has consumed (Not really used for anything at the moment)
 * @param hasDisease If the member is ill
 * @param hasBeenBuried If the member has been buried
 */
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
