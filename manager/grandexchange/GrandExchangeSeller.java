package scripts.goldfarmscript.manager.grandexchange;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.types.*;
import scripts.goldfarmscript.api.banknoter.NoteItems;
import scripts.goldfarmscript.constants.*;
import scripts.goldfarmscript.constants.Constants;

import java.util.Random;

/**
 * Created by Jet on 28/11/2016.
 */
public class GrandExchangeSeller {


    public void grandExchangeSell(String[] items){
        GrandExchangeSeller ge = new GrandExchangeSeller();
        General.sleep(1800,8000);
        WalkToGrandExchange walk = new WalkToGrandExchange();
        AccountStatus.status = "Walking to GE";
        //Walk to GE if not already there
        walk.walkToGrandExchange();
        General.sleep(6500, 12400);
        AccountStatus.status = "Withdrawing items from bank";
        //Withdraw items from bank
        withdrawItemsFromBank(items);
        General.sleep(1400,3200);
        if (Inventory.getAll().length == 0) {
            AccountStatus.status = "No sellable items detected in inventory - continuing to the next step";
            return;
        }
        AccountStatus.status = "Selling items";
        ge.openGrandExchange();

        General.sleep(1200, 6000);
        //Added more error code while opening GE, so I don't believe this is needed
        /*
        if(!isGrandExchangeOpen()){
            ge.openGrandExchange();
        }
        */
        General.sleep(50,1200);
        ge.sellItems(items);
        General.sleep(500,1200);
        General.sleep(30,500);
        AccountStatus.amountOfCoinsAccountHas = Inventory.getCount("coins");
        AccountStatus.status = "Successfully sold items - Continuing next process";




        }

    public void withdrawItemsFromBank(String[] items){
        Banking.openBank();
        General.sleep(3200,5400);

        if(!Banking.isInBank()){
            WalkToGrandExchange walk = new WalkToGrandExchange();
            walk.walkToGrandExchange();
            General.sleep(2400,4400);
            Banking.openBank();
        }
        if(Inventory.getAll().length >0) {
            if(!(Inventory.getAll().length == 1 && Inventory.getCount("Coins") > 0)) {
                Banking.depositAll();
            }
        }

        General.sleep(1800,4000);
        NoteItems.setNoted(true);
        GrandExchangeSeller s = new GrandExchangeSeller();
        for(int i = 0;i<items.length; i++){
            //picks random number like 999 9999 9999999
            Banking.withdraw(s.randomSellingNumber(),items[i]);
            General.sleep(500,1200);

        }
        Banking.withdraw(99999999 +General.random(0,1),"Coins");
        Banking.close();
        General.sleep(1800,2700);


    }
    public void sellItems(String[] items){
            for(String s : items) {
                //Im passing through a bunch of items - Not all items may be in inventory, to stop it
                //offering nothing and clicking "collect" - just break out of loop before.
                if(Inventory.find(s).length == 0){
                    continue;
                }
                General.sleep(200, 1800);
                GrandExchange.offer(s, General.random(1, 8), Inventory.getCount(s), true);
                General.sleep(3400, 5300);

                //TODO: Improve this so it checks if it hasn't bought
                RSInterfaceChild child = Interfaces.get(Constants.MASTER, Constants.COLLECT_CHILD);
                RSInterfaceComponent component = child.getChild(Constants.COLLECT_COMPONENT);
                //Could be null - Nothing I can do atm tho takes a lot of code to fix, will do in future
                component.click("Collect to inventory");
                General.sleep(200, 2800);
            }


    }
    public void openGrandExchange(){
        RSTile GrandExchangeTile = new RSTile(3166,3487,0);
        RSNPC[] grandExchangeClerk = NPCs.find("Grand Exchange Clerk");
        if(grandExchangeClerk.length>0){
            if(grandExchangeClerk[0].isClickable()){
                grandExchangeClerk[0].click("Exchange Grand Exchange Clerk");
                General.sleep(1000,2500);
                //Error occurs while grand exchange is not open, for some reason somtimes doesn't click the GE, so walk even closer if it's not open
                //I call a method which will try and reopen it

            }
            else{
                WebWalking.walkTo(grandExchangeClerk[0]);
            }
        }
        else{
            WebWalking.walkTo(GrandExchangeTile);
        }
        //Yes I know doing this is bad, as it probably will stay in the while loop forever, although it's better that it keeps trying that a null point that will occur, and I can't stop occuring without it doing thid bit
        while(GrandExchange.getWindowState()  == null){

            Walking.walkTo(new RSTile(3164 + General.random(-1,1),3487 + General.random(-2,0)));
            //Try reopening it using the ID of the NPC - Also add message saying it got here
            General.println("Error occured - GrandExchangeSeller line 117 (Retrying)");
            RSNPC[] grandExchangeClerkSecondAttempt = NPCs.find(2148);
            General.sleep(200,400);
            if(grandExchangeClerkSecondAttempt.length > 0){
                grandExchangeClerkSecondAttempt[0].click("Exchange Grand Exchange Clerk");
            }else{
                General.println("Fatal error occurred - GrandExchangeSeller line 126");
            }
            General.sleep(3200,9400);
        }
    }

    public int randomSellingNumber(){
        Random r = new Random();
        int n = r.nextInt(6);
        switch (n){
            case 1:
                return 99999;
            case 2:
                return 123456;
            case 3:
                return 11111;

            case 4:
                return 9999;
            case 5:
                return 123321;
            case 6:
                return 657677;
            default:
                return 999;
        }



    }
    public boolean isGrandExchangeOpen(){
        if(GrandExchange.getWindowState()  == null){
            General.println("Looks like an error occured - Grand exchange was not open when it expcected to be, retrying");
            return false;
        }
        return true;

    }

}
