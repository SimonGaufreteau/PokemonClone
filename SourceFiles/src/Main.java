import pokemonGameUtiles.AbilityDict;
import pokemonGameUtiles.MoveDict;
import pokemonGameUtiles.Pokedex;

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
            System.out.println(moveDict);

            Pokedex pokedex = new Pokedex("Pokemons", abilityDict, moveDict);
            //System.out.println(pokedex);

            /*Pokemon marill = new Pokemon(183, 20, pokedex);
            for (int i = 0; i <= 99; i++) {
                marill = new Pokemon(183, i, pokedex);
            }
            System.out.println(marill);*/
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
