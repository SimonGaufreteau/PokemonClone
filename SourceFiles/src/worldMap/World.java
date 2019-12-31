package worldMap;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/*
Class used to generate and manipulate a World, made of regions.
 */

public class World {
    //Using ArrayList to facilitate the modification of the map (May be modified in a future version if not used)
    private ArrayList<ArrayList<Region>> regions;

    /*Creates a world based on a file.
    File template : Matrix of files containing RegionMaps (see corresponding class).
    Matrix given by integers, each integer corresponding to the file name.
    (0 means none)
    File example :

    Files :
    1:RegionMap1.txt
    2:RegionMap2.txt
    3:RegionMap3.txt
    ...
    Matrix :
    0 2 0
    5 1 3
    0 4 0


    The attribute regions, will be composed of lines containing the different Regions loaded with the files.

    Note : All Regions may not be of the same size.
     Each time the player crosses the limit of one border, the next Region will be loaded.
     */
    public World(String fileName) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        File file = new File(fileName);

        BufferedReader br = new BufferedReader(new FileReader(file));

        //Adding all the file names to a list.
        ArrayList<String> fileNames = new ArrayList<>();

        String st = br.readLine();
        if (st.compareTo("Files :") == 0) {
            //While we are reading files, put them into the list. They will be opened later
            st = br.readLine();
            while (st.compareTo("") != 0) {
                fileNames.add(st.split(" : ")[1]); //Taking the part after the ":", which is the fileName
                st = br.readLine();
            }
        }

        ArrayList<Region> regionLine;
        String[] indexes;
        regions = new ArrayList<>();
        br.readLine(); //Reading the "Matrix :" line
        String filePath;
        while ((st = br.readLine()) != null) {
            //Each time we have a line in the matrix, we add a line in "regions". For each int in the line, we create a new Region and add it to the line
            regionLine = new ArrayList<>();
            indexes = st.split(" ");

            for (String index : indexes) {
                //Getting the fileName out of the fileNames list.
                //Only if the index!=0
                if (Integer.parseInt(index)!=0){
                    filePath =System.getProperty("user.dir")+ "\\SourceFiles\\RegionMaps\\"+fileNames.get(Integer.parseInt(index)-1);
                    String regionFile = filePath;
                    //Creation and adding the region to the regionLine
                    regionLine.add(new Region(regionFile));
                }
                else regionLine.add(new Region());


            }
            //Adding the line to the regions.
            this.regions.add(regionLine);
        }
    }


    public String toDetailedMap(){
        StringBuilder s = new StringBuilder();
        for (ArrayList<Region> line:regions){
            for (Region r:line) {
                s.append(r.toString());
            }
        }
        return s.toString();
    }

    public String toString(){
        StringBuilder s = new StringBuilder("World :\n");
        for (ArrayList<Region> line:regions) {
            for (Region r: line) {
                s.append(r.getName()).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}