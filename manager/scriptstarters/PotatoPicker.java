package scripts.goldfarmscript.manager.scriptstarters;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;

/**
 * Created by Jet on 19/01/2017.
 */
public class PotatoPicker {

    public void potatoPickerStarter(){
        General.sleep(500, 1200);
        if(Inventory.getAll().length > 0){
            Banking.openBank();
            General.sleep(100,600);
            if(!Banking.isBankScreenOpen()){
                WebWalking.walkToBank();
                General.sleep(3200,6000);
                Banking.openBank();
                General.sleep(100,600);
            }
            General.sleep(300,900);
            Banking.depositAll();
            General.sleep(100,600);
            Banking.close();
        }



    }
}
