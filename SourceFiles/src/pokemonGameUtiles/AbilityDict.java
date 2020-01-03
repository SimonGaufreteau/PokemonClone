package pokemonGameUtiles;

import exceptions.AbilityNotFoundException;
import exceptions.FileFormatException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AbilityDict {
    Ability[] abilities;

    public AbilityDict(String directoryName) throws IOException, FileFormatException {
        File folder = new File(System.getProperty("user.dir")+"\\SourceFiles\\"+directoryName);
        File[] listOfFiles = folder.listFiles();

        ArrayList<Ability> abilitiesRead = new ArrayList<>();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                String fileName = file.getName();
                //Checking if the file is a .pkm
                String[]st = (fileName.split("\\."));
                if (file.isFile() && st[1].compareTo("abi")==0) {
                    //For each file, adding the Ability to the List
                    Ability tempAbility = readAbility(file);
                    abilitiesRead.add(tempAbility);
                }
            }
        }
        //All files read. Updating attributes
        abilities= new Ability[abilitiesRead.size()];
        abilities= abilitiesRead.toArray(abilities);
    }

    //Reads an Ability from a File
    private Ability readAbility(File file) throws IOException, FileFormatException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        String[] st;
        int id=0;
        String name=null;
        String description=null;

        while (line!=null){
            st = line.replace("\"","").split(" : ");
            if (st[1]==null) throw new FileFormatException(file.getName());
            switch (st[0].toLowerCase()){
                case "id" :
                    id = Integer.parseInt(st[1]);
                    break;
                case "name" :
                    name = st[1];
                    break;
                case "description" :
                    description = st[1];
                    break;
            }
            line = br.readLine();
        }//Lines read.
        if(id==0 || name==null || description==null) throw new FileFormatException(file.getName());
        return new Ability(id,name,description);
    }

    public Ability getByName(String name) throws AbilityNotFoundException {
        for (Ability a:abilities) {
            if(a.getName().compareTo(name)==0) return a;
        }
        //If Ability not found ,throw Exception
        throw new AbilityNotFoundException(name);
    }

    @Override
    public String toString() {
        return "AbilityDict{" +
                "abilities=" + Arrays.toString(abilities) +
                '}';
    }
}
