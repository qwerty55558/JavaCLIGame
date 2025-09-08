package monster;

public class Monster {
    private String name;
    private int Hp;
    private int atk;
    private int def;
    private int exp;

    public Monster() {
    }

    public Monster(String name, int Hp, int atk, int def) {
        this.name = name;
        this.Hp = Hp;
        this.atk = atk;
        this.def = def;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    private int[] dropTable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return Hp;
    }

    public void setHp(int hp) {
        Hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int[] getDropTable() {
        return dropTable;
    }

    public void setDropTable(int[] dropTable) {
        this.dropTable = dropTable;
    }
}

