package worldMap.objects;

public class FrontObject {
    private final String displayString;
    private final String name;

    public FrontObject(String name, String display) {
        this.displayString=display;
        this.name=name;
    }

    //Creating a blank FrontObject (" " as a DisplayString)
    public FrontObject() {
        this("null"," ");
    }

    public String toString(){
        return displayString;
    }
}
