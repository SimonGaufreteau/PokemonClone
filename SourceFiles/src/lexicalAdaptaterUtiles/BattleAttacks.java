package lexicalAdaptaterUtiles;

import entities.Pokemon;
import pokemonGameUtiles.Move;
import pokemonGameUtiles.Stats;
import pokemonGameUtiles.Status;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BattleAttacks {
    private BattleAttacks() {
    }

    //
    private static final String typesFileNamePath = "SourceFiles/OtherUsefulFiles/typesEffects.txt";

    static {
        try {
            Map<String, Map<String, Integer>> typesEffects = getTypesEffectsFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Map<String, Integer>> getTypesEffectsFromFile() throws IOException {
        File file = new File(typesFileNamePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        Map<String, Integer> tempMap = new HashMap<>();
        Map<String, Map<String, Integer>> map = new HashMap<>();

        String line = br.readLine();
        //Reading line until the end of the file
        while (line != null) {
            //TODO :Working here


            line = br.readLine();
        }


        return map;
    }


    public static String attack(Move m, Pokemon attacker, Pokemon[] targets) {
        StringBuilder s = new StringBuilder();
        for (Pokemon target : targets) s.append(attack(m, attacker, target, targets.length));
        return s.toString();
    }

    /*
    Uses the algorithm given in Bulbapapedia to calculate the damage dealt to the targets.
    TODO : implement the weather, number of turns for the critical rate
     */
    private static String attack(Move m, Pokemon attacker, Pokemon target, int nbTargets) {
        Stats attackerActualStats = attacker.getActualStats();
        Stats targetActualStats = target.getActualStats();

        int attackStat, defenseStat;
        if (m.getType().equals("Special")) {
            attackStat = attackerActualStats.getSpAtk();
            defenseStat = targetActualStats.getSpDef();
        } else {
            attackStat = attackerActualStats.getAtk();
            defenseStat = targetActualStats.getDef();
        }
        double modifier;
        if (nbTargets > 1) modifier = 0.75;
        else modifier = 1;

        //CriticalRate is either 1 or 2 (0.5 chance for now, will be changed soon, see to do)
        Random r = new Random();
        int criticalrate = 1 + (r.nextInt(2));

        //Number between 0 and 1 --> 0 and 0.15 --> 0.85 and 1
        double random = 0.15 * r.nextDouble() + 0.85;
        //int dmg=(((double)(((2*attacker.getLevel())/5)+2)*m.getPower()*(double)(attackStat/defenseStat))/50)+2*modifier;
        return "";
    }

    //Confuses the targets
    //Note : We assume that all Pokemons can be confused
    public static String confuse(Pokemon[] targets) {
        StringBuilder s = new StringBuilder();
        for (Pokemon p : targets) {
            p.addOtherStatus(new Status("Confusion"));
            s.append(String.format("%s is now confused !\n", p.getName()));
        }
        return s.toString();
    }

    public static String dealDirectDamage(int dmg, Pokemon attacker, Pokemon[] targets) {
        StringBuilder s = new StringBuilder();
        for (Pokemon p : targets) {
            p.updateHps(dmg);
            s.append(String.format("%s takes %d damages !\n", p.getName(), dmg));
        }
        return s.toString();
    }
}
