// All code written by Nicholas Colon
// https://github.com/nicholas-net

public class Item {

    public static void print(String s) {
        System.out.println(s); }
    public static void printLine() {
        System.out.println("---------------------------------------------------------------------"); }

    // Method to randomly spawn items in designated rooms. Each item is spawned only once
    public static void spawnItems(Room room) {

        while (true) {
            int n = DungeonApp.random(0,3);
            if (DungeonApp.itemsToSpawn[n] == 1) {
                DungeonApp.itemsToSpawn[n]--;
                room.items[n]++;
                return; }}
    }

    // Method to randomly spawn weapons in designated rooms
    public static void spawnWeapons(Room room) {

        while (true) {
            int n = DungeonApp.random(4,5);
            if (DungeonApp.itemsToSpawn[n] == 1) {
                DungeonApp.itemsToSpawn[n]--;
                room.items[n]++;
                return; }}
    }

    // Method to check if a room has an item in it
    public static boolean roomItemCheck(Room room) {
        for (int i = 0; i < room.items.length; i++) {
            if (room.items[i] == 1)
                return true; }
        return false;
    }

    // Method to check if the hero is holding any items
    public static boolean heroItemCheck() {
        for (int i = 0; i < Hero.items.length; i++) {
            if (Hero.items[i] == 1)
                return true; }
        return false;
    }

    // Method to pick up an item from a room
    public static void getItem(Room room) {
        if (!roomItemCheck(room)) {
            print("There are no items to pick up here.");
            return;
        }
        else {
            char K = '0'; char P = '0'; char R = '0'; char S = '0'; char G = '0'; char F = '0';
            print("Items here:");
            if (room.items[0] == 1) {
                print("Key (K)");
                K = 'K'; }
            if (room.items[1] == 1) {
                print("Potion (P)");
                P = 'P'; }
            if (room.items[2] == 1) {
                print("Ring (R)");
                R = 'R'; }
            if (room.items[3] == 1) {
                print("Shield (S)");
                S = 'S'; }
            if (room.items[4] == 1) {
                print("Greatsword (G)");
                G = 'G'; }
            if (room.items[5] == 1) {
                print("Flamethrower (F)");
                F = 'F'; }
            print("What would you like to pick up? Press the corresponding key to pick that item up or press C to cancel.");
            char input = DungeonApp.input(K, P, R, S, G, F, 'C');
            printLine();

            switch (input) {
                case('C'): return;
                case('K'):
                    if (Hero.itemAmount == 3) {
                        print("You cannot hold more than 3 items. Drop one to pick up another.");
                        return;
                    }
                    Hero.items[0]++;
                    Hero.itemAmount++;
                    print("You pick up the key. You think you will be able to open any locked doors you encounter with this.");
                    room.items[0]--; break;
                case('P'):
                    if (Hero.itemAmount == 3) {
                        print("You cannot hold more than 3 items. Drop one to pick up another.");
                        return;
                    }
                    Hero.items[1]++;
                    Hero.itemAmount++;
                    print("You pick up the potion. It smells strange, but you think you can drink it during combat to heal yourself.");
                    room.items[1]--; break;
                case('R'):
                    if (Hero.itemAmount == 3) {
                        print("You cannot hold more than 3 items. Drop one to pick up another.");
                        return;
                    }
                    Hero.items[2]++;
                    Hero.agility += 2;
                    Hero.itemAmount++;
                    print("You pick up the magical ring. You feel... quicker. Your agility has increased by (+2).");
                    room.items[2]--; break;
                case('S'):
                    if (Hero.itemAmount == 3) {
                        print("You cannot hold more than 3 items. Drop one to pick up another.");
                        return;
                    }
                    Hero.items[3]++;
                    Hero.itemAmount++;
                    print("You pick up the shield. Enemies will now deal reduced damage to you.");
                    room.items[3]--; break;
                case('G'):
                    if (Hero.items[5] == 1) {
                        print("You cannot hold both the flamethrower and the greatsword. Drop the flamethrower first.");
                        break; }
                    else {
                        Hero.items[4]++;
                        Hero.damage += 2;
                        print("You sheath the rusty sword you found earlier and wield the greatsword. Your damage has increased by (+2).");
                        room.items[4]--; break; }
                case('F'):
                    if (Hero.items[4] == 1) {
                        print("You cannot hold both the greatsword and the flamethrower. Drop the greatsword first.");
                        break; }
                    else {
                        Hero.items[5]++;
                        Hero.damage += 4;
                        Hero.agility -= 4;
                        print("You sheath the rusty sword you found earlier and wield the flamethrower. It is very heavy.");
                        print("Your damage has increased by (+4) but your agility has decreased by (-4).");
                        room.items[5]--; break; }
            }
        }
    }

    // Method to drop an item in a room
    public static void dropItem(Room room) {
        if (!heroItemCheck()) {
            print("You have no items to drop.");
            return; }
        else {
            char K = '0'; char P = '0'; char R = '0'; char S = '0'; char G = '0'; char F = '0';
            print("Items you can drop:");
            if (Hero.items[0] == 1) {
                print("Key (K)");
                K = 'K'; }
            if (Hero.items[1] == 1) {
                print("Potion (P)");
                P = 'P'; }
            if (Hero.items[2] == 1) {
                print("Ring (R)");
                R = 'R'; }
            if (Hero.items[3] == 1) {
                print("Shield (S)");
                S = 'S'; }
            if (Hero.items[4] == 1) {
                print("Greatsword (G)");
                G = 'G'; }
            if (Hero.items[5] == 1) {
                print("Flamethrower (F)");
                F = 'F'; }
            print("What would you like to drop? Press the corresponding key to drop that item or press C to cancel.");
            char input = DungeonApp.input(K, P, R, S, G, F, 'C');
            printLine();

            switch (input) {
                case('C'): return;
                case('K'):
                    room.items[0]++;
                    print("You drop the key. You will no longer be able to open locked doors.");
                    Hero.items[0]--;
                    Hero.itemAmount--; break;
                case('P'):
                    room.items[1]++;
                    print("You drop the potion.");
                    Hero.items[1]--;
                    Hero.itemAmount--; break;
                case('R'):
                    room.items[2]++;
                    Hero.agility -= 2;
                    print("You drop the magical ring. You feel... slower. Your agility has decreased by (-2).");
                    Hero.items[2]--;
                    Hero.itemAmount--; break;
                case('S'):
                    room.items[3]++;
                    print("You drop the shield. Enemies will no longer deal reduced damage to you.");
                    Hero.items[3]--;
                    Hero.itemAmount--; break;
                case('G'):
                    room.items[4]++;
                    Hero.damage -= 2;
                    print("You drop the greatsword. Your damage has decreased by (-2).");
                    Hero.items[4]--; break;
                case('F'):
                    room.items[5]++;
                    Hero.damage -= 4;
                    Hero.agility += 4;
                    print("You drop the flamethrower. Your damage has decreaed by (-4) and your agility has increased by (+4).");
                    Hero.items[5]--; break;
            }
        }
    }
}
