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
    private Stats EV;
    private int level;
    private String nature;
    private String gender;
    private Ability ability;
    private Item holdItem;
    private Move[] moves;
    private PokemonBase pokemonBase;
    private static final String[] natures = {"Hardy","Lonely","Brave","Adamant","Naughty","Bold","Docile","Relaxed","Impish","Lax","Timid","Hasty","Serious","Jolly","Naive","Modest","Mild","Quiet","Bashful","Rash","Calm","Gentle","Sassy","Careful","Quirky"};
    //Representing the effected stats (for each nature) : + / -
    private static final String[][] effectedStats = {{},{"atk","def"},{"atk","spd"},{"atk","spatk"},{"atk","spdef"},{"def","spd"},{},{"def","spd"},{"def","spatk"},{"def","spdef"},{"spd","atk"},{"spd","def"},{},{"spd","spatk"},{"spd","spdef"} ,{"spatk","atk"},{"spatk","def"},{"spatk","spd"},{},{"spatk","spdef"},{"spdef","atk"},{"spdef","def"},{"spdef","spd"},{"spdef","spatk"},{}};


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
        int countMoves = 0;
        while(countMoves<4 && lvl>0){
            ArrayList<Move> moveList = lvlLearnset.get(lvl);

            //If there are moves for the current level try to add them, if not possible decrease the level
            if (moveList!=null){
                boolean moveLearnt=false;
                for (Move m:moveList) {
                    if (countMoves >= 4) continue;
                    if (!isMoveKnown(m)) {
                        moves[countMoves] = m;
                        countMoves++;
                        moveLearnt = true;
                    }
                }
                //If all moves were learnt at this level (none where learnt at this iteration), decrease the level by one to check for other moves
                if (!moveLearnt) lvl--;
            }
            //If no move at this level, decrease the level by one
            else lvl--;
        }
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
        EVGiven =pokemonBase.getEVGiven();
        IV = null;
        EV = new Stats();
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



        //A nature is randomly generated
        Random r = new Random();
        nature = natures[r.nextInt(natures.length)];

        //A gender is randomly generated according to the gender (male) rate in the base
        double value = r.nextDouble();
        if ((int)(value*100)<pokemonBase.getMaleRate()) gender="Male";
        else gender = "Female";

        //An ability is randomly picked (if there is more than one)
        Ability[] abilities = pokemonBase.getAbilities();
        if (abilities.length<2) ability=abilities[0];
        else ability= abilities[r.nextInt(abilities.length)];

        //IVs are generated : https://bulbapedia.bulbagarden.net/wiki/Individual_values#Generation_III_onward
        //Generating a number between 0 and 31 for each stat.
        IV=new Stats(r.nextInt(32),r.nextInt(32),r.nextInt(32),r.nextInt(32),r.nextInt(32),r.nextInt(32));

        //Generating the stats following the last link.
        generateStats();

    }

    private void generateStats(){
        int tempHP = ((2*stats.getHp()+IV.getHp()+EVGiven.getHp()/4)*level)/100 + level+10;
        stats.setHp(tempHP);

        String[] values=null;
        //Getting the stats affected by the nature
        for (int i = 0; i < natures.length; i++) {
            String nature = natures[i];
            if (this.nature.equals(nature)) {
                values = effectedStats[i];
                break;
            }
        }
        stats.setAtk(calculateStat(stats.getAtk(),IV.getAtk(), EV.getAtk(),"atk",values));
        stats.setDef(calculateStat(stats.getDef(),IV.getDef(), EV.getDef(),"def",values));
        stats.setSpAtk(calculateStat(stats.getSpAtk(),IV.getSpAtk(), EV.getSpAtk(),"spatk",values));
        stats.setSpDef(calculateStat(stats.getSpDef(),IV.getSpDef(), EV.getSpDef(),"spdef",values));
        stats.setSpd(calculateStat(stats.getSpd(),IV.getSpd(), EV.getSpd(),"spd",values));

    }

    //Calculating a stat of a Pokemon from its baseStats, IV,EV,level and nature.
    private int calculateStat(int statBase,int statIV,int statEV,String statCalc,String[] effectedStats){
        int preCalculation = (((2*statBase+statIV+statEV/4)*level)/100) +5;
        double natureCoef = 1.;

        if (effectedStats != null && effectedStats.length == 2) {
            if (statCalc.equals(effectedStats[0])) natureCoef = 1.1;
            else if (statCalc.equals(effectedStats[1])) natureCoef = 0.9;
        }
        return (int)(preCalculation*natureCoef);
    }

    public boolean isMoveKnown(Move mTest){
        for (Move m :moves) {
            if (m!=null && m.compareTo(mTest) == 0)  return true;
        }
        return false;
    }

    public Stats getIV() {
        return IV;
    }

    public Stats getEV() {
        return EV;
    }

    public Move[] getMoves() {
        return moves;
    }

    @Override
    public String toString() {
        String superString = super.toString().replaceAll("[}]$", "").replace("PokemonAbstract{", "Pokemon{");
        String s = "IV=" + IV + '\n' +
                "level=" + level + '\n' +
                "nature=" + nature + '\n' +
                "gender=" + gender + '\n' +
                "ability=" + ability + '\n' +
                "holdItem=" + holdItem + '\n' +
                "moves=" + Arrays.toString(moves) + '\n' +
                //"pokemonBase=" + pokemonBase +'\n' +
                '}';
        return superString + s;
    }
}
