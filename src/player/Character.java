package player;

public class Character {
    private String userId;
    private String characterName;
    private int hp;
    private int mp;
    private boolean isAlive = true;

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    private int[] haveSkills;

    private int atk;
    private int def;
    private double dodgeChance;

    private int str;
    private int dex;

    private int exp;

    public Character(String userId, String characterName){
        this.userId = userId;
        this.characterName = characterName;
        hp = 100;
        mp = 10;
        haveSkills = new int[10];
        str = 4;
        dex = 5;
        atk = str * 4;
        dodgeChance = (double) dex * 0.025;
        def = 5;
        exp = 0;
    }

    public String getCharacterName(){
        return characterName;
    }

    public String getUserId() {
        return userId;
    }

    public int getHp() {
        return hp;
    }

    public int getMp() {
        return mp;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public double getDodgeChance() {
        return dodgeChance;
    }

    public int getStr() {
        return str;
    }

    public int getDex() {
        return dex;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
