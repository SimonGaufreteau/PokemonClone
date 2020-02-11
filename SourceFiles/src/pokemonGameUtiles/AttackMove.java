package pokemonGameUtiles;

import entities.Pokemon;

public interface AttackMove {
    //Returns if the move is effective on each target or not
    //Example (for 1 target) : "T1 : It's super effective !"
    //Example (for 3 targets) : "T1 : It's super effective on {TargetName} ! , T2 : It's not very effective on {TargetName}... , T3 : {TargetName}
    String attack(Pokemon attacker, Pokemon[] targets, Move m);
}
