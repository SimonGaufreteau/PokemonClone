package pokemonGameUtiles;

import entities.Pokemon;
import entities.Trainer;
import exceptions.PokemonNotFoundException;

import java.util.Random;

public class WildBattle extends Battle {
    Trainer firstTrainer;
    Pokemon wildPokemon;
    int turnPassed; //Number of turn already passed

    /*
    Creates a WildBattle with a Trainer and Pokemon
     */
    public WildBattle(Trainer t, Pokemon p){
        this.firstTrainer=t;
        this.wildPokemon=p;
    }

    /*
    Creates a WildBattle with a Pokemon ID
     */
    public WildBattle(Trainer t, int pokeID,Pokedex pokedex) throws PokemonNotFoundException {
        this(t,new Pokemon(pokeID,pokedex));
    }

    /*
    Creates a WildBattle object with a random wild Pokemon from an id list
     */
    public WildBattle(Trainer t,int[] pokeIDs,Pokedex pokedex) throws PokemonNotFoundException {
        this(t,null);
        int id = new Random().nextInt(pokeIDs.length);
        this.wildPokemon = new Pokemon(id,pokedex);
    }



}
