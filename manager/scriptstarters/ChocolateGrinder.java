package scripts.goldfarmscript.manager.scriptstarters;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Inventory;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.constants.Constants;
import scripts.goldfarmscript.manager.grandexchange.GrandExchangeBuyer;

/**
 * Created by Jet on 19/01/2017.
 */
public class ChocolateGrinder {

    public void chocolateGrinderStarter(){

        int amountOfBarsToBuy = AccountStatus.amountOfCoinsAccountHas / Constants.priceOfChoclateBar; //AmountOfCoins will always have a value in it. //Do check just incase it doesnt
        if (amountOfBarsToBuy < 10) {
            AccountStatus.currentScript = AccountStatus.Status.POTATOPICKER;

        }else {
            General.println("Buying " + amountOfBarsToBuy + " chocolate bars");
            GrandExchangeBuyer geB = new GrandExchangeBuyer();
            geB.grandExchangeBulkBuy("Chocolate bar", amountOfBarsToBuy);
            General.sleep(500, 1200);
            String[] knife = {"Knife"};
            geB.buyItems(knife);
            //Close GE if window state is not null (Returns null if no window open)
            if(!(GrandExchange.getWindowState()==null)) {
                GrandExchange.close();
            }
            General.sleep(500, 1200);
            //Get the bot in the possition so it can start instantly
            Banking.openBank();
            General.sleep(500, 1200);
            if (Inventory.getAll().length > 0) {
                Banking.depositAll();
            }
            General.sleep(500, 1200);
            Banking.withdraw(27, "Chocolate bar");
            General.sleep(900, 1400);
            Banking.withdraw(1, "Knife");
            Banking.close();
        }
    }
}
