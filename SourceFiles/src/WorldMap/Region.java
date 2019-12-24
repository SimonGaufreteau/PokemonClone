package WorldMap;

import java.io.*;

public class Region {
    String name;
    Map map;
    String type;
    /*Building the region from a file.
    File template :

    Type : Route
    Name : Route 1
    MapFile : MapfileRoute1.txt
     */
    public Region(String regionFile) throws IOException {
        File file = new File(regionFile);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null){
            String[] splitted = st.split(" : ");

            //Not verifying Names and types for now. Mapfiles are checked in the Map class.

            switch (splitted[0]){
                case "Type":
                    this.type = splitted[1];
                    break;
                case "Name":
                    this.name = splitted[1];
                    break;
                case "MapFile" :
                    this.map= new Map(splitted[1]);
                    break;
                default :
                    System.out.println("Unrecognized line in Region File : "+st);
            }
        }

    }
    //Create a "null" region
    public Region() {
        this.type="N";
        this.name="N";
        this.map=null;
    }


    public String getName() {
        return this.name;
    }
}
