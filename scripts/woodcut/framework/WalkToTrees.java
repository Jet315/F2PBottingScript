package scripts.goldfarmscript.scripts.woodcut.framework;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSTile;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.scripts.woodcut.WoodcuttingUtils;
import scripts.goldfarmscript.utils.Utils;
import scripts.webwalker_logic.WebWalker;

/**
 * Created by Jet on 22/01/2017.
 */
public class WalkToTrees extends Node {
    @Override
    public void execute() {

        AccountStatus.status = "Walking to the trees";
        int woodcuttingLevel = WoodcuttingUtils.getWoodcutLevel();
        //Walk to normal log area
        if(woodcuttingLevel < 15){

                //Walk to tree location
                WebWalker.walkTo(new RSTile(3160 + General.random(-3,3),3455 + General.random(-3,3)));
                General.sleep(2200,4800);

            //Else walk to oak area, improve in future to support more logs
        }else{
                //Walk to oak location
                WebWalker.walkTo(new RSTile(3166 + General.random(-2,2),3417 + General.random(-3,3)));
                General.sleep(2200,4800);
            }

        Utils.performRandomTaskLC();
        General.sleep(3200,6200);
    }

    @Override
    public boolean validate() {
        //If inventory is not full and not already in the location
        if(!Inventory.isFull() && !(WoodcuttingUtils.isInTreeLocation())){
            return true;
        }
        return false;
    }
}
