package scripts.goldfarmscript.scripts.potato;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.*;
import scripts.goldfarmscript.constants.Constants;
import scripts.goldfarmscript.utils.Utils;

/**
 * Created by Jet on 29/11/2016.
 */
public class WalkToFields extends Node{
    private final int CLOSED_GATE_ID = 1558;
    private final int OPEN_GATE_ID = 1559;

    @Override
    public void execute() {
        AccountStatus.status = "Walking to the potato farm";
        scripts.webwalker_logic.WebWalker.walkTo(new RSTile(3146 + General.random(-2,2),3293 + General.random(-1,2)));
        General.sleep(3200,7200);
        if(isGateClosed()) {
            RSObject[] gate = Objects.findNearest(7, CLOSED_GATE_ID);
            if (gate.length > 0) {
                if(!gate[0].isOnScreen()) Utils.turnToTile(gate[0]);
                DynamicClicking.clickRSObject(gate[0], "Open");
                General.sleep(1800, 2900);
            }

        }
        scripts.webwalker_logic.WebWalker.walkTo(new RSTile(3147 + General.random(-3,4),3285 + General.random(-2,5)));
        General.sleep(50,1000);
        Utils.performRandomTaskLC();
        General.sleep(1500,2600);


    }

    @Override
    public boolean validate() {
        if(!Inventory.isFull() && !Constants.potatoArea.contains(Player.getPosition())){
            return true;

        }

        return false;
    }

    private boolean isGateClosed(){
        RSObject[] gate = Objects.findNearest(5, CLOSED_GATE_ID);
        if(gate != null && gate.length > 0){
            return true;
        }
        return false;


    }
}
