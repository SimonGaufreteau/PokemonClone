package WorldMap.Objects;

import WorldMap.Objects.BackgroundObject;
import WorldMap.Objects.Surfable;

public class Water extends BackgroundObject implements Surfable {
    public Water(String name, String display) {
        super(name,display);
    }
}
