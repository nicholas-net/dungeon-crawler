// All code written by Nicholas Colon
// https://github.com/nicholas-net

public class Entity {

    boolean hostile;
    String name;
    int health;
    int maxHealth;
    int damage;

    public Entity(boolean hostile, String name, int health, int damage) {
        this.hostile = hostile;
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.damage = damage;
    }
}

