package scripts.goldfarmscript.scripts.woodcut.framework;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.constants.Constants;
import scripts.goldfarmscript.manager.grandexchange.GrandExchangeBuyer;
import scripts.goldfarmscript.manager.grandexchange.GrandExchangeSeller;
import scripts.goldfarmscript.manager.NewSkillPicker;
import scripts.goldfarmscript.scripts.woodcut.WoodcuttingUtils;
import scripts.goldfarmscript.utils.Utils;

/**
 * Created by Jet on 22/01/2017.
 */
public class Bank extends Node{
    @Override
    public void execute() {
        AccountStatus.status = "Banking";
        WebWalking.walkToBank();
        General.sleep(1800,3800);
        Utils.performRandomTaskVLC();

        Banking.openBank();
        General.sleep(800,1800);
        if(!Banking.isBankScreenOpen()){
            General.println("Error occured - Expected to be in bank screen and wasn't. Retrying (scripts.woodcut.framework.Bank)");
            General.sleep(3200,1800);
            Banking.openBank();
            General.sleep(800,1800);
        }
        //Deposit items
        Banking.depositAllExcept(WoodcuttingUtils.highestAxe());
        int shouldChangeSkill = General.random(0,20);
        if(shouldChangeSkill == 1){
          NewSkillPicker n = new NewSkillPicker();
            n.shouldChangeSkill(1);
            return;
        }


        General.sleep(400,1800);

        if(Inventory.getAll().length == 0){
            AccountStatus.status = "Upgrading axe";
            //Nothing in inventory, meaning we need to get better axe out the bank
            Banking.withdraw(1,WoodcuttingUtils.highestAxe());
            General.sleep(800,1800);
            //Check if axe is now in invetory
            if(Inventory.getAll().length == 0){
                AccountStatus.status = "Buying a better axe";
                //Still nothing inventory, looks like we need to buy it. Let's first sell the logs
                Banking.close();
                GrandExchangeSeller s = new GrandExchangeSeller();
                s.grandExchangeSell(Constants.limitedListOfSellItems);
                General.sleep(500,1800);
                //TODO: Improve this, as atm it closes the GE then opens it back up, kinda bot ish
                //Must be in grand exchange still, so instead of calling the grandexchange method to buy items for us, we will call specific methods
                GrandExchangeBuyer b = new GrandExchangeBuyer();
                b.openGrandExchange();
                General.sleep(300,1200);
                String axeToBuy[] = {WoodcuttingUtils.highestAxe()};
                b.buyItems(axeToBuy);
                General.sleep(300,1200);
                GrandExchange.close();

            }
        }


    }

    @Override
    public boolean validate() {
        if(Inventory.isFull() || Inventory.find(Constants.axes).length == 0){
            return true;
        }
        return false;
    }
}
