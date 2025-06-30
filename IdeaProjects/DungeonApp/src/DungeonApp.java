// All code written by Nicholas Colon
// https://github.com/nicholas-net

import java.util.Scanner;

public class DungeonApp {

    public static int[] itemsToSpawn = {1,1,1,1,1,1};
    public static Room[] rooms = new Room[8];

    public static String[][] charMap = {
            {"[ ]"},
            {"[ ]","[ ]","[ ]"},
            {"[ ]","[ ]","[ ]","[ ]","[ ]"},
            {"[ ]","[ ]","[ ]"},
            {"[ ]"}
    };

    /*
     *                           [Boss]
     *                              I
     *               [Generic1]-[Generic2]-[Generic3]
     *                    I         I          I
     *     [Weapon1]-[Generic4]-[Fountain]-[Generic5]-[Weapon2]
     *                    I         I          I
     *               [Generic6]-[Generic7]-[Generic8]
     *                              I
     *                           [Start]
     */

    public static int random(int min, int max) {
        return (int) (Math.random()*(max-min+1))+min; }

    public static void print(String s) {
        System.out.println(s); }

    public static void printLine() {
        System.out.println("---------------------------------------------------------------------"); }

    // Method to check if an input is valid or not
    public static char input(char a, char b, char c, char d, char e, char f, char g) {
        while (true) {
            Scanner in = new Scanner(System.in);
            char input = in.next().charAt(0);
            if (input == a || input == b || input == c || input == d || input == e || input == f || input == g) {
                return input;
            }
            else {
                print("That is not a valid input. Please try again.");
            }
        }
    }

    // Method prints the map as well as the hero's position in it
    public static void printMap(int y, int x) {
        charMap[y][x] = "[X]";
        printLine();
        System.out.println("      "+charMap[0][0]);
        System.out.println("   "+charMap[1][0]+charMap[1][1]+charMap[1][2]);
        System.out.println(charMap[2][0]+charMap[2][1]+charMap[2][2]+charMap[2][3]+charMap[2][4]);
        System.out.println("   "+charMap[3][0]+charMap[3][1]+charMap[3][2]);
        System.out.println("      "+charMap[4][0]);
        charMap[y][x] = "[ ]";
    }

    public static void main(String[] args) {

        // Generate entities and rooms. Monsters and generic rooms are added to arrays in order to randomly place them later
        Entity wizard = new Entity(true,"Gandalf",random(20,30),random(5,10));
        Entity monster1 = new Entity(true,"Norris",random(15,20),random(4,6));
        Entity monster2 = new Entity(true,"Weston",random(15,20),random(4,6));
        Entity monster3 = new Entity(true,"Ester",random(15,20),random(4,6));
        Entity monster4 = new Entity(true,"Selene",random(15,20),random(4,6));

        Entity[] monsters = new Entity[5];
        monsters[0] = monster1; monsters[1] = monster2; monsters[2] = monster3; monsters[3] = monster4;

        Room bossRoom = new Room("Boss",true,new int[6],0,0,wizard);
        Room weaponRoom1 = new Room("Weapon",false,new int[6],2,0);
        Room weaponRoom2 = new Room("Weapon",false,new int[6],2,4);
        Room startRoom = new Room("Start",false,new int[6],4,0);
        Room fountainRoom = new Room("Fountain",false,new int[6],2,2);
        Room blank = new Room("blank",false,new int[6],-1,-1);

        Room generic1 = new Room("Generic",false,new int[6],1,0);
        Room generic2 = new Room("Generic",false,new int[6],1,1);
        Room generic3 = new Room("Generic",false,new int[6],1,2);
        Room generic4 = new Room("Generic",false,new int[6],2,1);
        Room generic5 = new Room("Generic",false,new int[6],2,3);
        Room generic6 = new Room("Generic",false,new int[6],3,0);
        Room generic7 = new Room("Generic",false,new int[6],3,1);
        Room generic8 = new Room("Generic",false,new int[6],3,2);
        rooms[0] = generic1; rooms[1] = generic2; rooms[2] = generic3; rooms[3] = generic4;
        rooms[4] = generic5; rooms[5] = generic6; rooms[6] = generic7; rooms[7] = generic8;


        // The following code randomly spawns monsters in 4 of the 8 generic rooms. Each time the program is run, monsters will occupy different rooms
        int j = 0;
        double toSpawn = 4;
        double roomsLeft = 8;
        while (toSpawn > 0) {
            double chance = Math.random();
            double compare = toSpawn / roomsLeft;
            int i = random(1,7);
            if (chance >= compare && rooms[i] != null) {
                rooms[i].entity = monsters[j];
                rooms[i].occupied = true;
                j++;
                toSpawn--;
                roomsLeft--;
                rooms[i] = null;
            }
        }

        // Populate rooms with references to both themselves and their adjacent rooms
        // blank rooms are null rooms only meant to fill out the adjacentRooms[] array for rooms that have less than 4 neighbors
        startRoom.adjacentRooms(startRoom,generic7,blank,blank,blank);
        fountainRoom.adjacentRooms(fountainRoom,generic2,generic4,generic5,generic7);
        generic1.adjacentRooms(generic1,blank,blank,generic2,generic4);
        generic2.adjacentRooms(generic2,bossRoom,generic1,generic3,fountainRoom);
        generic3.adjacentRooms(generic3,blank,generic2,blank,generic5);
        generic4.adjacentRooms(generic4,generic1,weaponRoom1,fountainRoom,generic6);
        generic5.adjacentRooms(generic5,generic3,fountainRoom,weaponRoom2,generic8);
        generic6.adjacentRooms(generic6,generic4,blank,generic7,blank);
        generic7.adjacentRooms(generic7,fountainRoom,generic6,generic8,startRoom);
        generic8.adjacentRooms(generic8,generic5,generic7,blank,blank);
        bossRoom.adjacentRooms(bossRoom,blank,blank,blank,generic2);
        weaponRoom1.adjacentRooms(weaponRoom1,blank,blank,generic4,blank);
        weaponRoom2.adjacentRooms(weaponRoom2,blank,generic5,blank,blank);

        // Randomly spawn items and weapons in their respective rooms. Items only spawn in rooms not occupied by monsters
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i] != null) {
                Item.spawnItems(rooms[i]);
            }
        }
        Item.spawnWeapons(weaponRoom1);
        Item.spawnWeapons(weaponRoom2);

        // The game begins
        printLine();
        print("You wake up in a dark room lit only by the dim glow of a torch on the wall. You don't remember how you got here.");
        print("You get up to survey the room. You surmise that you are trapped in a dungeon. The only way out is a door in front of you.");
        print("On the ground next to you is a skeleton. Clutched in one of its bony hands is a rusty sword, and clutched in the other is a parchment map. You pick up both.");
        print("Somehow you know that if you wish to leave the dungeon alive you will have to fight hard. You prepare yourself for the battles ahead.");
        System.out.println("You take a look at yourself. You have (" + Hero.health + ") health, you deal (" + Hero.damage + ") damage, and your agility is (" + Hero.agility + ").");
        startRoom.userChoice();
    }
}
