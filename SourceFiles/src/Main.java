import entities.Pokemon;
import pokemonGameUtiles.AbilityDict;
import pokemonGameUtiles.Move;
import pokemonGameUtiles.MoveDict;
import pokemonGameUtiles.Pokedex;
import worldMap.*;

import java.io.File;

public class Main {
    public static void main (String[] args){
        /*
        //Tests for World Class
        try{
            World w1 = new World("D:\\Documents\\Programmation\\Pokemon_Replique_Files(Java)\\PokemonClone\\SourceFiles\\RegionMaps\\WorldMapTest.txt");
            System.out.println(w1);
            System.out.println(w1.toDetailedMap());

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try{
            AbilityDict abilityDict = new AbilityDict("Abilities");
            //System.out.println(abilityDict);

            MoveDict moveDict = new MoveDict("Moves\\Movelist.txt");
            //System.out.println(moveDict.getByID(18));

            Pokedex pokedex = new Pokedex("Pokemons",abilityDict,moveDict);
            //System.out.println(pokedex);

            Pokemon marill = new Pokemon(183,pokedex);
            System.out.println(marill);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
