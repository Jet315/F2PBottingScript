package scripts.goldfarmscript.scripts.combat;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.manager.Bank;
import scripts.goldfarmscript.utils.Utils;

/**
 * Created by Jet on 06/11/2016.
 */
public class CombatStart extends Node{

    @Override
    public void execute() {
        Utils.performRandomTaskHC();
        AccountStatus.status = "Starting combat - Walking to bank";
        RSItem[] sword =  Inventory.find(1277);
        RSItem[] shield =  Inventory.find(1171);
        General.sleep(10,300);
        if(shield.length >=1 && sword.length >= 1){
            sword[0].click("wield");
            General.sleep(10,300);
            shield[0].click("Wield");
            General.sleep(10,2000);
        }else{
            General.println("ERROR OCCURED (CLASS: COMBATSTART - When trying to wield shield and sword!)");
            System.exit(1);
        }
        Combat.selectIndex(1);
        Combat.setAutoRetaliate(true);
        if(General.random(0,2) == 1){
            //Random chance of switching back to inventory
            General.sleep(50,500);
            Inventory.open();
        }
        WebWalking.walkTo(new RSTile(3183 + General.random(-2,2),3436+ General.random(-2,2)));
        General.sleep(1200,2400);
        Bank b = new Bank();
        int notToBankItems[] = {1171,1277};
        b.depositItems(notToBankItems);
        General.sleep(50,500);
        Utils.performRandomTaskHC();

    }

    @Override
    public boolean validate() {

        if (Player.getPosition().distanceTo(new RSTile(3233, 3230)) <= 1 && Utils.getPlayersLevel() == 3 && Inventory.find(1277).length > 0) {
            return true;
        } else {
            return false;
        }
    }






}
