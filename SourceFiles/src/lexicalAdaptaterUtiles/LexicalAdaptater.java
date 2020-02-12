package lexicalAdaptaterUtiles;

import entities.Pokemon;
import exceptions.WordNotFoundException;
import exceptions.WrongDescriptionException;
import pokemonGameUtiles.Move;

/*
--> Purpose of this class : create an adapter that will be able to manipulate Strings in different contexts.


--> Why do we need this here ?
    - Pack up everything we need at the same place to simplify the code
    - Easier implementation and modification

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
public final class LexicalAdaptater {
    //Prevent possible instances to appear in the code --> only a static-utility Class
    private LexicalAdaptater() {
    }

    /*
    Returns the instance of a Move (Attack, Debuff, Buff, etc..) according to a String (mostly taken from a pre-defined file).
    Input : String moveLine --> a String describing the move
    Output : a Move

    (See class' pre-definition for examples of lines)
     */

    public static Move generateMoveFromString(String moveLine) throws Exception {
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


        //While we are constructing the class, this return statement will be used (same as in MoveDict Class)
        return new Move(id, name, type, category, contest, PP, power, accuracy, description);
    }


    public static String runMove(Move m, Pokemon attacker, Pokemon[] targets) throws WrongDescriptionException, WordNotFoundException {
        //Checking separately the empty String
        if (m.getDescription().length()==0){
            return BattleAttacks.attack(m, attacker, targets);
        }
        return runMoveRecursive(m,attacker,targets,m.getDescription());
    }

    //Recursively updates the game with the actual description of the move (the description gets shortened every time
    private static String runMoveRecursive(Move m,Pokemon attacker,Pokemon[] targets,String actualDescription) throws WrongDescriptionException, WordNotFoundException {
        if (m.getDescription().length()==0) return "";
        else {
            //Return String containing the result of the attack
            StringBuilder s = new StringBuilder();
            String nextDescription;

            String desc = m.getDescription();
            String[] split = desc.split(" ,.");
            int nextIndex = Math.min(desc.indexOf(","), desc.indexOf("."));
            if (split.length != 0) {
                switch (split[0].toLowerCase()) {
                    case "always":
                        /* Semantic : Always inflicts X HP
                         * Example : Always inflicts 10 HP
                         * Required : length>=4,split[2] must be an int
                         */
                        if (split.length < 4) throw new WrongDescriptionException(desc);
                        if (split[1].toLowerCase().compareTo("inflicts") != 0)
                            throw new WordNotFoundException(split[1], desc);
                        if (split[3].toLowerCase().compareTo("hp") != 0)
                            throw new WordNotFoundException(split[3], desc);
                        try {
                            int dmg = Integer.parseInt(split[2]);
                            s.append(BattleAttacks.dealDirectDamage(dmg, attacker, targets));
                        } catch (NumberFormatException e) {
                            throw new WrongDescriptionException(desc);
                        }
                        break;
                    case "charges":
                        /* Semantic : Charges on the first turn
                         * Required : length>=5
                         */
                        //If the attacker is waiting, it means that we've already been in this condition --> deal the dmg to the targets
                        if (split.length<5) throw new WrongDescriptionException(desc);
                        if (attacker.isWaiting()){
                            s.append(BattleAttacks.attack(m, attacker, targets));
                        }
                        attacker.setWaiting();
                        break;
                    case "confuses" :
                        /* Semantic : Confuses {target}
                         * Example : Confuses opponent
                         * Required : length>=2
                         * Note : only 'opponent' is valid at the moment
                         */
                        s.append(BattleAttacks.confuse(targets));
                        break;
                    case "damage" :
                        /* Semantic : Damage occurs X turns later
                         * Example : Damage occurs 2 turns later
                         * Required : length>=5,split[2] int
                         * Note : Only one of these move can be up for one pokemon. TODO : decrease the number of turn of this move by one each turn
                         */
                        if (split.length<5) throw new WrongDescriptionException(desc);
                        //Checking if the attacker doesn't already have a move waiting
                        if (!attacker.getWaitingToBeDealt().isEmpty()) s.append("Impossible, a move is already prepared !");
                        else {
                            try{
                                int turns = Integer.parseInt(split[2]);
                                attacker.setWaitingToBeDealt(m,turns);
                                s.append(String.format("%s prepared an attack !",attacker.getName()));
                            }
                            catch (NumberFormatException e) {
                                throw new WrongDescriptionException(desc);
                            }
                        }
                        break;
                    case "doubles" :
                        /* Semantic : Doubles in power each turn for X turns
                         * Example : Doubles in power each turn for 5 turns
                         * Required : length>=8,split[6] int
                         */
                        //If the pokemon is already in a permanent state
                        if (split.length<8) throw new WrongDescriptionException(desc);
                        if (!attacker.getPermanentAttack().isEmpty()){
                            s.append(BattleAttacks.attack(m, attacker, targets));
                            attacker.decreasePermanentAttack(m);
                        }
                        else {
                            try {
                                int turns = Integer.parseInt(split[2]);
                                attacker.setPermanentAttack(m, turns);
                                s.append(String.format("%s is preparing an attack !", attacker.getName()));
                                s.append(BattleAttacks.attack(m, attacker, targets));
                            } catch (NumberFormatException e) {
                                throw new WrongDescriptionException(desc);
                            }
                        }
                        break;
                    default:
                        throw new WrongDescriptionException(desc);
                }
            }
            if (nextIndex == -1)
                return "";
            else {
                s.append(runMoveRecursive(m, attacker, targets, actualDescription.substring(nextIndex)));
                return s.toString();
            }
        }
    }


    //TODO : Generate an Item from a String
    /*public static Item generateItemFromString(String itemLine) {
        return null;
    }*/

    //TODO : Generate an Effect (not implemented yet) from a String
    /*public Effect generateEffectFromString(String effectLine){
        return null
    } */
}
