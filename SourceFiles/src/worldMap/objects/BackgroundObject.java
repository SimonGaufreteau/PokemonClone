package worldMap.objects;

public class BackgroundObject {
    private final String displayString;
    private final String name;

    public BackgroundObject(String name, String display) {
        this.displayString=display;
        this.name=name;
    }

    //Only String display for now.

    @Override
    public String toString() {
        return displayString;
    }
}
