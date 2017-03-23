package scripts.goldfarmscript.manager.grandexchange;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.types.*;
import scripts.goldfarmscript.constants.*;
import scripts.goldfarmscript.constants.Constants;

/**
 * Created by Jet on 28/11/2016.
 */
public class GrandExchangeBuyer {

    //Withdraw money from bank
    //Buy items

    //Maybe boolean to check if can buy items
    public void grandExchangeBulkBuy(String item, int quantity){
        GrandExchangeBuyer ge = new GrandExchangeBuyer();
        //Check if the grand exchange interface is open, if it is must have coins in inventory
        if(GrandExchange.getWindowState() == null) {
            General.sleep(1800, 8000);
            AccountStatus.status = "Walking to GE";
            //Walk to GE if not already there
            WalkToGrandExchange walk = new WalkToGrandExchange();
            walk.walkToGrandExchange();
            General.sleep(1800, 2700);

            General.sleep(2500, 5400);
            AccountStatus.status = "Withdrawing coins from bank";
            withdrawCoins();
            General.sleep(1200,300);
            ge.openGrandExchange();
        }
        General.sleep(1200, 6000);
        //Now grand exchange is open, so start buying items
        AccountStatus.status = "Buying items";
        General.sleep(1200,6000);
        General.sleep(50,1200);
        ge.buyItems(item,quantity);
        General.sleep(500,1200);
        General.sleep(200,1200);
        AccountStatus.amountOfCoinsAccountHas = Inventory.getCount("coins");
        AccountStatus.status = "Successfully bought items - Continuing next process";
        General.sleep(100,1200);



    }
    public void grandExchangeBuy(String[] items) {
        GrandExchangeBuyer ge = new GrandExchangeBuyer();
        //Same as the above, check if grand exchange is open
        if(GrandExchange.getWindowState() == null) {
            General.sleep(1800, 8000);
            AccountStatus.status = "Walking to GE";
            //Walk to GE if not already there (Should be tho)

            WalkToGrandExchange walk = new WalkToGrandExchange();
            walk.walkToGrandExchange();
            General.sleep(6500, 12400);
            AccountStatus.status = "Withdrawing coins from bank";
            withdrawCoins();
            General.sleep(1200,6000);
            ge.openGrandExchange();
        }

        General.sleep(1200, 6000);

        AccountStatus.status = "Buying items";
        General.sleep(50,1200);
        ge.buyItems(items);
        General.sleep(500,1200);
        General.sleep(200,1200);
        AccountStatus.amountOfCoinsAccountHas = Inventory.getCount("coins");
        AccountStatus.status = "Successfully bought items - Continuing next process";
        General.sleep(100,1200);



    }

    public void withdrawCoins(){
        //Check if coins are already in inventory
        if(Inventory.getCount("Coins") == AccountStatus.amountOfCoinsAccountHas){
            return;
        }
        Banking.openBank();

        General.sleep(800,3400);
        if(!Banking.isInBank()){
            WalkToGrandExchange walk = new WalkToGrandExchange();
            walk.walkToGrandExchange();
            General.sleep(2400,4400);
            Banking.openBank();
        }
        if(Inventory.getAll().length >0) {
            if(Inventory.getAll().length == 1 && Inventory.find("Coins").length == 1){
                General.sleep(50,250);
            }else {
                Banking.depositAll();
            }
        }
        General.sleep(500,2000);
        //Make it so it clicks "all" button
        Banking.withdraw(randomBankWithdraw() + General.random(0,1),"Coins");
        General.sleep(600,1200);
        Banking.close();
        General.sleep(600,2000);


    }
    public void buyItems(String item,int quantity) {
        //Im passing through a bunch of items - Not all items may be in inventory, to stop it
        //offering nothing and clicking "collect" - just break out of loop before.
        General.sleep(800, 1800);
        if(item.equalsIgnoreCase("Bucket")){
            GrandExchange.offer(item,Constants.priceOfBucket + General.random(-35,70),quantity,false);
        }else{
            General.sleep(200, 900);
            RSInterfaceChild child = Interfaces.get(Constants.MASTER, Constants.BUY_CHILD);
            RSInterfaceComponent component = child.getChild(Constants.BUY_COMPONENT);
            if(component == null){
                //Obviously could still throw an error, but don't mind to much at the moment (just like all the other place where I do this)
                General.sleep(3200,8700);
            }
            General.sleep(50,350);
            component.click("Create Buy offer");
            General.sleep(500,1800);
            GrandExchange.setItem(item);
            General.sleep(800,2400);
            //Tribot has to wait for a bit until it has cashed the guide price - This method should prevent it from buying ores at 0
           // so doing this is necessary
            GrandExchange.setPrice(GrandExchange.getGuidePrice() + General.random(38,62));
            General.sleep(400,1200);
            GrandExchange.setQuantity(quantity + General.random(-8,2));
            General.sleep(600,1800);
            GrandExchange.confirmOffer();
        }

        General.sleep(1400,4500);
        RSInterfaceChild child = Interfaces.get(Constants.MASTER, Constants.COLLECT_CHILD);
        RSInterfaceComponent component = child.getChild(Constants.COLLECT_COMPONENT);
        if(component == null){
            General.sleep(3200,8700);
        }
        component.click("Collect to inventory");
        General.sleep(200, 2800);
    }


    public void buyItems(String[] items){
        for(String s : items) {
            //Im passing through a bunch of items - Not all items may be in inventory, to stop it
            //offering nothing and clicking "collect" - just break out of loop before.
            if (Inventory.getCount("Coins") > 1500) {
                GrandExchange.offer(s, 1000 + General.random(0, 500), 1, false);
                General.sleep(500, 1200);
            } else {
                GrandExchange.offer(s, Inventory.getCount("Coins"), 1, false);

            }
            General.sleep(2400,7500);
            RSInterfaceChild child = Interfaces.get(Constants.MASTER, Constants.COLLECT_CHILD);
            RSInterfaceComponent component = child.getChild(Constants.COLLECT_COMPONENT);
            if(component == null){
                General.sleep(3200,8700);
            }
            component.click("Collect to inventory");
            General.sleep(800, 2800);

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
            General.println("Error occurred - GrandExchangeSeller line 117 (Retrying)");
            RSNPC[] grandExchangeClerkSecondAttempt = NPCs.find(2148);
            General.sleep(200,400);
            if(grandExchangeClerkSecondAttempt.length > 0){
                grandExchangeClerkSecondAttempt[0].click("Exchange Grand Exchange Clerk");
            }else{
                General.println("Fatal error occurred - GrandExchangeSeller line 126");
            }
            General.sleep(2200,9400);
        }
    }

    public int randomBankWithdraw(){
        int x = General.random(0,4);
        switch (x){
            case 1:
                return 9999999;
            case 2:
                return 99999999;
            case 3:
                return 432324324;
            case 4:
                return 12231123;
            default:
                return 99999999;
        }

    }
    public void walkToClerk(){
        if(General.random(0,1) == 1){
            WebWalking.walkTo(new RSTile(3167,3489 + General.random(0,1)));
        }else{
            WebWalking.walkTo(new RSTile(3162,3489 + General.random(0,1)));
        }
        //Sleep fot a bit, as bot may have to walk to location
        General.sleep(1800,4400);

    }
    public boolean isGrandExchangeOpen(){
        if(GrandExchange.getWindowState()  == null){
            General.println("Looks like an error occured - Grand exchange was not open when it expcected to be, retrying");
            return false;
        }
        return true;

    }

}
