package hlpdev.oregontrail.npcs;

public abstract class Names {
    private static final String[] NAMES = {
            "Silas McGraw",
            "Ezekiel Tate",
            "Amos Whitmore",
            "Clement Harper",
            "Tobias Finch",
            "Rufus Callahan",
            "Elijah Mercer",
            "Phineas Holloway",
            "Bartholomew \"Bart\" Cobb",
            "Cyrus Dunbar",
            "Obadiah Wells",
            "Thaddeus Crowley",
            "Horace Pritchard",
            "Zeke Rawlins",
            "Gideon Hawthorne",
            "Luther Beauregard",
            "Otis McBride",
            "Solomon Redding",
            "Hiram Thatcher",
            "Barnabas Cooke",
            "Enoch Driscoll",
            "Augustus \"Gus\" Vickers",
            "Michael Scofield",
            "John Rice",
            "Aidan Warren",
            "Logan Johnson",
            "Sarah McOwens",
            "Donald \"Jay\" McNutt",
            "Jay Bartholomew \"Jaybarrow\" Dingleberry",
            "Buck \"No Luck\" Jenkins"
    };

    public static String getRandomName() {
        return NAMES[(int) (Math.random() * NAMES.length)];
    }
}
