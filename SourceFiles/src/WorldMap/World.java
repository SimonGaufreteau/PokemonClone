package WorldMap;

import java.util.ArrayList;

/*
Class used to generate and manipulate a World, made of regions.
 */

public class World {
    private ArrayList<Region> regions;

    public World(ArrayList<Region> regions) {
        this.regions = regions;
    }

    /*Creates a world based on a file.
    File template : Matrix of files containing RegionPart (see corresponding class).
    Matrix given by integers, each integer corresponding to the file name.
    For each RegionPart, its neighbors are given with the following template :
    [Up,Right,Down,Left]
    (0 means none)
    File example :
    1 : 1,2,3,4
    2 :

    1 : "RegionPart1.txt"
    2 : "RegionPart2.txt"
    3 : "RegionPart3.txt"



     */
    public World(String fileName){

    }



    public ArrayList<Region> getRegions() {
        return regions;
    }

}
