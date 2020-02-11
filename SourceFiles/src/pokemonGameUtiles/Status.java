package pokemonGameUtiles;

public class Status {
    private String name;

    public Status(){
        this.name="None";
    }
    public Status(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }
}
