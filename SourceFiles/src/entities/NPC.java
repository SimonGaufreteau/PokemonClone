package entities;

public class NPC {
    private String name;
    private int id;
    private static int countNPC=0;

    public NPC(){
        this("");
    }

    public NPC(String name){
        this.name=name;
        this.id = countNPC++;
    }
}
