package pokemonGameUtiles;

import exceptions.AbilityNotFoundException;

public class AbilityDict {
    Ability[] abilities;

    public Ability getByName(String name) throws AbilityNotFoundException {
        if (name==null)throw new AbilityNotFoundException(name);

        return null;
    }
}
