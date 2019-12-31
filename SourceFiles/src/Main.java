import WorldMap.*;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main (String[] args){
        try{
            World w1 = new World("D:\\Documents\\Programmation\\Pokemon_Replique_Files(Java)\\PokemonClone\\SourceFiles\\RegionMaps\\WorldMapTest.txt");
            System.out.println(w1);
            System.out.println(w1.toDetailledMap());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
