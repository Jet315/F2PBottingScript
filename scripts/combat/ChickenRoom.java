package scripts.goldfarmscript.scripts.combat;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.utils.Utils;
import scripts.webwalker_logic.WebWalker;

/**
 * Created by Jet on 21/11/2016.
 */
public class ChickenRoom extends Node{
    private final int CLOSED_DOOR_ID = 1535;

    @Override
    public void execute() {
        AccountStatus.status = "Stuck in chicken room - Getting out!";
        if(isDoorClosed()){
            RSObject[] gate = Objects.findNearest(3, CLOSED_DOOR_ID);
            if (gate.length > 0) {
                General.sleep(500,1200);
                DynamicClicking.clickRSObject(gate[0], "Open");
                General.sleep(1900,2800);
                WebWalker.walkTo(new RSTile(3232 + Utils.randomSmallNumber(), 3292 + Utils.randomSmallNumber()));
                General.sleep(300, 1200);
                int shouldCloseGate = Utils.randomSmallNumber();
                if (shouldCloseGate == 1) {
                    RSObject[] openGate = Objects.findNearest(4, CLOSED_DOOR_ID);
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
        if (Utils.getPlayersLevel() <= AccountStatus.cowLevelChange) {
            if (Player.getPosition().distanceTo(new RSTile(3228, 3289)) <= 3) {
                return true;
            }
        }
        return false;
    }

    private boolean isDoorClosed(){
        RSObject[] door = Objects.findNearest(4, CLOSED_DOOR_ID);
        if(door != null && door.length > 0){
            return true;
        }
        return false;


    }

}
