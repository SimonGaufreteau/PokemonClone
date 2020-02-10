package pokemonGameUtiles;

import entities.Pokemon;
import entities.Trainer;
import exceptions.PokemonNotFoundException;
import items.Item;
import items.Usable;

import java.util.Random;

public class WildBattle extends Battle {
    Trainer firstTrainer;
    Pokemon trainerPokemon;
    Pokemon wildPokemon;
    private static final String notUsableMessage = "You cannot use this item in battle ! ";
    //int turnPassed; //Number of turn already passed

    /*
    Creates a WildBattle with a Trainer and Pokemon
     */
    public WildBattle(Trainer t, Pokemon p) {
        this.firstTrainer = t;
        this.wildPokemon = p;
        if (t.getPokemons().size() > 0)
            this.trainerPokemon = t.getPokemons().get(0);
    }

    /*
    Creates a WildBattle with a Pokemon ID
     */
    public WildBattle(Trainer t, int pokeID, Pokedex pokedex) throws PokemonNotFoundException {
        this(t, new Pokemon(pokeID, pokedex));
    }

    public WildBattle(Trainer t, int[] pokeIDs, Pokedex pokedex) throws PokemonNotFoundException {
    /*
    Creates a WildBattle object with a random wild Pokemon from an id list
     */
        this(t, null);
        int id = new Random().nextInt(pokeIDs.length);
        this.wildPokemon = new Pokemon(id, pokedex);

    }

    /*
        Plays a turn.
        Input : an Object. Can be a : Move,Item,Pokemon or "run"
    */
    public String playTurn(Item i) {
        if (i instanceof Usable) {
            ((Usable) i).use(trainerPokemon);
            return null;
        } else return notUsableMessage;
    }


    /*
        Move playturn.
        We assume that the number of PPLeft has already been checked.
        returns a String containing the effectiveness of the move on each targeted Pokemon.
     */
    public String playTurn(Move m, Pokemon[] targets) {
        //if (m.getPP()==0) return "This move can't be used !";
        StringBuilder returnString = new StringBuilder();
        //If the move is a buffer/debuffer move, apply the effect.
        if (m instanceof BufferMove) {
            returnString.append(((BufferMove) m).buff(trainerPokemon, targets, m)).append("\n");
        } else if (m instanceof DebufferMove) {
            returnString.append(((DebufferMove) m).debuff(trainerPokemon, targets, m)).append("\n");
        }

        //If the move is an attack move, use the attack
        //NOTE : A move can be both a buffer/debuffer and an attack move
        if (m instanceof AttackMove) {
            returnString.append(((AttackMove) m).attack(trainerPokemon, targets, m)).append("\n");
        }

        return returnString.toString();
    }


    //Input : index of the move in the actual pokemon moveset.
    public String playTurn(int intMove, Pokemon[] targets) throws Exception {
        Move[] moves = trainerPokemon.getMoves();
        if (moves[intMove] == null) {
            throw new Exception("Invalid index found while trying to play the move.");
        }
        return playTurn(trainerPokemon.getMoves()[intMove], targets);
    }

    /*
    Swaps a Pokemon with the actual one if possible.
     */
    public String changePokemon(Pokemon target) {
        if (canBeSwapped(trainerPokemon)) {
            trainerPokemon = target;
            return "Come back " + trainerPokemon.getName() + ". " + target.getName() + " go !";
        }
        return trainerPokemon.getName() + " cannot escape !";
    }

    /*
    Checks if the pokemon can be swapped or not
     */
    private boolean canBeSwapped(Pokemon p) {
        //TODO : check if the Pokemon can be swapped or not --> Check effects (not implemented yet)
        return true;
    }
}
