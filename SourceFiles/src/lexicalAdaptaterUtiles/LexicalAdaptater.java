package lexicalAdaptaterUtiles;

import items.Item;
import pokemonGameUtiles.Move;

/*
--> Purpose of this class : create an adapter that will be able to convert a String to the corresponding object.

--> Why do we need this here ?
    - Pack up everything we need at the same place to simplify the code
    - Easier implementation and modification
    -

--> Potential Objects adapted (in this project) :
    - TODO : Moves
    - TODO : Items
    - TODO : Effects

--> Examples of adaptation :
    - Move (simple attack) : 10,Scratch,Normal,Physical,Tough,35,40,100%,""
    - Move (effect-only) : 39,Tail Whip,Normal,Status,Cute,30,—,100%,Lowers opponent's Defense.
    - Move (attack and debuff) : 189,Mud-Slap,Ground,Special,Cute,10,20,100%,Lowers opponent's Accuracy.

--> Future adaptations :
A package than can create an Object according to a regex or some samples.
 */
public class LexicalAdaptater {

    /*
    Returns the correct instance of a Move (Attack, Debuff, Buff, etc..) according to a String (mostly taken from a pre-defined file).
    Input : String moveLine --> a String describing the move
    Output : a Move (can be of any sub-classes of Move)

    (See class' pre-definition for examples of lines)
     */

    public Move generateMoveFromString(String moveLine) throws Exception {
        int id, PP, power, accuracy;
        String name, type, category, contest, description;
        String[] st;

        if (moveLine.compareTo("") != 0) {
            st = moveLine.split(",");
            if (st.length < 9) {
                throw new Exception("Error while reading the following line (invalid length : " + st.length + " ) : \"" + moveLine + "\"");
            } else if (st.length > 9) {
                //If ',' in description, concat last elements
                for (int j = 9; j < st.length; j++) {
                    st[8] = st[8].concat(",").concat(st[j]);
                }
            }
            id = Integer.parseInt(st[0]);
            name = st[1];
            type = st[2];
            category = st[3];
            contest = st[4];
            PP = Integer.parseInt(st[5]);

            //If we have a — , put -1 in power instead
            if (st[6].compareTo("—") == 0) {
                power = -1;
            } else {
                power = Integer.parseInt(st[6]);
            }

            //If we have a — , put -1 in accuracy instead
            if (st[7].compareTo("—") == 0) {
                accuracy = -1;
            } else {
                accuracy = Integer.parseInt(st[7].replace("%", ""));
            }
            //Replacing "" in description
            st[8] = st[8].replace("\"", "");
            if (st[8].compareTo("") == 0) {
                description = String.format("A standard %s attack", type);
            } else description = st[8];
        } else throw new Exception("Error. Empty String given as a Move.");


        //We now have everything we need to build a Move. We now use the "" method to generate the correct instance according to the move description.


        //While we are constructing the class, this return statement will be used (same as in MoveDict Class)
        return new Move(id, name, type, category, contest, PP, power, accuracy, description);
    }

    //TODO : Generate the correct instance from a description
    private Class<Move> getClassFromDescription(String description) {
        return Move.class;
    }

    //TODO : Generate an Item from a String
    public Item generateItemFromString(String itemLine) {
        return null;
    }

    //TODO : Generate an Effect (not implemented yet) from a String
    /*public Effect generateEffectFromString(String effectLine){
        return null
    } */
}
