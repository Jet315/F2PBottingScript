package scripts.goldfarmscript.scripts.woodcut;


import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
/**
 * Created by Jet on 22/01/2017.
 */

public class WoodcuttingUtils {
    //Polygon area of the tree areas
    public static RSArea logTreesArea = new RSArea(new RSTile[] { new RSTile(3173, 3450, 0), new RSTile(3171, 3455, 0), new RSTile(3164, 3460, 0), new RSTile(3157, 3466, 0), new RSTile(3141, 3466, 0), new RSTile(3145, 3454, 0), new RSTile(3148, 3442, 0), new RSTile(3156, 3442, 0), new RSTile(3163, 3448, 0) });
    public static RSArea oakTreesArea =new RSArea(new RSTile[] { new RSTile(3171, 3425, 0), new RSTile(3165, 3424, 0), new RSTile(3162, 3420, 0), new RSTile(3163, 3413, 0), new RSTile(3168, 3413, 0), new RSTile(3170, 3417, 0) });
    //Path to trees
    public static RSTile[] bankToLogTrees = new RSTile[]{new RSTile(3183,3448), new RSTile(3182,3451), new RSTile(3178,3455), new RSTile(3172,3457), new RSTile(3166,3456),new RSTile(3157,3458)};
    public static RSTile[] bankToOakTrees = new RSTile[]{new RSTile(3182,3432), new RSTile(3177,3429),new RSTile(3172,3427),new RSTile(3169,3424),new RSTile(3165,3419)};
    //Class contains methods I can use when when using node task framework
    //I am planning to add more trees in, which is why the code isn't really completed - It is designed for me to easily add more trees if (Using else ifs, so I can add more else ifs in)



    public static boolean isInTreeLocation(){
        int woodcuttingLevel = getWoodcutLevel();
        //Check if player is in log area (Must have < 15 woodcut)
        if(woodcuttingLevel < 15){
            if(logTreesArea.contains(Player.getPosition())){
                return true;
            }else{
                return false;
            }
        }else if(woodcuttingLevel >= 15){
            if(oakTreesArea.contains(Player.getPosition())){
                return true;
            }else{
                return false;
            }

        }
        return false;
    }


    public static String currentTree() {
        int woodcuttingLevel = getWoodcutLevel();
        if(woodcuttingLevel <15){
            return "Tree";
        }
        return "Oak";

    }



    public boolean shouldUpgradeAxe() {
        //Make sure the axe is cashed into memory
        General.sleep(500,1200);
        if (Inventory.getCount(highestAxe()) == 1){
            return false;
        }
        return true;

    }


    public static String highestAxe() {
        int woodcuttingLevel = getWoodcutLevel();
        if (woodcuttingLevel < 6) {
            return "Iron axe";
        } else if (woodcuttingLevel >= 6 && woodcuttingLevel < 21) {
            return "Steel axe";
        } else if (woodcuttingLevel >= 21 && woodcuttingLevel < 31) {
            return "Mithril axe";
        } else if (woodcuttingLevel >= 31 && woodcuttingLevel < 41) {
            return "Adamant axe";
        } else {
            //Should be rune axe here, but costs the bot a lot of money so wil lreturn addy axe for now
            return "Adamant axe";
        }


    }


    public static int getWoodcutLevel() {

        return Skills.getCurrentLevel(Skills.SKILLS.WOODCUTTING);
    }


    }
