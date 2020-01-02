package pokemonGameUtiles;

import entities.Pokemon;
import entities.PokemonBase;
import exceptions.PokemonNotFoundException;

public class Pokedex {
    private Pokemon[] pokemons;
    private int nbPokemon;

    /*
    A Pokedex is a list of Pokemons, which are initially consigned in a series of files in a specific directory (given in argument to the constructor).
    Every Pokemon created in the game will be a "clone" of these "base" Pokemons. (see " " method below)


     */
    public Pokedex(String directoryName) {

    }

    public PokemonBase getPokemonByID(int id) throws PokemonNotFoundException {
        if (id==0) throw new PokemonNotFoundException(id);
        return null;
    }
}
