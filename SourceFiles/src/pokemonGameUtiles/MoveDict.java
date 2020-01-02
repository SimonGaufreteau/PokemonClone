package pokemonGameUtiles;

import exceptions.MoveNotFoundException;

public class MoveDict {
    Move[] moves;

    public Move getByName(String name) throws MoveNotFoundException {
        if (name==null)throw new MoveNotFoundException(name);
        return null;
    }
}
