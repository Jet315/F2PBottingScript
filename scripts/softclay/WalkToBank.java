package scripts.goldfarmscript.scripts.softclay;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.utils.Utils;

/**
 * Created by Jet on 15/01/2017.
 */
public class WalkToBank extends Node{
    @Override
    public void execute() {
        AccountStatus.status = "Walking to bank";
        int whatBankShouldWalkTo = Utils.randomSmallNumber();
        if(whatBankShouldWalkTo == 0){
            scripts.webwalker_logic.WebWalker.walkTo(new RSTile(3096 + General.random(-1,2),3494 + General.random(-1,1)));
        }else{
            scripts.webwalker_logic.WebWalker.walkTo(new RSTile(3094 + General.random(-1,1),3490 + General.random(-2,3)));
        }
        General.sleep(2400,3400);

        if(!Banking.isInBank()){
            //Just incase still not in bank
            WebWalking.walkToBank();
            General.println("Error occured - Bot predicted to be in bank but did not make it. scripts.softclay.WalkToBank (Rewalking to bank)");
            General.sleep(3200,4200);
        }
        Utils.performRandomTaskLC();

    }

    @Override
    public boolean validate() {
        //When miles away starting the script, I need a way for the bot to walk to the correct area, then a new way to only walk to the bank when softclay is in inventory
        if(Player.getPosition().distanceTo(new RSTile(3094,3491)) > 7 && Inventory.getCount("Soft clay") > 0 || Player.getPosition().distanceTo(new RSTile(3094,3491)) > 28 || Player.getPosition().distanceTo(new RSTile(3089,3490)) < 2 || Player.getPosition().distanceTo(new RSTile(3093,3498)) < 2){
            return true;

        }
        return false;
    }
}
