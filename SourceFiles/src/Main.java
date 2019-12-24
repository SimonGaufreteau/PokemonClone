import WorldMap.*;

import java.io.IOException;

public class Main {
    public static void main (String[] args){
        try{
            World w1 = new World("D:\\Documents\\Programmation\\Pokemon_Replique_Files(Java)\\PokemonClone\\SourceFiles\\Maps\\WorldMap.txt");
            System.out.println(w1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
