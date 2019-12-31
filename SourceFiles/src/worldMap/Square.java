package worldMap;

import worldMap.objects.*;

import java.lang.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Square {
    private FrontObject frontObject;
    private BackgroundObject backgroundObject;


    //Used to initialize the object with a map describing a BackgroundObject
    //eg : {"SpecialObject" : "true" , "Class" : "worldMap.objects.Water" , "Name" : "Water1" , "DisplayType" : "String" , "Display" : "W"}
    public Square(Map<String, String> mapBG) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        //Checking if the object is "normal"
        if (mapBG.get("SpecialObject").compareTo("false")==0){
            this.backgroundObject=new BackgroundObject(mapBG.get("Name"),mapBG.get("Display"));
        }
        else { //Special case
            String s = mapBG.get("Class");
            Class<?> c = Class.forName(s);
            //Creating an object from the class given
            this.backgroundObject = (BackgroundObject) c.getConstructor(String.class,String.class).newInstance(mapBG.get("Name"),mapBG.get("Display"));
        }

    }
    //Used to finalize the object with a map describing a FrontObject
    //By default : SolidObject
    //eg : 001 = {SpecialObject : true , Class : SmallGrass , Name : SmallGrass1 , DisplayType : String , Display : "G"}
    public void setFrontObject(Map<String, String> mapF) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //If id = 000 --> mapF=null, we create a "null" object as the Square's FrontObject
        if(mapF==null){
            this.frontObject=new FrontObject();
        }
        else if (mapF.get("SpecialObject").compareTo("false")==0){
            this.frontObject=new SolidObject(mapF.get("Name"),mapF.get("Display"));
        }
        else { //Special case
            Class<?> c = Class.forName(mapF.get("Class"));
            Constructor<?> r = c.getConstructor(String.class,String.class);
            Object obj = r.newInstance(mapF.get("Name"), mapF.get("Display"));
            this.frontObject = (FrontObject) obj;
        }
    }

    public FrontObject getFrontObject() {
        return frontObject;
    }

    public BackgroundObject getBackgroundObject() {
        return backgroundObject;
    }
}
