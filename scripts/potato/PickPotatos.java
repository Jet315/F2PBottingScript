package scripts.goldfarmscript.scripts.potato;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.*;
import scripts.goldfarmscript.constants.Constants;
import scripts.goldfarmscript.utils.Utils;

/**
 * Created by Jet on 04/12/2016.
 */
public class PickPotatos extends Node{

    @Override
    public void execute() {
        AccountStatus.status = "Picking Potatos";
        RSObject[] potato = Objects.findNearest(10, Filters.Objects.nameContains("Potato"));
        int y = General.random(0,2);
        int z;
        if(y > 0){
             z = General.random(0,2);
        }else{
             z = y;
        }


        if(potato.length > z){
            if(DynamicClicking.clickRSObject(potato[z],"Pick potato") && potato[z].isOnScreen()){
                General.sleep(900,1800);
                Timing.waitCondition(new Condition() {

                    @Override
                    public boolean active() {
                        return Player.getAnimation() == 827 || Player.isMoving();
                    }
                }, General.random(300, 950));

            }

        }
        Utils.performRandomTaskVLC();
    }

    @Override
    public boolean validate() {
        if(!Inventory.isFull() && Constants.potatoArea.contains(Player.getPosition())){
            return true;

        }

        return false;
    }
}
