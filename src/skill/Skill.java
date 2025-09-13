package skill;

public abstract class Skill {
    private String skillName;
    private int mpCost;
    private int dmg;
    private boolean isBuff;

    public void setBuff(boolean buff) {
        isBuff = buff;
    }

    public boolean isBuff() {
        return isBuff;
    }

    public Skill(String skillName, int mpCost) {
        this.skillName = skillName;
        this.mpCost = mpCost;
    }

    public String getSkillName() {
        return skillName;
    }

    public int getMpCost() {
        return mpCost;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public abstract void calcDmg(int str);
}
