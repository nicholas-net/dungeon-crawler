// All code written by Nicholas Colon
// https://github.com/nicholas-net

public class Room {

    String type;
    boolean occupied;
    int[] items;
    Entity entity;
    Room[] adjacent = new Room[5];
    int[] coords = new int[2];
    boolean hasDeadMonster = false;

    // Room constructor specifying the room's type, whether its occupied, its items, and its position
    public Room(String type, boolean occupied, int[] items, int y, int x) {
        this.type = type;
        this.occupied = occupied;
        this.items = items;
        this.coords[0] = y;
        this.coords[1] = x;
    }

    // Same as above except this constructor is for rooms with entities such as monsters
    public Room(String type, boolean occupied, int[] items, int y, int x, Entity entity) {
        this.type = type;
        this.occupied = occupied;
        this.items = items;
        this.entity = entity;
        this.coords[0] = y;
        this.coords[1] = x;
    }

    // Specifies the room's neighbors
    public void adjacentRooms(Room self, Room north, Room west, Room east, Room south) {
        this.adjacent[0] = self;
        this.adjacent[1] = north;
        this.adjacent[2] = west;
        this.adjacent[3] = east;
        this.adjacent[4] = south;
    }

    public void print(String s) {
        System.out.println(s); }
    public void printLine() {
        System.out.println("---------------------------------------------------------------------"); }

    // Method that allows users to choose what to do in each room
    public void userChoice() {
        while (true) {
            printLine();
            print("What would you like to do? Type a direction to move, press G or D to get or drop an item, or press M to view the map.");
            print("Valid directions: ");
            char input1 = '0'; char input2 = '0'; char input3 = '0'; char input4 = '0';
            if (adjacent[1].type != "blank") {
                System.out.print("N ");
                input1 = 'N'; }
            if (adjacent[2].type != "blank") {
                System.out.print("W ");
                input2 = 'W'; }
            if (adjacent[3].type != "blank") {
                System.out.print("E ");
                input3 = 'E'; }
            if (adjacent[4].type != "blank") {
                System.out.print("S ");
                input4 = 'S'; }
            print("");
            printLine();
            char input = DungeonApp.input(input1,input2,input3,input4,'G','D','M');
            switch (input) {
                case('N'): print("You decide to move north."); printLine(); adjacent[1].gameplay();  break;
                case('W'): print("You decide to move west."); printLine(); adjacent[2].gameplay();  break;
                case('E'): print("You decide to move east."); printLine(); adjacent[3].gameplay();  break;
                case('S'): print("You decide to move south."); printLine(); adjacent[4].gameplay();  break;
                case('M'): print("You look at your map."); DungeonApp.printMap(coords[0],coords[1]); break;
                case('G'):  if (Item.roomItemCheck(adjacent[0]))
                    Item.getItem(adjacent[0]);
                else
                    print("There are no items to pick up here.");
                    break;
                case('D'):  if (Item.heroItemCheck())
                    Item.dropItem(adjacent[0]);
                else
                    print("You have no items to drop.");
                    break; }}}

    // Method that dictates what events occur when the hero enters a room, depending on the type of room and its occupants
    public void gameplay() {
        if (occupied == true && type == "Generic") {
            print("The room is dimly lit, but you can see that a monster occupies this room. It notices you enter, and turns towards you. It snarls and bares its teeth!");
            System.out.println("The monster's name is (" + entity.name + "), its health is (" + entity.health + "), and its damage is (" + entity.damage + "). Get ready to fight!");
            printLine();
            Combat.monsterFight(entity);
            if (Hero.isFleeing == true) {
                Hero.isFleeing = false;
                if (Hero.items[3] == 1)
                    entity.damage += 1;

                while (true) {
                    int randomNum = DungeonApp.random(1,4);
                    if (adjacent[randomNum].type != "blank")
                        adjacent[randomNum].gameplay(); }}
            else {
                occupied = false;
                hasDeadMonster = true;
                print("You catch your breath, survey the dead monster, and then decide to move on.");
                userChoice();
            }
        }
        if (occupied == false && (type == "Generic" || type == "Start")) {
            print("You enter a room.");
            if (Item.roomItemCheck(adjacent[0]))
                print("You see items on the ground.");
            if (hasDeadMonster == true) {
                print("A dead monster lay on the ground where you slew it. You give it a good kick just to make sure it's dead.");
            }
            userChoice();
        }
        if (occupied == false && type == "Weapon") {
            print("You enter a strange circular room.");
            if (Item.roomItemCheck(adjacent[0])) {
                print("In the center of the room there is a pedastal holding a powerful weapon.");
                print("You think you may be able to pick it up and use it.");
            }
            userChoice();
        }
        if (occupied == false && type == "Fountain") {
            print("You enter a beautiful, well-lit room. In its center is a finely carved fountain, made of pristine marble and bubbling with crystal clear water.");
            print("You suddenly realize how thirsty you are. You walk closer and examine the water. It looks so refreshing. You feel an almost overwhelming urge to drink from it.");
            print("Press Y to drink from the pristine fountain or press N to resist.");
            char inputFountain = DungeonApp.input('Y', 'N', '0', '0', '0', '0','0');
            if (inputFountain == 'Y') {
                type = "DrankFountain";
                printLine();
                print("You bend down and have a drink, giving in to the temptation.");
                double fountain = Math.random();
                if (fountain < 0.2) {
                    Hero.damage += 2;
                    print("The water tastes so you could almost cry. Your thirst vanishes and you stand back up. Somehow you feel stronger now...");
                    print("Your damage has increased by (+2). It is now (" + Hero.damage + ")."); }
                if (fountain >= 0.2 && fountain < 0.4) {
                    Hero.agility += 2;
                    print("The water tastes so you could almost cry. Your thirst vanishes and you stand back up. Somehow you feel faster now...");
                    print("Your agility has increased by (+2). It is now (" + Hero.agility + ")."); }
                if (fountain >= 0.4 && fountain < 0.6) {
                    Hero.health -= 2;
                    print("The water tastes awful, like bitter chalk. As you watch the water magically turns from crystal clear to a dark, cloudy gray. You gag, and almost throw up.");
                    print("Your health has decreased by (-2). It is now (" + Hero.health + ").");
                    Combat.deathCheck(); }
                if (fountain >= 0.6) {
                    print("The water tastes... okay. It isn't as refreshing as you thought it would be. Kind of disapointing, really."); }
                userChoice(); }
            if (inputFountain == 'N') {
                printLine();
                print("You resist the temptation. You back away from the fountain and examine the rest of the room. There are 4 doors leading away.");
                userChoice(); }
        }
        if (occupied == false && type == "DrankFountain") {
            print("This is the room with the fountain you drank from before.");
            userChoice(); }

        if (type == "Boss") {
            if (Hero.items[0] == 0) {
                print("A locked gate blocks the way into the northernmost room.");
                print("You must find the key first in order to proceed.");
            }
            if (Hero.items[0] == 1) {
                print("You use your key to unlock the gate. It swings open loudly.");
                print("Further down the hall you see a faint eery glow emnating from the next room's doorway.");
                print("You prepare yourself and move down the hall. Your hairs stand on end as you feel magic energies coursing through the air.");
                print("You are about to fight the final boss. You will not be able to run away after proceeding.");
                print("Type Y to enter the next room and slay the wizard, or press N to go back and prepare further.");
                printLine();
                char inputBoss = DungeonApp.input('Y', 'N', '0','0','0','0','0');
                if (inputBoss == 'Y') {
                    Combat.bossFight(entity);
                    printLine();
                    print("*****************************");
                    printLine();
                    print("You are victorious!");
                    printLine();
                    print("*****************************");
                    printLine();
                    System.exit(0);

                }

            }
        }
    }
}
