package scripts.goldfarmscript.scripts.combat;

import org.tribot.api2007.Inventory;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.manager.ScriptStarterAndFinisher;

/**
 * Created by Jet on 28/11/2016.
 */
public class WalkToGE extends Node{
    @Override
    public void execute() {
        //DOESN'T ACTUALLY WALK TO GE DUE TO CANT SELL HIDES TILL 18 hours old
        AccountStatus.status = "Walking to Bank to pick new skill and to bank items";
/*
        int whatBankToGoTo = General.random(1,2);
        if(whatBankToGoTo == 1){
            WebWalking.walkTo(new RSTile(3253 + General.random(-2,1),3421 + General.random(-2,2)));

        }else {
            WebWalking.walkTo(new RSTile(3182 + General.random(-2, 4), 3439 + General.random(-3, 3)));
        }

        General.sleep(4200,7500);
        Banking.openBankBanker();
        General.sleep(2400,4800);
        Banking.depositAll();
        General.sleep(1200,1800);
        Banking.depositEquipment();
        Banking.close();
*/

        ScriptStarterAndFinisher s = new ScriptStarterAndFinisher();
        s.finishScript();





    }

    @Override
    public boolean validate() {
        if(Inventory.isFull()){
            return true;
        }
        return false;
    }
}
