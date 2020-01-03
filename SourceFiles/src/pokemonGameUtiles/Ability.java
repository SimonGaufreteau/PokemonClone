package pokemonGameUtiles;

public class Ability {
    private int id;
    private String name;
    private String description;

    public Ability(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Ability{" + '\n' +
                "id=" + id + '\n' +
                "name=" + name + '\n' +
                "description=" + description + '\n' +
                '}';
    }
}
