package scripts.goldfarmscript.scripts.softclay;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.constants.Constants;
import scripts.goldfarmscript.manager.grandexchange.GrandExchangeBuyer;
import scripts.goldfarmscript.manager.grandexchange.GrandExchangeSeller;
import scripts.goldfarmscript.manager.NewSkillPicker;
import scripts.goldfarmscript.utils.Utils;

/**
 * Created by Jet on 15/01/2017.
 */
public class Bank extends Node {
    @Override
    public void execute() {
        AccountStatus.status = "Banking";
        General.sleep(30, 100);
        Banking.openBank();
        //Open bank and try to withdraw clay
        General.sleep(600, 1200);
        //Just so people know - Even if there is not 14 clay in inventory or in bank, it wont matter - It will deposit/withdraw the max amount it can
        if (Inventory.getAll().length == 0) {
            //No items in inventory - So get items

            Banking.withdraw(14, "Clay");
            General.sleep(200, 700);
            Banking.withdraw(14, "Bucket");

        } else if (Inventory.getCount("Soft clay") == 14) {
            Banking.deposit(14, "Soft clay");
            General.sleep(200, 1200);
            Banking.withdraw(14, "Clay");
        } else {
            General.println("Something possibly messed up (Scripts.softclay.Bank) else occured (Line 61)");
            General.println("Bot should be able to recover");
            Banking.depositAll();
            General.sleep(500,1800);
            //Just incase of water in bank
            Banking.withdraw(999,"Bucket of water");
            General.sleep(500,1200);
            if(Inventory.getCount("Bucket of water") > 0){
                Banking.close();
                AccountStatus.status = "Removing water from buckets";
                RSItem[] bucketOfWaterThatNeedsEmptying = Inventory.find("Bucket of water");
                for(RSItem bucket:bucketOfWaterThatNeedsEmptying){
                    bucket.click("Empty Bucket of water");
                    General.sleep(500,1200);
                }
                General.sleep(500,1800);
                Banking.openBank();
                General.sleep(500,1800);

                Banking.depositAll();

            }

            General.sleep(100, 1200);
            Banking.withdraw(14, "Clay");
            General.sleep(600, 1200);
            Banking.withdraw(14, "Bucket");


        }
        General.sleep(1200,2400);
        //If after the bot there is no clay in inventory, then we need to go and buy some more
        if (Inventory.getCount("Clay") == 0) {
            if (Inventory.getAll().length > 0) {
                Banking.depositAll();
            }

            //See if bot should pick new script
            General.sleep(100,500);
            int shouldChangeSkill = General.random(0,3);
            if(shouldChangeSkill == 1) {
                NewSkillPicker sp = new NewSkillPicker();
                sp.shouldChangeSkill(1);
                return;
            }

                AccountStatus.status = "Buying Clay";
                //So it didn't pick a new skill, but not enough clay to continue. We need to buy some!
                GrandExchangeSeller s = new GrandExchangeSeller();
                GrandExchangeBuyer b = new GrandExchangeBuyer();
                String[] softClay = {"Soft clay"};
                //Method will automaticly sell alll the clay
                s.openGrandExchange();
                General.sleep(500,300);
                s.grandExchangeSell(softClay);
                //While selling the clay, the account would have updated the mone it has on it, so this will work
                General.sleep(500, 1200);
                int amountOfClayToBuy = AccountStatus.amountOfCoinsAccountHas / Constants.priceOfClay;
                b.grandExchangeBulkBuy("clay", amountOfClayToBuy);
                Banking.openBank();
                General.sleep(500,1200);
                Banking.depositAll();
                General.sleep(100,300);
                General.sleep(100, 1800);


                General.sleep(200, 800);
                Utils.performRandomTaskLC();


            }
        }




    @Override
    public boolean validate() {
        if((Inventory.getCount("Soft clay") > 0 && Banking.isInBank()) || (Inventory.getAll().length == 0 && Banking.isInBank() || Inventory.getCount("Clay") == 0 && Banking.isInBank()) || (Inventory.find("Bucket").length == 0 && Inventory.find("Bucket of water").length == 0)){
            //No clay in inventory
            return true;
        }
        return false;
    }
}
