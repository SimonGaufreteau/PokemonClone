package entities;

import pokemonGameUtiles.Stats;

public abstract class PokemonAbstract {
    /*
    Defines a non-instantiated Pokemon. The base of the Pokemon is contained in the PokemonBase class,
    as the instantiated Pokemon will be contained in the Pokemon class.
     */
    protected int pokedexID;
    protected String[] types =new String[2]; //2 types max, 1 type min (defined in constructor)
    protected String name;
    protected Stats EV;
    protected Stats IV;
    protected Stats stats;
    protected String description;
    protected float weight;
    protected float height;
    protected int catchRate;
    protected int baseExp;
    protected String lvlRate;
    protected int evolutionID;

    public int getPokedexID() {
        return pokedexID;
    }

    public String[] getTypes() {
        return types;
    }

    public String getName() {
        return name;
    }

    public Stats getEV() {
        return EV;
    }

    public Stats getIV() {
        return IV;
    }

    public Stats getStats() {
        return stats;
    }

    public String getDescription() {
        return description;
    }

    public float getWeight() {
        return weight;
    }

    public float getHeight() {
        return height;
    }

    public int getCatchRate() {
        return catchRate;
    }

    public int getBaseExp() {
        return baseExp;
    }

    public String getLvlRate() {
        return lvlRate;
    }

    public int getEvolutionID() {
        return evolutionID;
    }
}
