package pokemonGameUtiles;

import entities.Pokemon;

public interface DebufferMove {
    //Returns if the move is effective on each target or not
    //See AttackMove class for further explanations.
    String debuff(Pokemon attacker, Pokemon[] targets, Move m);

}
