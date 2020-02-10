package entities;

import pokemonGameUtiles.Pokedex;

import java.util.ArrayList;
import java.util.Random;

public class Trainer extends NPC {
    private ArrayList<Pokemon> pokemons;


    public Trainer(String name, ArrayList<Pokemon> pokemons) {
        super("Trainer".concat(name));
        this.pokemons = pokemons;
    }

    public Trainer(String name, int[] idPokemons, Pokedex pokedex) throws Exception {
        if (idPokemons.length>6){
            throw new Exception("Too many ids.");
        }
        for (int id:idPokemons) {
            this.pokemons.add(new Pokemon(id,pokedex));
        }
    }

    public Trainer(String name){
        this(name,new ArrayList<>());
    }

    public Trainer(){
        this("");
    }


    public void addPokemon(Pokemon p){
        this.pokemons.add(p);
    }

    public ArrayList<Pokemon> getPokemons(){
        return this.pokemons;
    }

    public Pokemon getRandomPokemon(){
        Random r = new Random();
        return pokemons.get(r.nextInt(pokemons.size()));
    }
}
