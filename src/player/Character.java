package player;

import skill.Skill;
import skill.attack.PowerStrike;
import skill.buff.Bless;

public class Character {
    private String userId;
    private String characterName;
    private int hp;
    private int mp;
    private boolean isAlive = true;

    private Skill[] haveSkills;

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
        haveSkills = new Skill[2];
        haveSkills[0] = new PowerStrike();
        haveSkills[1] = new Bless();
        str = 4;
        dex = 5;
        atk = str * 4;
        dodgeChance = (double) dex * 0.025;
        def = 5;
        exp = 0;
    }

    public void setAtk() {
        this.atk = str * 4;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public Skill[] getHaveSkills() {
        return haveSkills;
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
