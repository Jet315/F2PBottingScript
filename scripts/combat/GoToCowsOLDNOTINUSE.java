package scripts.goldfarmscript.scripts.combat;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.utils.Utils;

/**
 * Created by Jet on 18/11/2016.
 */
public class GoToCowsOLDNOTINUSE extends Node {
    private final int CLOSED_GATE_ID = 1558;
    private final int OPEN_GATE_ID = 1559;
    @Override
    public void execute() {
        AccountStatus.status = "Walking to the cow area";
        //Check if in chicken place
        if (new RSTile(3233, 3295).isClickable()) {
            Walking.walkTo(new RSTile(3236, 3295));
            General.sleep(500, 1200);
            RSObject[] gate = Objects.findNearest(4, CLOSED_GATE_ID);
            if (gate.length > 0) {
                DynamicClicking.clickRSObject(gate[0], "Open");
                General.sleep(500, 1200);
            }

        }
        WebWalking.walkTo(new RSTile(3251, 3267));
        General.sleep(1700, 4300);
        if (isGateClosed()) {
            Camera.setCameraAngle(General.random(70, 90));
            Camera.setCameraRotation(General.random(230, 295));
            RSObject[] gate = Objects.findNearest(5, CLOSED_GATE_ID);
            if (gate.length > 0) {
                DynamicClicking.clickRSObject(gate[0], "Open");
                General.sleep(500, 1200);
                Walking.walkTo(new RSTile(3254, 3266));
                General.sleep(300, 1200);
                int shouldCloseGate = Utils.randomSmallNumber();
                if (shouldCloseGate == 1) {
                    RSObject[] openGate = Objects.findNearest(4, OPEN_GATE_ID);
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
        if (Utils.getPlayersLevel() > AccountStatus.cowLevelChange) {
            if (new RSTile(3253, 3267).isClickable() || new RSTile(3256, 3261).isClickable() || new RSTile(3263, 3264).isClickable() || new RSTile(3260, 3276).isClickable() || new RSTile(3251, 5283).isClickable() || new RSTile(3256, 3290).isClickable()){
                return false;
            }else{
                return true;
            }

        }
        return false;
    }
    private boolean isGateClosed(){
        RSObject[] gate = Objects.findNearest(4, CLOSED_GATE_ID);
        if(gate != null && gate.length > 0){
            return true;
        }
        return false;


    }


    }



