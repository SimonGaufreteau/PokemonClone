package pokemonGameUtiles;

public class Move implements Comparable<Move>{
    private String name;
    public int compareTo(Move mTest) {
        if (mTest.getName().compareTo(name)==0) return 0;
        else return 1;
    }

    public String getName() {
        return name;
    }
}
