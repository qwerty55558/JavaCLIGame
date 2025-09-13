package skill.attack;

import skill.Skill;

public class PowerStrike extends Skill {
    public PowerStrike(){
        super("강타", 2);
        this.setBuff(false);
    }
    @Override
    public void calcDmg(int str) {
        setDmg(str * 6);
    }
}
