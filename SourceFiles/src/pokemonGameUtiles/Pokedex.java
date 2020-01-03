package pokemonGameUtiles;

import entities.Pokemon;
import entities.PokemonBase;
import exceptions.AbilityNotFoundException;
import exceptions.FileFormatException;
import exceptions.MoveNotFoundException;
import exceptions.PokemonNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Pokedex {
    private PokemonBase[] pokemons;

    /*
    A Pokedex is a list of Pokemons (PokemonBase), which are initially consigned in a series of files in a specific directory (given in argument to the constructor).
    Every Pokemon created in the game will be a "clone" of these "base" Pokemons. (see " " method below)

    directoryName must be the name of the directory under "SourceFiles"
     */
    public Pokedex(String PokemonDirectoryName,AbilityDict abilityDict, MoveDict moveDict) throws FileFormatException, PokemonNotFoundException, MoveNotFoundException, AbilityNotFoundException, IOException {
        File folder = new File(System.getProperty("user.dir")+"\\SourceFiles\\"+PokemonDirectoryName);
        File[] listOfFiles = folder.listFiles();

        ArrayList<PokemonBase> pokemonBases = new ArrayList<>();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                String fileName = file.getName();
                //Checking if the file is a .pkm
                String[] fNameCropped = fileName.split("\\.");
                if (fNameCropped.length==2 && file.isFile() && (fNameCropped)[1].compareTo("pkm")==0) {
                    //For each file, adding the PokemonBase to the Pokedex
                    pokemonBases.add(new PokemonBase(file,abilityDict,moveDict));
                }
            }
        }

        //Checking if the Pokemon really is in the Pokedex, throws PokemonNotFoundException if not
        for (PokemonBase pkm:pokemonBases) {
            getPokemonByID(pkm.getEvolutionID()); //throws Exception if pokemon not found
        }
        pokemons= new PokemonBase[pokemonBases.size()];
        pokemons= pokemonBases.toArray(pokemons);
    }

    public PokemonBase getPokemonByID(int id) throws PokemonNotFoundException {
        if (id==0) return null;

        for (PokemonBase pkm:pokemons) {
            if (pkm.getPokedexID()==id) return pkm;
        }
        throw new PokemonNotFoundException(id);
    }

    @Override
    public String toString() {
        return "Pokedex{" +
                "pokemons=" + Arrays.toString(pokemons) +
                '}';
    }
}
