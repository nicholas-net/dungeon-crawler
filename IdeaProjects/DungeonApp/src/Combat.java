// All code written by Nicholas Colon
// https://github.com/nicholas-net

import java.lang.Thread;

public class Combat {

    public static void print(String s) {
        System.out.println(s); }
    public static void printLine() {
        System.out.println("---------------------------------------------------------------------"); }

    // Method to check if the hero has died whenever any damage is taken
    public static void deathCheck() {
        if (Hero.health > 0)
            return;
        if (Hero.health <= 0) {
            printLine();
            print("");
            print("You have died. Have you tried not being bad at the game?");
            print("");
            printLine();
            System.exit(0); }}

    // This method dictates the flow and choices of fighting a monster
    public static void monsterFight(Entity enemy) {

        boolean enemyDefending = false;
        boolean heroDodging = false;

        if (Hero.items[3] == 1) {
            enemy.damage -= 1;
            print("Monsters will deal reduced damage (-1) to you due to your shield."); }

        print("You currently deal (" + Hero.damage + ") damage.");
        print("Your dexterity allows you a (" + (int) (((double) Hero.agility/10)*100) + "%) chance of dodging or running.");
        while (true) {
            heroDodging = false;
            if (Hero.items[1] == 1 && Hero.health < Hero.maxHealth) {
                Hero.health += 2;
                print("You take a sip of your potion and heal 2 hp."); }
            print("You currently have (" + Hero.health + ") health.");
            print("The monster currently has (" + enemy.health + ") health.");
            print("It's your turn. What would you like to do? Type A to attack, D to dodge, and R to run away.");
            char input = DungeonApp.input('A','D','R','0','0','0','0');
            printLine();

            // Sequence to play if the hero chooses to attack
            if (input == 'A') {
                // If the monster is not defending
                if (enemyDefending == false) {
                    enemy.health -= Hero.damage;
                    System.out.println("You attack and deal " + Hero.damage + " damage! The monster's health is now " + enemy.health + ".");
                    if (enemy.health <= 0) {
                        print("The monster screeches as it dies. You are victorious!");
                        break; }
                    printLine();
                }
                // If the monster is defending
                if (enemyDefending == true) {
                    enemy.health -= Hero.damage/2;
                    System.out.println("You attack but the monster partially blocks your attack. You deal " + Hero.damage/2 + " damage. The monster's health is now " + enemy.health + ".");
                    enemyDefending = false;
                    if (enemy.health <= 0) {
                        print("The monster screeches as it dies. You are victorious!");
                        break; }
                    printLine();
                }
            }

            // Sequence to play if the hero chooses to dodge
            if (input == 'D') {
                heroDodging = true;
                System.out.println("You prepare to dodge. Your chance of a successful dodge is " + (int) (((double) Hero.agility/10)*100) + "%.");
                printLine();
            }

            // Sequence to play if the hero chooses to flee
            if (input == 'R') {
                double run = Math.random();
                if ((double) Hero.agility/10 > run) {
                    print("Success! You pick a door at random and flee into one of the adjoining rooms. The monster doesn't follow.");
                    Hero.isFleeing = true;
                    break;
                }
                else {
                    print("You try to run but you trip on your shoelaces and fall down instead. Oh no!");
                    printLine();
                }
            }

            // If the hero has not killed the monster or successfully fled by the end of his turn, the monster's turn starts
            print("It's the monster's turn. It chooses to...");

            try {
                Thread.sleep(250);
                System.out.println(".");
                Thread.sleep(250);
                System.out.println(".");
                Thread.sleep(250);
                System.out.println(".");
                Thread.sleep(250);
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            enemyDefending = false;
            double monsterChoice = Math.random();
            if (monsterChoice >= 0.69) {
                print("Defend!");
                printLine();
                print("Your damage during the next turn will be reduced by 1/2.");
                enemyDefending = true;
                printLine();
            }
            else {
                print("Attack!");
                printLine();
                if (heroDodging == false) {
                    Hero.health -= enemy.damage;
                    print("It deals " + enemy.damage + " damage to you. Your health is now " + Hero.health + ".");
                    deathCheck();
                    printLine();
                }
                if (heroDodging == true) {
                    heroDodging = false;
                    double successfulDodge = Math.random();
                    if ((double) Hero.agility/10 >= successfulDodge) {
                        print("It swings at you but you successfully dodge.");
                        printLine();
                    }
                    else {
                        print("It swings at you and you attempt to dodge but you slip on a banana peel and fail.");
                        Hero.health -= enemy.damage;
                        print("It deals " + enemy.damage + " damage to you. Your health is now " + Hero.health + ".");
                        deathCheck();
                        printLine();
                    }
                }
            }
        }
    }

    // Method containing the sequence for the boss fight
    // The boss has increased damage and health compared to a regular monster, as well as the ability to heal himself and perform a special attack dealing extra damage
    public static void bossFight (Entity enemy) {
        printLine();
        print("You enter the next room. Magical energies swirl around the room, making it glow blue and purple.");
        print("The room is large, and bones lay scattered about.");
        print("In the center of the room is a pile of skulls partly melted together in the shape of a throne.");
        print("Sitting on the throne of skulls is an old man wearing dark robes. Beneath the hood of his robe you see red, evil eyes.");
        print("In his right hand is a wooden staff ornately decorated with patterns and words.");
        print("He sees you come in, and stands up. Neither of you speak; you both know this is a fight to the death.");
        printLine();
        System.out.println("The wizard's name is (" + enemy.name + "), his health is (" + enemy.health + "), and his damage is (" + enemy.damage + ").");
        printLine();

        boolean enemyDefending = false;
        boolean heroDodging = false;
        boolean wizardHealed = false;
        boolean wizardPoweringUp = false;

        if (Hero.items[3] == 1) {
            enemy.damage -= 1;
            print("The wizard will deal reduced damage (-1) to you due to your shield."); }

        print("You currently deal (" + Hero.damage + ") damage.");
        print("Your dexterity allows you a (" + (int) (((double) Hero.agility/10)*100) + "%) chance of dodging.");
        while (true) {
            heroDodging = false;
            if (Hero.items[1] == 1 && Hero.health < Hero.maxHealth) {
                Hero.health += 2;
                print("You take a sip of your potion and heal 2 hp."); }
            print("You currently have (" + Hero.health + ") health.");
            print("The wizard currently has (" + enemy.health + ") health.");
            print("It's your turn. What would you like to do? Type A to attack, or D to dodge.");
            char input = DungeonApp.input('A','D','0','0','0','0','0');
            printLine();

            // Sequence to play if the hero chooses to attack
            if (input == 'A') {
                // If the wizard is not defending
                if (enemyDefending == false) {
                    enemy.health -= Hero.damage;
                    System.out.println("You attack and deal " + Hero.damage + " damage! The wizard's health is now " + enemy.health + ".");
                    if (enemy.health <= 0) {
                        break; }
                    printLine();
                }
                // If the wizard is defending
                if (enemyDefending == true) {
                    enemy.health -= Hero.damage/2;
                    System.out.println("You attack but the wizard partially blocks your attack. You deal " + Hero.damage/2 + " damage. The wizard's health is now " + enemy.health + ".");
                    enemyDefending = false;
                    if (enemy.health <= 0) {
                        printLine();
                        break; }
                    printLine();
                }
            }

            // Sequence to play if the hero chooses to dodge
            if (input == 'D') {
                heroDodging = true;
                System.out.println("You prepare to dodge. Your chance of a successful dodge is " + (int) (((double) Hero.agility/10)*100) + "%.");
                printLine();
            }

            // If the hero has not killed the wizard or successfully fled by the end of his turn, the wizard's turn starts
            // The wizard has different and more powerful attacks compared to regular monsters
            print("It's the wizard's turn. He chooses to...");

            try {
                Thread.sleep(250);
                System.out.println(".");
                Thread.sleep(250);
                System.out.println(".");
                Thread.sleep(250);
                System.out.println(".");
                Thread.sleep(250);
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            // If the wizard is powering up his special attack, this happens. The special attack has a random chance to deal 1.5x, 2x, or 2.5x damage
            if (wizardPoweringUp == true) {

                print("Attack!");
                printLine();

                wizardPoweringUp = false;
                enemyDefending = false;

                double randomMultiplier = Math.random();
                int specialAttackDamage = enemy.damage;
                if (randomMultiplier >= 0.66) {
                    specialAttackDamage *= 2.5;
                }
                else if (randomMultiplier >= 0.33) {
                    specialAttackDamage *= 2;
                }
                else {
                    specialAttackDamage *= 1.5;
                }

                if (heroDodging == false) {
                    Hero.health -= specialAttackDamage;
                    print("He hurls an extra powerful magical fireball at you and deals " + specialAttackDamage + " damage to you. Your health is now " + Hero.health + ".");
                    deathCheck();
                    printLine(); }
                if (heroDodging == true) {
                    heroDodging = false;
                    double successfulDodge = Math.random();
                    if ((double) Hero.agility/10 >= successfulDodge) {
                        print("You successfully dodge the extra powerful fireball.");
                        printLine(); }
                    else {
                        print("He throws an extra powerful fireball at you and you attempt to dodge but you slip on a banana peel and fail.");
                        Hero.health -= specialAttackDamage;
                        print("He deals " + specialAttackDamage + " damage to you. Your health is now " + Hero.health + ".");
                        deathCheck();
                        printLine();
                    }
                }
                continue;
            }

            // When the wizard's health drops below 50%, he heals himself back up to 75-100% health
            if (enemy.health < enemy.maxHealth / 2 && wizardHealed == false) {
                wizardHealed = true;
                print("");
                print("Heal!");
                printLine();
                print("The wizard steps back clutching his wounds. Blood drips onto the ground at his feet.");
                print("He brings his staff up and yells a magical phrase, then brings it slamming down onto the ground.");
                print("Bright green energy flows out of his staff and surrounds him, healing his wounds and rejuvenating him.");
                printLine();

                try {
                    Thread.sleep(250);
                    System.out.println(".");
                    Thread.sleep(250);
                    System.out.println(".");
                    Thread.sleep(250);
                    System.out.println(".");
                    Thread.sleep(250);
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                int healAmount = enemy.maxHealth / 2;
                double randomHeal = Math.random();
                if (randomHeal < 0.50) {
                    randomHeal = 0.50;
                }
                randomHeal = randomHeal * healAmount;
                enemy.health += (int) randomHeal;
                printLine();
                System.out.println("The wizard has healed (" + (int) randomHeal + ") health. His health is now (" + enemy.health + ").");
                printLine();
                continue;
            }

            // If the wizard is not healing or performing his special attack, this standard combat sequence occurs
            enemyDefending = false;
            double wizardChoice = Math.random();

            if (wizardChoice >= 0.79) {
                wizardPoweringUp = true;
                enemyDefending = true;
                print("Charge a special atack!");
                print("The wizard raises his staff and begins charging a special attack.");
                print("While he charges his attack, a magical shield materializes around him. Your damage during the next turn will be reduced by 1/2.");
                print("After next turn he will unleash a powerful attack dealing up to 3x damage.");
                printLine();
            }
            else if (wizardChoice >= 0.59) {
                print("Defend!");
                printLine();
                print("A magical shield materializes around him.");
                print("Your damage during the next turn will be reduced by 1/2.");
                enemyDefending = true;
                printLine();
            }
            else {
                print("Attack!");
                printLine();
                if (heroDodging == false) {
                    Hero.health -= enemy.damage;
                    print("He hurls a magical fireball at you and deals " + enemy.damage + " damage to you. Your health is now " + Hero.health + ".");
                    deathCheck();
                    printLine(); }
                if (heroDodging == true) {
                    heroDodging = false;
                    double successfulDodge = Math.random();
                    if ((double) Hero.agility/10 >= successfulDodge) {
                        print("He throws a fireball at you but you successfully dodge.");
                        printLine(); }
                    else {
                        print("He throws a fireball at you and you attempt to dodge but you slip on a banana peel and fail.");
                        Hero.health -= enemy.damage;
                        print("He deals " + enemy.damage + " damage to you. Your health is now " + Hero.health + ".");
                        deathCheck();
                        printLine();
                    }
                }
            }
        }
    }
}
