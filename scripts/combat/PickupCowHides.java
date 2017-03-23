package scripts.goldfarmscript.scripts.combat;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;

import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSGroundItem;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.utils.Utils;

/**
 * Created by Jet on 27/11/2016.
 */
public class PickupCowHides extends Node{
    @Override
    public void execute() {
        AccountStatus.status = "Picking up cowhides";
        RSGroundItem[] cowHide = GroundItems.findNearest("Cowhide");
        if(cowHide.length>0){
            if (DynamicClicking.clickRSGroundItem(cowHide[0], "Take Cowhide")&& cowHide[0].isClickable()) {
                Timing.waitCondition(new Condition() {

                    @Override
                    public boolean active() {
                        return Player.isMoving();
                    }
                }, General.random(600, 1800));
            }

            }

        Inventory.dropAllExcept(1739);
        General.sleep(500,1200);
        if(Utils.randomMediumNumber() == 1){
            AccountStatus.status = "Taking a small break";
            //1 to 120 secs break
            General.sleep(1000,30000);
            Utils.performRandomTaskHC();
        }
        General.sleep(500,1800);
    }

    @Override
    public boolean validate() {

        if(GroundItems.find("cowhide").length > 0 && !(Inventory.isFull() && !(Player.getRSPlayer().isInCombat()))){
            if(Player.getRSPlayer().isInCombat() || Player.getRSPlayer().isMoving()){
                return false;
            }
            return true;

        }
        return false;
    }
}
