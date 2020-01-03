package pokemonGameUtiles;

import exceptions.MoveCreationException;

public class Move implements Comparable<Move>{
    private int id;
    private String name;
    private String type;
    private String category;
    private String contest;
    private int PP;
    private int power;
    private int accuracy;
    private String description;

    public Move(int id, String name, String type, String category, String contest, int PP, int power, int accuracy, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.category = category;
        this.contest = contest;
        this.PP = PP;
        this.power = power;
        this.accuracy = accuracy;
       this.description=description;
    }

    public int compareTo(Move mTest) {
        if (mTest.getName().compareTo(name)==0) return 0;
        else return 1;
    }

    public String getName() {
        return name;
    }

    public int getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return "Move{\n" +
                "id=" + id + '\n' +
                "name=" + name + '\n' +
                "type=" + type + '\n' +
                "category=" + category + '\n' +
                "contest=" + contest + '\n' +
                "PP=" + PP + '\n' +
                "power=" + power + '\n' +
                "accuracy=" + accuracy + '\n' +
                "description=" + description + '\n' +
                '}';
    }
}
