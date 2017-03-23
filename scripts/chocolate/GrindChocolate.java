package scripts.goldfarmscript.scripts.chocolate;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.utils.Utils;

/**
 * Created by Jet on 27/12/2016.
 */
public class GrindChocolate extends Node{
    @Override
    public void execute() {
        AccountStatus.status = "Grinding Chocolate";
        RSItem[] chocolateBars = Inventory.find("Chocolate bar");
        if(Banking.isBankScreenOpen()){
            Banking.close();
            General.sleep(500,1800);
        }

        RSItem[] knife = Inventory.find("Knife");

        if(knife.length == 0){
            General.println("No knife detected, banking");

            return;
        }
        //Have to get bot to click on knife, then click the nearest chocolate
        //TODO: Add missclick chance while in process of crafting chocolate

        for(int i = 0; i < chocolateBars.length;i++) {
            knife[0].click("use");
            General.sleep(3,30);
            chocolateBars[chocolateBars.length -1].click();
            General.sleep(20,150);
        }
        General.sleep(80,160);
        //Make the bot on purpose continue clicking the wrong item instead off the exact amount
        if(General.random(0,3) == 1){
            AccountStatus.status = "Purposely creating error";
            RSItem[] chocolateDustArray = Inventory.find("Chocolate dust");
            int totalChocolateDust = Inventory.getCount("Chocolate dust");
            for(int i = 0; i < General.random(1,5);i++) {

                knife[0].click("use");
                General.sleep(3,40);
                //Quick check, as sometimes I get array index out out of bounds which I believe is due to the bot not cashing the inventory, so to (Maybe) prevent this

                if(chocolateDustArray.length > totalChocolateDust) chocolateDustArray[totalChocolateDust-1].click();
                General.sleep(20,130);
            }

        }

        Utils.performRandomTaskVLC();
    }

    @Override
    public boolean validate() {
        //Might have to add check as inventory may contain chocolate bars that are noted
        if(Inventory.find("Chocolate bar").length > 0){
            return true;
        }
        return false;
    }



}
