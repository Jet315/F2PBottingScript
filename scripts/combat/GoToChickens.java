package scripts.goldfarmscript.scripts.combat;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.utils.Utils;
import scripts.webwalker_logic.WebWalker;

/**
 * Created by Jet on 14/11/2016.
 */
public class GoToChickens extends Node{
    private final int CLOSED_GATE_ID = 1558;
    private final int OPEN_GATE_ID = 1559;

    @Override
    public void execute() {
        // walk to chickens (Just outside
        // Check fence is not closed, if so open move camera to fence
        // Walk +- 2 to an exact tile inside move camera
        AccountStatus.status = "Walking to the chickens";
        if(!(new RSTile(3238,3295).isClickable())){
            WebWalker.walkTo(new RSTile(3238+General.random(-1,1),3295+General.random(-1,1)));
            General.sleep(1800,2300);

        if(isGateClosed()) {
            Camera.setCameraAngle(General.random(40, 80));
            Camera.setCameraRotation(General.random(40, 170));
            RSObject[] gate = Objects.findNearest(3, CLOSED_GATE_ID);
            if (gate.length > 0) {
                DynamicClicking.clickRSObject(gate[0], "Open");
                General.sleep(500,1200);
                WebWalker.walkTo(new RSTile(3236+General.random(-1,1), 3296+General.random(-1,1)));
                General.sleep(300, 1200);
                int shouldCloseGate = Utils.randomSmallNumber();
                if (shouldCloseGate == 1) {
                    RSObject[] openGate = Objects.findNearest(4, OPEN_GATE_ID);
                    if (openGate.length > 0) {
                        DynamicClicking.clickRSObject(openGate[0], "Close");
                        General.sleep(300, 1200);
                    }
                }
                WebWalker.walkTo(new RSTile(3233 + Utils.randomSmallNumber(), 3296 + Utils.randomSmallNumber()));
                General.sleep(400, 1800);
                Inventory.open();
                RSItem[] sword = Inventory.find(1277);
                General.sleep(10, 300);
                if (sword.length >= 1) {
                    sword[0].click("wield");
                    General.sleep(10, 300);
                }


            } else {
                WebWalker.walkTo(new RSTile(3234, 3295));
                General.println("Error occured however not fatal (GoToChickens class, (if(gate.length>0) else occured");
            }
        }
        }
        Walking.walkTo(new RSTile(3233 + Utils.randomSmallNumber(),3295 + Utils.randomSmallNumber()));
        General.sleep(400,1800);
        Inventory.open();
        RSItem[] sword =  Inventory.find(1277);
        General.sleep(10,300);
        if(sword.length >= 1) {
            sword[0].click("wield");
            General.sleep(10, 300);
        }






    }

    @Override
    public boolean validate() {
        if (Utils.getPlayersLevel() <= AccountStatus.cowLevelChange) {
            if(Player.getPosition().distanceTo(new RSTile(3239,3295)) <= 2){
                return true;
            }
            //Check if at starting tile
            if(Player.getPosition().distanceTo(new RSTile(3233, 3230)) <= 1 || Player.getPosition().distanceTo(new RSTile(3235,3295)) <=10){
                return false;
            }else {
                //Else lower than level 10, and not at starting area and not at chicken area

                return true;
            }

        } else {
            return false;
        }
    }

    private boolean isGateClosed(){
        RSObject[] gate = Objects.findNearest(4, CLOSED_GATE_ID);
        if(gate != null && gate.length > 0){
            return true;
        }
        return false;


    }

}
