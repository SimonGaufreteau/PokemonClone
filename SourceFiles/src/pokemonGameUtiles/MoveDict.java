package pokemonGameUtiles;

import exceptions.FileFormatException;
import exceptions.MoveNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MoveDict {
    Move[] moves;

    //Creates a MoveDict from a file. (See MoveList.txt for an example)
    public MoveDict(String fileName) throws IOException, FileFormatException {
        File file = new File(System.getProperty("user.dir")+"\\SourceFiles\\"+fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        //Reading all lines until the end. Blank lines are passed
        String line = br.readLine();
        String[] st;
        ArrayList<Move> moves = new ArrayList<>();
        int id,PP,power,accuracy;
        String name;
        String type;
        String category;
        String contest;
        String description;

        while (line!=null){
            if(line.compareTo("")!=0){
                st = line.split(",");
                if (st.length<9){
                    throw new FileFormatException(fileName);
                }
                else if(st.length>9){
                    //If ',' in description, concat last elements
                    for (int j=9;j<st.length;j++){
                        st[8]=st[8].concat(",").concat(st[j]);
                    }
                }
                id = Integer.parseInt(st[0]);
                name = st[1];
                type = st[2];
                category = st[3];
                contest = st[4];
                PP = Integer.parseInt(st[5]);

                //If we have a — , put -1 in power instead
                if (st[6].compareTo("—")==0){
                    power = -1;
                }
                else {
                    power = Integer.parseInt(st[6]);
                }

                //If we have a — , put -1 in accuracy instead
                if (st[7].compareTo("—")==0){
                    accuracy = -1;
                }
                else {
                    accuracy = Integer.parseInt(st[7].replace("%",""));
                }
                //Replacing "" in description
               st[8] = st[8].replace("\"","");
                if (st[8].compareTo("")==0) {
                    description = String.format("A standard %s attack",type);
                }
                else description = st[8];

                //All attributes set, adding the new move to the array
                moves.add(new Move(id,name,type,category,contest,PP,power,accuracy,description));
            }
            line=br.readLine();
        }
        //All files read. Updating attributes
        this.moves= new Move[moves.size()];
        this.moves= moves.toArray(this.moves);
    }



    public Move getByName(String name) throws MoveNotFoundException {
        for (Move m : moves) {
            if (m.getName().compareTo(name)==0) return m;
        }
        //Move could not be found
        throw new MoveNotFoundException(name);

    }

    public Move getByID(int id) throws MoveNotFoundException {
        for (Move m : moves) {
            if (m.getId()==id) return m;
        }
        //Move could not be found
        throw new MoveNotFoundException(id);

    }

    @Override
    public String toString() {
        return "MoveDict{" +
                "moves=" + Arrays.toString(moves) +
                '}';
    }
}
