package entities;

import exceptions.*;
import pokemonGameUtiles.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PokemonBase  extends PokemonAbstract {
    //The "stat" attribute is the Base Stats here
    private int maleRate;
    private Ability[] abilities;
    private Map<Integer,ArrayList<Move>> lvlLearnset;
    private ArrayList<Move> HMTMLearnset;

    /*
    Creates a PokemonBase from a file. (See "Pokemons" directory for examples)
     */
    public PokemonBase(File file, AbilityDict abilityDict,MoveDict moveDict) throws IOException, FileFormatException, AbilityNotFoundException, MoveNotFoundException, PokemonNotFoundException {
        String fileName=file.getName();
        BufferedReader br = new BufferedReader(new FileReader(file));

        //Reading all the lines and initializing attributes. If the file is not written correctly, an exception will be thrown
        String line = br.readLine();
        String[] st;
        while (line != null) {

            //If reading a blank line, pass it
           if (line.compareTo("") != 0) {
               st = line.split(" : ");
            /*If a definition happens twice in a file, the second one will be taken as the "right" one
            Each line has a "string1 : something", string1 describing the attribute to be initialized
           */
               switch (st[0].toLowerCase()) {
                   case "id":
                       if (st[1] == null) throw new FileFormatException(fileName);
                       pokedexID = Integer.parseInt(st[1]);
                       break;
                   case "name":
                       if (st[1] == null) throw new FileFormatException(fileName);
                       name = st[1].replace("\"","");
                       break;
                   case "base stats :":
                       //Adding the stats
                       stats = readStats(br, fileName);
                       break;
                   case "ev :":
                       EVGiven = readStats(br, fileName);
                       break;
                   case "types":
                       if (st[1] == null) throw new FileFormatException(fileName);
                       String[] types = st[1].split(" / ");
                       if (types[1] == null) throw new FileFormatException(fileName);
                       this.types = types;
                       break;
                   case "desc":
                       if (st[1] == null) throw new FileFormatException(fileName);
                       description = st[1].replace("\"", "");
                       break;
                   case "weight":
                       if (st[1] == null) throw new FileFormatException(fileName);
                       weight = Float.parseFloat(st[1]);
                       break;
                   case "height":
                       if (st[1] == null) throw new FileFormatException(fileName);
                       height = Float.parseFloat(st[1]);
                       break;
                   case "catchrate":
                       if (st[1] == null) throw new FileFormatException(fileName);
                       catchRate = Integer.parseInt(st[1]);
                       break;
                   case "baseexp":
                       if (st[1] == null) throw new FileFormatException(fileName);
                       baseExp = Integer.parseInt(st[1]);
                       break;
                   case "lvlrate":
                       if (st[1] == null) throw new FileFormatException(fileName);
                       if (st[1].compareTo("Fast") == 0 || st[1].compareTo("Medium fast") == 0 || st[1].compareTo("Medium slow") == 0 || st[1].compareTo("Slow") == 0) {
                           lvlRate = st[1];
                       } else throw new FileFormatException(fileName);
                       break;
                   case "gender ratio":
                       if (st[1] == null) throw new FileFormatException(fileName);
                       String[] ratio = st[1].split("/");
                       //Checking if we don't have a blank percentage
                       if (ratio[1] == null || ratio[0].compareTo("") == 0 || ratio[1].compareTo("") == 0)
                           throw new FileFormatException(fileName);
                       int ratioM = Integer.parseInt(ratio[0]);
                       int ratioF = Integer.parseInt(ratio[1]);

                       //Checking if sum=100
                       if (ratioF + ratioM != 100) throw new FileFormatException(fileName);
                       maleRate = ratioM;
                       break;
                   case "abilities":
                       String[] abilities = st[1].replaceAll("[\"]", "").split(" , ");
                       this.abilities = new Ability[abilities.length];
                       for (int i = 0; i < abilities.length; i++) {
                           String abilityString = abilities[i];
                           this.abilities[i] = abilityDict.getByName(abilityString);
                       }
                       break;
                   case "evolution id":
                       if (st[1] == null) throw new FileFormatException(fileName);
                       //Checking if the Pokemon really is in the Pokedex, throws PokemonNotFoundException if not --> Done in the Pokedex Class
                       evolutionID = Integer.parseInt(st[1]);
                       break;
                   case "lvl-up learnset :":

                       lvlLearnset = readLVLearnset(br, fileName, moveDict);
                       break;
                   case "hm-tm learnset :":
                       //Reading the next line to get the list
                       line = br.readLine();
                       HMTMLearnset = readHMTMLearnset(line, moveDict);
                       break;
               }
           }
            line=br.readLine();
        }//End of the file's reading, checking if every attributes has been initialized. If not, throw Exception
        if (types==null || name==null || EVGiven ==null || stats==null || description==null || weight==0.0f || height==0.0f || catchRate==0 || baseExp==0 || lvlRate==null){
            throw new FileFormatException(fileName);
        }

    }

    private ArrayList<Move> readHMTMLearnset(String line, MoveDict moveDict) throws  MoveNotFoundException {
        ArrayList<Move> moves = new ArrayList<>();
        String[] st = line.replaceAll("[{} ]","").split(",");

        // ---------- HM TM NOT CODED YET ----------
        //Getting every move in the list and adding it to "moves"
        /*for (String tm:st) {
            moves.add(moveDict.getByName(tm));
        }*/
        return moves;
    }

    private Map<Integer,ArrayList<Move>> readLVLearnset(BufferedReader br, String fileName, MoveDict moveDict) throws MoveNotFoundException, IOException, FileFormatException {
        Map<Integer,ArrayList<Move>> dictLVLMoves = new HashMap<>();
        String line = br.readLine();
        String[] st;
        Move move;
        int lvl;

        boolean beginReading = false;
        boolean continueReading = true;
        //Reading lines from '{' until we get a '}' at the end of the line
        while(line!=null && continueReading){
           if(line.charAt(0)=='{'){
               beginReading=true;
               line=line.replace("{","");
           }
            if (line.charAt(line.length()-1)=='}') {
                line=line.replace("}","");
                continueReading=false;
            }
           //Only reading if we saw a '{' at the beginning of a line
           if (beginReading){
               st=line.split(" : ");
               if (st[1]==null) throw new FileFormatException(fileName);
               move = moveDict.getByName(st[1]);
               lvl = Integer.parseInt(st[0]);
               if(dictLVLMoves.containsKey(lvl)){
                   //If the Map already contains moves for the given lvl, update the list of moves.
                   dictLVLMoves.get(lvl).add(move);
                   }
               else {
                   //Nothing at this lvl, adding the new level->[move] to the Map
                   ArrayList<Move> moveTemp = new ArrayList<>();
                   moveTemp.add(move);
                   dictLVLMoves.put(Integer.parseInt(st[0]),moveTemp);
               }

               //If we are at the end of the HMTM list, end the reading


           }//End of the if(beginReading)

           line = br.readLine();
        }
        return dictLVLMoves;
    }

    private Stats readStats(BufferedReader br, String fileName) throws IOException, FileFormatException {
        int hp = 0, atk = 0, def = 0, spAtk = 0, spDef = 0, spd = 0;
        for (int i = 0; i <= 5; i++) {
            String line = br.readLine();
            if (line == null) throw new FileFormatException(fileName);
            String[] st = line.split(" : ");
            if (st[1] == null) throw new FileFormatException(fileName);
            switch (st[0].toLowerCase()) {
                case "hp":
                    hp = Integer.parseInt(st[1]);
                    break;
                case "atk":
                    atk = Integer.parseInt(st[1]);
                    break;
                case "def":
                    def = Integer.parseInt(st[1]);
                    break;
                case "spatk":
                    spAtk = Integer.parseInt(st[1]);
                    break;
                case "spdef":
                    spDef = Integer.parseInt(st[1]);
                    break;
                case "spd":
                    spd = Integer.parseInt(st[1]);
                    break;
            }
        }
        return new Stats(hp,atk,def,spAtk,spDef,spd);
    }

    public int getMaleRate() {
        return maleRate;
    }

    public Ability[] getAbilities() {
        return abilities;
    }

    public Map<Integer, ArrayList<Move>> getLvlLearnset() {
        return lvlLearnset;
    }

    public ArrayList<Move> getHMTMLearnset() {
        return HMTMLearnset;
    }

    public String toString(){
        String s = super.toString();
        s=s.replace("}","");
        String thisString = "PokemonBase{" +
                "maleRate=" + maleRate +
                "abilities=" + Arrays.toString(abilities) +
                "lvlLearnset=" + lvlLearnset +
                "HMTMLearnset=" + HMTMLearnset +
                '}';
        return s+thisString;
    }
}


