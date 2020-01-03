package entities;

import exceptions.PokemonNotFoundException;
import items.Item;
import pokemonGameUtiles.Ability;
import pokemonGameUtiles.Move;
import pokemonGameUtiles.Pokedex;
import pokemonGameUtiles.Stats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class Pokemon extends PokemonAbstract {
    private Stats IV;
    private int level;
    private String nature;
    private String gender;
    private Ability ability;
    private Item holdItem;
    private Move[] moves;
    private PokemonBase pokemonBase;
    private static final String[] natures = {"Hardy","Lonely","Brave","Adamant","Naughty","Bold","Docile","Relaxed","Impish","Lax","Timid","Hasty","Serious","Jolly","Naive","Modest","Mild","Quiet","Bashful","Rash","Calm","Gentle","Sassy","Careful","Quirky"};


    public Pokemon(int id, Pokedex pokedex) throws PokemonNotFoundException {
        this(id,1 + (new Random()).nextInt(101),pokedex);

    }

    /*
    Gets moves to build the Pokemon, starts with the moves at the Pokemon's level and decreases until level 1
     */
    public Pokemon(int id, int level, Pokedex pokedex) throws PokemonNotFoundException {
        this(id,level,null,pokedex);
        //Taking moves from the most recent ones in the learning list
        Map<Integer, ArrayList<Move>> lvlLearnset = pokemonBase.getLvlLearnset();
        int lvl = this.level;
        moves = new Move[4];
        // ---------- TO BE CODED : ADDING MOVES ACCORDING TO THE POKEMON'S LEVEL ----------
        /*
        while(moves.length<4 && lvl>0){
            ArrayList<Move> moveList = lvlLearnset.get(lvl);

            //If there are moves for the current level try to add them, if not possible decrease the level
            if (moveList!=null){
                boolean moveLearnt=false;
                for (Move m:moveList) {
                    if(!isMoveKnown(m)) {
                        moves[moves.length-1]=m;
                        moveLearnt=true;
                    }
                }
                //If all moves were learnt at this level, decrease the level by one to check for other moves
                if (!moveLearnt) lvl--;
            }
            //If no move at this level, decrease the level by one
            else lvl--;
        }*/
    }

    public Pokemon(int id, int level, Move[] moves, Pokedex pokedex) throws PokemonNotFoundException {
        this(id,level,moves,null,pokedex);
    }

    /*
    Creates a Pokemon from a PokemonBase (see class) in the Pokedex with a level, moves and item given
     */
    public Pokemon(int id, int level, Move[] moves, Item item, Pokedex pokedex) throws PokemonNotFoundException {
        //Setting default values
        pokemonBase = pokedex.getPokemonByID(id);
        pokedexID = pokemonBase.getPokedexID();
        name=pokemonBase.getName();
        types=pokemonBase.getTypes();
        EV=pokemonBase.getEV();
        IV = null;
        description=pokemonBase.getDescription();
        weight=pokemonBase.getWeight();
        height=pokemonBase.getHeight();
        catchRate=pokemonBase.getCatchRate();
        baseExp=pokemonBase.getBaseExp();
        lvlRate=pokemonBase.getLvlRate();
        evolutionID=pokemonBase.getEvolutionID();
        stats = pokemonBase.getStats();

        holdItem=item;
        this.moves=moves;
        this.level = level;
        Random r = new Random();
        //A nature is randomly generated
        nature = natures[r.nextInt(25)];

        //A gender is randomly generated according to the gender (male) rate in the base
        double value = r.nextDouble();
        if ((int)(value*100)<pokemonBase.getMaleRate()) gender="Male";
        else gender = "Female";

        //An ability is randomly picked (if there is more than one)
        Ability[] abilities = pokemonBase.getAbilities();
        if (abilities.length<2) ability=abilities[0];
        else ability= abilities[r.nextInt(abilities.length)];
    }



    public boolean isMoveKnown(Move mTest){
        boolean isKnown = false;
        for (Move m :moves) {
            if (m.compareTo(mTest) == 0) {
                isKnown = true;
                break;
            }
        }
        return isKnown;
    }

    public Stats getIV() {
        return IV;
    }

    @Override
    public String toString() {
        String superString =super.toString().replaceAll("[}]$", "").replace("PokemonAbstract{","Pokemon{");
        String s="IV=" + IV +'\n' +
                "level=" + level +'\n' +
                "nature=" + nature + '\n' +
                "gender=" + gender + '\n' +
                "ability=" + ability +'\n' +
                "holdItem=" + holdItem +'\n' +
                "moves=" + Arrays.toString(moves) +'\n' +
                //"pokemonBase=" + pokemonBase +'\n' +
                '}';
        return superString+s;
    }
}
