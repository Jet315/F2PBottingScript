package scripts.goldfarmscript.scripts.chocolate;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Inventory;
import scripts.goldfarmscript.api.banknoter.NoteItems;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.constants.Constants;
import scripts.goldfarmscript.manager.grandexchange.GrandExchangeBuyer;
import scripts.goldfarmscript.manager.grandexchange.GrandExchangeSeller;
import scripts.goldfarmscript.manager.NewSkillPicker;

/**
 * Created by Jet on 27/12/2016.
 */
public class Bank extends Node{
    @Override
    public void execute() {
        AccountStatus.status = "Banking";

        Banking.openBank();
        General.sleep(500,1200);
        Banking.deposit(27,"Chocolate dust");
        General.sleep(300,1800);

        if(Inventory.getCount("knife") == 1 && Inventory.getAll().length == 1){
            Banking.withdraw(27,"Chocolate bar");
        }else{
            if(Inventory.getAll().length == 0){
                //No items in inventory
                Banking.withdraw(27,"Chocolate bar");
                General.sleep(100,900);
                Banking.withdraw(1,"Knife");

            }else{
                General.println("Error occured (Line 81 scripts.chocolate.Bank) (No knife found in inventroy");
                General.println("Bot trying to recover...");
                Banking.depositAll();
                General.sleep(100,900);
                Banking.withdraw(27,"Chocolate bar");
                General.sleep(100,900);
                Banking.withdraw(1,"Knife");
            }
        }
        General.sleep(500,1400);

        if(Inventory.getCount("Chocolate bar") == 0){
            //Often the inventory hasn't loaded into cashe, so therefor it thinks there are not chocolate bars when there is a few, so to preven this:
            General.sleep(3200,6400);
            if(!(Inventory.getCount("Chocolate bar") == 0)) return;
            //else let's continue - No chocolate
            int shouldChangeSkill = General.random(0,2);
            if(shouldChangeSkill == 1) {
                NewSkillPicker sp = new NewSkillPicker();
                sp.shouldChangeSkill(1);
                return;
            }
            //Need to check that the bo

                //More chocolate needs to be bought - (Quite messy code, but oh well :( )
                AccountStatus.status = "Buying more chocolate";
                NoteItems n = new NoteItems();
                GrandExchangeBuyer b = new GrandExchangeBuyer();
                GrandExchangeSeller s = new GrandExchangeSeller();
                n.setNoted(true);
                General.sleep(100,200);
                Banking.withdraw(b.randomBankWithdraw(), "Chocolate dust");
                General.sleep(80,250);
                Banking.withdraw(b.randomBankWithdraw(), "Coins");
                General.sleep(50,200);
                Banking.close();
                General.sleep(500, 1200);
                String[] chocoDust = {"Chocolate dust"};
                s.openGrandExchange();
                General.sleep(500, 1200);
                s.sellItems(chocoDust);
                General.sleep(500, 1200);
                //At this point items are sold and money is collected - Grand exchange is NOT closed
                AccountStatus.amountOfCoinsAccountHas = Inventory.getCount("coins");
                //Check the current counts the account has
                //Work out how many bars to buy
                int amountOfBarsToBuy = AccountStatus.amountOfCoinsAccountHas / Constants.priceOfChoclateBar;
                b.buyItems("Chocolate bar", amountOfBarsToBuy);
                General.sleep(200, 1200);
                GrandExchange.close();
                General.sleep(300,1200);
                Banking.openBank();
                General.sleep(500, 1800);
                AccountStatus.amountOfCoinsAccountHas = Inventory.getCount("coins");
                Banking.depositAll();
                //Could call a method to sell it automaticly, however does procedures in it that are not needed, so I will call the methods myself
            }




        General.sleep(300,1200);
        Banking.close();

    }

    @Override
    public boolean validate() {
        //                                                                                  //Check if inventory contains noted chocolate - Wont work if it's noted
        if(Inventory.getCount("Chocolate bar") == 0 || Inventory.getCount("Knife") == 0 || Inventory.getCount(1974) == 0){
            return true;

        }
        return false;
    }
}
