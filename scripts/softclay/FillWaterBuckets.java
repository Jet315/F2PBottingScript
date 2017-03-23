package scripts.goldfarmscript.scripts.softclay;

import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.utils.Utils;

/**
 * Created by Jet on 15/01/2017.
 */
public class FillWaterBuckets extends Node{
    //Tried documenting the first bit off this class as a friend was wondering what I was doing

    private int WELL_ID = 884; //Well ID, just like what the bot needs to look for it
    @Override
    public void execute() { // Like a Python function, we call this when we want to fill up the water buckets lol

        AccountStatus.status = "Filling up water buckets"; //Display message to tell user
        WebWalking.getUseRun(); //This just sets our bot to run mode

        //Walk to the Well ID tile (Game made up of tiles), and add some random numbers so it doesn't go to the EXACT same tile 		 everytime to prevent it getting banned
        WebWalking.walkTo(new RSTile(3084 + General.random(-1,3),3502 + General.random(-2,+2)));


        General.sleep(1500,3400); //Sleep, so pause for 1.5 seconds to 3.4 seconds (Anti ban again)

        RSItem[] bucket = Inventory.find("Bucket"); //Look in inventory for any items called "Bucket" - Save to array list
        bucket[0].click("use");  // Click on the first bucket in the array list
        RSObject[] well = Objects.findNearest(6, WELL_ID); //Now search (6 tiles away from player as in that method) for the well
        if(well.length > 0){ //Have to do a check to make sure a well does exist in the array, so there is one nearby
            if(!well[0].isOnScreen()){ // Checks if it is not on screen, if it is not then:
                Camera.turnToTile(well[0]); //Turn camera to the well
                General.sleep(100,1800);
            }
            well[0].click(); //Finally, click on the well to fill the buckets
            AccountStatus.status = "Successfully filling up water buckets";
        }else{
            General.println("Error occured (Else), softclay.FillWaterBuckets - Re walking to well");
            WebWalking.walkTo(new RSTile(3084 + General.random(-1,3),3502 + General.random(-2,+2)));

        }
        General.sleep(4800,12000);
        Utils.performRandomTaskLC();

        //No need to check if array list as to get to this method 14 buckets must exist in inventory




    }

    @Override
    public boolean validate() {
        //Make sure bot is waiting for buckets to be refilled before filling up buckets, can do this by checking if it has normal clay in inventory
        if(Inventory.find("Bucket").length == 14 && Inventory.find("Clay").length > 0){
            return true;
        }
        return false;
    }
}
