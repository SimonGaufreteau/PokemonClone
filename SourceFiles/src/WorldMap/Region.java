package WorldMap;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class Region {
    String name;
    Mapper mapper;
    String type;
    /*Building the region from a file.
    File template :

    Type : Route
    Name : Route 1
    MapFile : MapfileRoute1.txt
     */
    public Region(String regionFile) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
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
                    this.mapper = new Mapper(splitted[1]);
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
        this.mapper =null;
    }


    public String getName() {
        return this.name;
    }
    public String getType() {
        return this.type;
    }
}
