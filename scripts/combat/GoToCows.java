package scripts.goldfarmscript.scripts.combat;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.utils.Utils;
import scripts.webwalker_logic.WebWalker;

/**
 * Created by Jet on 27/11/2016.
 */
public class GoToCows extends Node {
    private final int CLOSED_GATE_ID = 1558;
    private final int OPEN_GATE_ID = 1559;
    private final int CLOSED_GATE_ID_COWS = 1562;
    private final int OPEN_GATE_ID_COWS = 1564;
    private RSTile walkToCowGate[] = {new RSTile(3011,3314),new RSTile(3018,3317),new RSTile(3022,3318),new RSTile(3027,3317),new RSTile(3032,3319), new RSTile(3033,3316)};

    @Override
    public void execute() {
        AccountStatus.status = "Walking to the cow area";
        //Check if in chicken place
        if (new RSTile(3233, 3295).isClickable()) {
            WebWalker.walkTo(new RSTile(3236+General.random(-1,1), 3295 + General.random(-1,1)));
            General.sleep(500, 1200);
            RSObject[] gate = Objects.findNearest(4, CLOSED_GATE_ID);
            if (gate.length > 0) {
                DynamicClicking.clickRSObject(gate[0], "Open");
                General.sleep(500, 1200);
            }

        }
        //TODO: Sort duped code out here
        if(new RSTile(3032,3316).isClickable()){
            //Stuck just outside
            if(isGateClosed()){
                Camera.setCameraAngle(General.random(70, 90));
                Camera.setCameraRotation(General.random(230, 295));
                RSObject[] gate = Objects.findNearest(7, CLOSED_GATE_ID_COWS);
                if (gate.length > 0) {
                    DynamicClicking.clickRSObject(gate[0], "Open");
                    General.sleep(1400, 2800);
                    WebWalker.walkTo(new RSTile(3031 + Utils.randomSmallNumber(), 3312));
                    General.sleep(1200, 3200);
                    int shouldCloseGate = Utils.randomSmallNumber();
                    if (shouldCloseGate == 1) {
                        RSObject[] openGate = Objects.findNearest(5, OPEN_GATE_ID_COWS);
                        if (openGate.length > 0) {
                            DynamicClicking.clickRSObject(openGate[0], "Close");
                            General.sleep(300, 1200);
                        }
                    }
                }



            }
            //Walk to middle of cow area
            WebWalker.walkTo(new RSTile(3032 + General.random(-3,3),3306 + General.random(-3,2)));
            return;


        }
        WebWalker.walkTo(new RSTile(3007 + Utils.randomSmallNumber(), 3313 + Utils.randomMediumNumber()));
        General.sleep(2300, 4300);

        Walking.walkPath(Walking.randomizePath(walkToCowGate,2,2));
        General.sleep(8500,11500);
        if (isGateClosed()) {
            Camera.setCameraAngle(General.random(70, 90));
            Camera.setCameraRotation(General.random(230, 295));
            RSObject[] gate = Objects.findNearest(7, CLOSED_GATE_ID_COWS);
            if (gate.length > 0) {
                DynamicClicking.clickRSObject(gate[0], "Open");
                General.sleep(1400, 2800);
                WebWalker.walkTo(new RSTile(3031 + Utils.randomSmallNumber(),3312 + General.random(-1,1)));
                General.sleep(1200,3200);
                int shouldCloseGate = Utils.randomSmallNumber();
                if (shouldCloseGate == 1) {
                    RSObject[] openGate = Objects.findNearest(5, OPEN_GATE_ID_COWS);
                    if (openGate.length > 0) {
                        DynamicClicking.clickRSObject(openGate[0], "Close");
                        General.sleep(300, 1200);
                    }
                }
            }
        }

    }

    @Override
    public boolean validate() {
        if (Utils.getPlayersLevel() > AccountStatus.cowLevelChange && !(Inventory.isFull())) {
            if(AccountStatus.currentScript != AccountStatus.Status.COMBAT){
                return false;
            }
            if(Player.getPosition().distanceTo(new RSTile(3032,3316)) <= 2){
                return true;
            }
            if (new RSTile(3037, 3310).isClickable() || new RSTile(3037, 3304).isClickable() || new RSTile(3034, 3301).isClickable() || new RSTile(3031, 3299).isClickable() || new RSTile(3026, 3301).isClickable() || new RSTile(3026, 3309).isClickable()){
                return false;
            }else{
                return true;
            }

        }
        return false;
    }
    private boolean isGateClosed(){
        RSObject[] gate = Objects.findNearest(4, CLOSED_GATE_ID_COWS);
        if(gate != null && gate.length > 0){
            return true;
        }
        return false;


    }


}



