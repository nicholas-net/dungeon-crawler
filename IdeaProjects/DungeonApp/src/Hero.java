// All code written by Nicholas Colon
// https://github.com/nicholas-net
public class Hero {

    // Each time the program is run, the hero gets random levels of health, damage, and agility
    public static int health = DungeonApp.random(20,30);
    public static int damage = DungeonApp.random(5,10);
    public static int agility = DungeonApp.random(0,10);

    // Debug hero stats for testing
    // public static int health = 200;
    // public static int damage = DungeonApp.random(5,10);
    // public static int agility = 5;

    public static int[] items = new int[6];
    public static int itemAmount = 0;
    public static int maxHealth = health;
    public static boolean isFleeing = false;
}
