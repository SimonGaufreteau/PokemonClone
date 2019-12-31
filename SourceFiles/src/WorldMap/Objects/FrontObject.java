package WorldMap.Objects;

public class FrontObject {
    private String displayString;
    private String name;

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
