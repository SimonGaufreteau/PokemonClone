package pokemonGameUtiles;

import entities.Pokemon;

public interface BufferMove {
    //Returns if the move is effective on each target or not
    //See AttackMove class for further explanations.

    String buff(Pokemon attacker, Pokemon[] targets, Move m);

}
