package scripts.goldfarmscript.scripts.softclay;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSItem;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.constants.Constants;

/**
 * Created by Jet on 15/01/2017.
 */
public class ClayIntoSoftClay extends Node{
    @Override
    public void execute() {

        //Make sure to empty buckets  (will prevent errors from occurring)
        AccountStatus.status = "Turning clay into soft clay";
        int amountOfWaterBuckets = Inventory.getCount("Bucket of water");
        RSItem[] clay = Inventory.find("clay");
        RSItem[] bucketOfWater = Inventory.find("Bucket of water");
        //No need to check if they exist, must do to run method
        clay[0].click();
        General.sleep(50,300);
        bucketOfWater[General.random(0,amountOfWaterBuckets-1)].click();

        General.sleep(350,1300);

        RSInterfaceChild child = Interfaces.get(Constants.MASTER_CLAY, Constants.CLAY_CHILD);

        if(child != null){
            Mouse.moveBox((int)child.getAbsoluteBounds().getMinX(), (int)child.getAbsoluteBounds().getMinY(),(int) child.getAbsoluteBounds().getMaxX(), (int)child.getAbsoluteBounds().getMaxY());
            General.sleep(30,600);
            Mouse.click(3);
            General.sleep(400,1800);
            Timing.waitChooseOption("Make All", 1000);
        }else{
            General.println("Error occurred - Could not find soft clay option on screen (Re trying)");
            General.sleep(1000,2400);
            return;

        }
        //I have checked that this cannot go into infinate loop, and it can't
        while(Inventory.getCount("clay") > 0 && Inventory.getCount("Bucket of water") > 0){
            AccountStatus.status = "Currently turning clay into soft clay";
            General.sleep(600,1200);

        }
        if(Inventory.getCount("Bucket of water") > 0){
            AccountStatus.status = "Removing water from buckets";
            RSItem[] bucketOfWaterThatNeedsEmptying = Inventory.find("Bucket of water");
            for(RSItem bucket:bucketOfWaterThatNeedsEmptying){
                bucket.click("Empty Bucket of water");
                General.sleep(500,1200);
            }

        }


    }

    @Override
    public boolean validate() {
        if(Inventory.find("Clay").length > 0 && Inventory.find("Bucket of water").length == 14){
            return true;
        }
        return false;
    }
}
