package pokemonGameUtiles;

public class Stats {
    private int hp;
    private int atk;
    private int def;
    private int spAtk;
    private int spDef;
    private int spd;

    public Stats(int hp, int atk, int def, int spAtk, int spDef, int spd) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.spAtk = spAtk;
        this.spDef = spDef;
        this.spd = spd;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "hp=" + hp +
                ", atk=" + atk +
                ", def=" + def +
                ", spAtk=" + spAtk +
                ", spDef=" + spDef +
                ", spd=" + spd +
                '}';
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public void setSpAtk(int spAtk) {
        this.spAtk = spAtk;
    }

    public void setSpDef(int spDef) {
        this.spDef = spDef;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }
}
