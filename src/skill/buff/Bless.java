package skill.buff;

import skill.Skill;

public class Bless extends Skill {
    private int turn;

    public Bless(){
        super("축복", 1);
        this.setBuff(true);
        setTurn(3);
    }
    @Override
    public void calcDmg(int str) {
        setDmg(0);
    }

    public int getBuffStatus(int str){
        return str + 3;
    }

    public int getDebuff(int str){
        return str - 3;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getTurn() {
        return turn;
    }
}
