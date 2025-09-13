package monster.shoreline;

import monster.Monster;

public class Zombie extends Monster {
    public Zombie() {
        super("좀비", 50, 9, 2);
        this.setExp(15);
    }
}
