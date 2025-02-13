package hlpdev.oregontrail.enums;

public enum Location {
    INDEPENDENCE(0, "Independence, Missouri", "Missouri Grasslands"),
    FORT_KEARNEY(100, "Fort Kearney, Nebraska", "Nebraska Grasslands"),
    CHIMNEY_ROCK(200, "Chimney Rock, Nebraska", "Nebraska Grasslands"),
    LARAMIE(300, "Laramie, Wyoming", "Wyoming Forests"),
    INDEPENDENCE_ROCK(400, "Independence Rock, Wyoming", "Wyoming Desert"),
    SOUTH_PASS(500, "South Pass, Wyoming", "Wyoming Forests"),
    SODA_SPRINGS(600, "Soda Springs, Idaho", "Idaho Desert"),
    FORT_HALL(700, "Fort Hall, Idaho", "Idaho Mountains"),
    FORT_BOISE(800, "Fort Boise, Idaho", "Idaho Mountains"),
    BLUE_MOUNTAINS(900, "Blue Mountains, Oregon", "Oregon Mountains"),
    THE_DALLES(1000, "The Dalles, Oregon", "Oregon Forests"),
    OREGON_CITY(1100, "Oregon City, Oregon", "Oregon Forests");

    public final int location;
    public final String name;
    public final String relativeLocation;

    Location(int location, String name, String relativeLocation) {
        this.location = location;
        this.name = name;
        this.relativeLocation = relativeLocation;
    }

    public static Location getLocation(int location) {
        for (Location loc : values()) {
            if (loc.location == location) {
                return loc;
            }
        }

        return null;
    }

    public static Location getClosestLocation(int location) {
        Location closest = Location.INDEPENDENCE;
        for (Location loc : values()) {
            if (loc.location <= location) {
                closest = loc;
            } else {
                break;
            }
        }

        return closest;
    }

    public static boolean isCloseToPointOfInterest(int distanceTraveled) {
        for (int x = 0; x <= 1100; x += 100) {
            if (java.lang.Math.abs(distanceTraveled - x) <= 10) {
                return true;
            }
        }

        return false;
    }

    public static Location getPointOfInterest(int distanceTraveled) {
        for (int x = 0; x <= 1100; x += 100) {
            if (java.lang.Math.abs(distanceTraveled - x) <= 10) {
                return Location.getLocation(x);
            }
        }

        return null;
    }
}
