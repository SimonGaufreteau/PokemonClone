package worldMap;


import exceptions.FileFormatException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Mapper {
   private ArrayList<ArrayList<Square>> squares;

   /*
   A mapFile is a matrix of squares. Each Square has a FrontObject (eg : grass,wall,fence,...)
   and a BackgroundObject (eg : ground, water).
   Special FrontObjects and BackgroundObjects has been defined and extends the Front or Background object class.
   Each map possesses 2 inner maps. The BackgroundMap and the FrontMap, representing their respective objects.
   These 2 maps are defined in one file, used in the constructor as mapFile.

   Each object (background and front) in each map has an id.
   To every id corresponds the type of the object. A list defining id -> objects is defined at the beginning of the file.
   Note : ids are defined as a 3 digit number. 000 is left as the null object.

        -- Example of a BackgroundObject : --
   001 = {SpecialObject : true , Name : Ground , DisplayType : String , Display : "G"}

        -- Description --
   SpecialObject : boolean --> Is the object Special (i.e. defined as a Particular Class)
   Class : String --> Name of the Class if special.
   Name : String --> The name of the object (eg : road1, road2, caveGroundPath, ...)
   DisplayType : String --> "String"/"Int"/"Image" accepted type. Defines how the object is supposed to be displayed.
   Display : String --> Defines the command-line display or the file corresponding to the image.



         -- Example of a FrontObject : --
   001 = {SpecialObject : true , Class : SmallGrass , Name : SmallGrass1 , DisplayType : String , Display : "G"}

        -- Description : --
   By default a FrontObject will be a SolidObject only (eg : wall, fence).
   If it's not, it needs to be declared as a SpecialObject.
   Besides this, the description of a FrontObject is the same as a BackgroundObject.
   Note : There is no importance for the Display to be different between Front and Background objects.
          On command-line display, if both of them are String, the program will just concatenate Back and Front String.
          For a null Object, the default Display will be a N and the default id 000. This one cannot be used by any other FrontObject.


    ########## IMPORTANT NOTE ##########
    Ids can be imported from on or more files.
    To do this, the first lines must be of the type :
    "import file1.txt
     import file2.txt
     import file3.txt
    "

    Any pre-defined id will be erased by a new definition.
    Only the ids will be taken on the file importation, anything else written in will not be read.
    If the syntax is not respected, the import will stop and an exception will be raised.

        -- Example of a mapFile : --

   BackgroundMapId :
   001 = {SpecialObject : false , Name : Ground1 , DisplayType : String , Display : "G"}
   002 = {SpecialObject : false , Name : Road1 , DisplayType : String , Display : "R1"}
   003 = {SpecialObject : false , Name : Road2 , DisplayType : String , Display : "R2"}
   004 = {SpecialObject : true , Class : worldMap.objects.Water , Name : Water1 , DisplayType : String , Display : "~"}

   FrontMapId :
   001 = {SpecialObject : true , Class : SmallGrass , Name : SmallGrass1 , DisplayType : String , Display : "G"}
   002 = {SpecialObject : false , Name : Fence1 , DisplayType : String , Display : "F1"}


   BackgroundMap :
   004 004 003 001
   004 004 002 001
   001 002 002 001
   002 002 001 001

   FrontMap :
   000 000 000 001
   000 000 000 001
   000 000 000 001
   000 002 002 001

   */
    public Mapper(String mapFile) throws IOException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, FileFormatException {
        File file = new File("D:\\Documents\\Programmation\\Pokemon_Replique_Files(Java)\\PokemonClone\\SourceFiles\\Maps\\"+mapFile);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String[] st= br.readLine().split(" ");
        Map<String, Map<String,String>> multiMapBackground = new HashMap<>();
        Map<String, Map<String,String>> multiMapFront = new HashMap<>();

        BufferedReader br1; //Used to read each imported file
        while (st[0].compareTo("import")==0){
           br1 = new BufferedReader(new FileReader(new File(st[1])));
           readID(multiMapBackground, br1);
           readID(multiMapBackground, br1);
        }
        //Reading the Ids of the file given (after the import)
        readID(multiMapBackground,br);
        readID(multiMapFront,br);

        String line;
        line=br.readLine();
        //Reading until we get the first map (which must be BackGroundMap
        while (line!=null && line.compareTo("BackgroundMap :")!=0){
            line=br.readLine();
        }
        //Reading one more line to get started
        line=br.readLine();
        //Now that we have the Map connecting ID to their characteristics, we read the map and create corresponding Square
        //Using the multimap we just created, we instantiate each square with their Background Object first. Front comes later
        this.squares= new ArrayList<>();
        ArrayList<Square> squareLine;

        //Counting the elements to be sure both maps are of the same size
        //Note : The first line determines the number of elements on every line
        boolean firstLine=true;
        int lineSizeBGMap=0;
        int lineAmountBGMap=0;
        int lineSizeFMap=0;
        int lineAmountFMap=0;

        //Counting elements on each line
        int elementsCounter =0;


        //Reading the Map while we don't get any blank line
        while(line!=null && line.compareTo("")!=0){
            //Getting all the ids of the line
            String[] splitID = line.split(" ");
            squareLine = new ArrayList<>();
            for (String id:splitID) {
                squareLine.add(new Square(multiMapBackground.get(id)));

                if(firstLine) lineSizeBGMap++;
                elementsCounter++;
            }
            //If not the right amount of elements on a line, throw Exception
            if (elementsCounter!=lineSizeBGMap) throw new FileFormatException(mapFile);

            elementsCounter=0;
            firstLine = false;
            squares.add(squareLine);
            line=br.readLine();
            lineAmountBGMap++;
        }

        firstLine=true;
        //Doing the same thing with the setter for FrontObject
        while (line!=null && line.compareTo("FrontMap :")!=0){
            line=br.readLine();
        }
        //Reading one more line to get started
        line = br.readLine();
        Square s;
        ArrayList<Square> lineFront;
        while(line!=null && line.compareTo("")!=0){
            //Getting all the ids of the line
            String[] splitID = line.split(" ");
            for (int i = 0; i < splitID.length; i++) {
                String id = splitID[i];
                //Getting the square array line
                lineFront = squares.get(lineAmountFMap);
                //Getting the square at the position we are interested in
                s = lineFront.get(i);
                //Setting the Front object of the square
                s.setFrontObject(multiMapFront.get(id));
                //Modifying the line with the new object
                lineFront.set(i,s);
                squares.set(lineAmountFMap,lineFront);

                if (firstLine) lineSizeFMap++;
                elementsCounter++;
            }

            //If not the right amount of elements on a line, throw Exception
            if (elementsCounter!=lineSizeFMap) throw new FileFormatException(mapFile);

            elementsCounter=0;
            firstLine=false;
            //Reading a new line and counting
            line=br.readLine();
            lineAmountFMap++;
        }

        //All squares have been set, checking the size and end of the constructor.
        if (lineAmountBGMap!=lineAmountFMap || lineSizeBGMap!=lineSizeFMap) throw new FileFormatException(mapFile);
    }




    private void readID(Map<String, Map<String, String>> multiMap, BufferedReader br) throws IOException {
        String st= br.readLine();
        while(st.compareTo("")==0 ||st.compareTo("FrontMapId :")==0||st.compareTo("BackgroundMapId :")==0){
            st = br.readLine();
        }

        //if (st.compareTo("BackgroundMapId :") == 0 || st.compareTo("FrontMapId :")==0) {
            while (st != null && st.compareTo("") != 0) {
                //Here we should have a line of the following type :
                //004 = {SpecialObject : true , Class : worldMap.objects.Water , Name : Water1 , DisplayType : String , Display : "~"}
                //We try to get : mainKey ="004" and mainValue a Map of the following elements in brackets

                String[] splitter = st.split(" = ");
                String mainKey = splitter[0];
                //Replace { by nothing and split to get the keys and values.
                String[] values = splitter[1].replaceAll("[{}\"]", "").split(" , ");

                //Creating a new "Dictionary" : Map<String,String>
                Map<String, String> mainValue = new HashMap<>();
                int i = 0;
                String value;
                String key;
                //Putting all key/value couples into the Map
                while (i< values.length) {
                    String[] keyValue = values[i].split(" : ");
                    key = keyValue[0];
                    value = keyValue[1];
                    mainValue.put(key, value);
                    i++;
                }

                //Putting the Map into the MultiMap
                multiMap.put(mainKey, mainValue);

                //Reading next ID
                st = br.readLine();
            }
        //}
    }

    /*public ArrayList<ArrayList<Square>> getSquares() {
        return squares;
    }*/

    public String toString(){
        StringBuilder frontMapString= new StringBuilder();
        StringBuilder backgroundMapString= new StringBuilder();
        for (ArrayList<Square> line:squares) {
            for (Square s : line) {
                backgroundMapString.append(s.getBackgroundObject().toString()).append(" | ");
                frontMapString.append(s.getFrontObject().toString()).append(" | ");
            }
            backgroundMapString.append("\n");
            frontMapString.append("\n");
        }
        return "FrontMap :\n"+frontMapString+"\nBackgroundMap :\n"+backgroundMapString;
    }
}

