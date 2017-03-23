package scripts.goldfarmscript.manager.scriptstarters;

import org.tribot.api.General;
import org.tribot.api2007.*;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.constants.Constants;
import scripts.goldfarmscript.manager.grandexchange.GrandExchangeBuyer;

/**
 * Created by Jet on 19/01/2017.
 */
public class SoftClay {

    public void softClayStater(){

        int amountOfClayToBuy = AccountStatus.amountOfCoinsAccountHas / Constants.priceOfClay;
        if(amountOfClayToBuy < 28){
            AccountStatus.currentScript = AccountStatus.Status.POTATOPICKER;

        }else {
            General.println("Buying around" + amountOfClayToBuy + " clay");
            General.println("walking to GE");
            //TODO: Calculate if bank has buckets in
            //Don't think the bucket check works great
            GrandExchangeBuyer geBulk = new GrandExchangeBuyer();
            geBulk.grandExchangeBulkBuy("Bucket", 14);
            General.sleep(1200, 2900);
            geBulk.openGrandExchange();
            General.sleep(50, 500);
            geBulk.buyItems("Clay", amountOfClayToBuy - 14);

            General.sleep(50, 500);
            //Close GE if window state is not null (Returns null if no window open)
            if(!(GrandExchange.getWindowState()==null)) {
                GrandExchange.close();
            }
            General.sleep(50, 500);
            if (Inventory.getAll().length > 0) {
                Banking.openBank();
                General.sleep(100, 600);
                if (!Banking.isBankScreenOpen()) {
                    WebWalking.walkToBank();
                    General.sleep(3200, 6000);
                    Banking.openBank();
                    General.sleep(100, 600);
                }
                General.sleep(300, 900);
                Banking.depositAll();
                General.sleep(300, 600);
                Banking.close();
            }
        }

    }
}
