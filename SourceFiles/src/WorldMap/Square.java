package WorldMap;

import WorldMap.Objects.BackgroundObject;
import WorldMap.Objects.FrontObject;
import WorldMap.Objects.SolidObject;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Square {
    private FrontObject frontObject;
    private BackgroundObject backgroundObject;


    //Used to initialize the object with a map describing a BackgroundObject
    //eg : {"SpecialObject" : "true" , "Class" : "Water" , "Name" : "Water1" , "DisplayType" : "String" , "Display" : "W"}
    public Square(Map<String, String> mapBG) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        //Checking if the object is "normal"
        if (mapBG.get("SpecialObject").compareTo("false")==0){
            this.backgroundObject=new BackgroundObject(mapBG.get("Name"),mapBG.get("Display"));
        }
        else { //Special case
            Class<?> c = Class.forName(mapBG.get("Class"));
            //Creating an object from the class given
            this.frontObject = (FrontObject) c.getConstructor(String.class,String.class).newInstance(mapBG.get("Name"),mapBG.get("Display"));
        }
        this.frontObject=null;
    }
    //Used to finalize the object with a map describing a FrontObject
    //By default : SolidObject
    //eg : 001 = {SpecialObject : true , Class : SmallGrass , Name : SmallGrass1 , DisplayType : String , Display : "G"}
    public void setFrontObject(Map<String, String> mapF) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (mapF.get("SpecialObject").compareTo("false")==0){
            this.frontObject=new SolidObject(mapF.get("Name"),mapF.get("Display"));
        }
        else { //Special case
            Class<?> c = Class.forName(mapF.get("Class"));
            this.frontObject = (FrontObject) c.getConstructor(String.class,String.class).newInstance(mapF.get("Name"),mapF.get("Display"));
        }
    }

    public FrontObject getFrontObject() {
        return frontObject;
    }

    public BackgroundObject getBackgroundObject() {
        return backgroundObject;
    }
}
