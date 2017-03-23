package scripts.goldfarmscript.scripts.chocolate;

import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;

/**
 * Created by Jet on 27/12/2016.
 */
public class WalkToBank extends Node{
    @Override
    public void execute() {
        AccountStatus.status = "Walking to bank to start Chocolate Grinding script!";
        WebWalking.getUseRun();
        General.sleep(100,800);
        scripts.webwalker_logic.WebWalker.walkTo(new RSTile(3167,3489 + General.random(-1,2)));
        General.sleep(1800,2700);
        }

    @Override
    public boolean validate() {
        //Grand Exchange Area
        if(!(Player.getPosition().distanceTo(new RSTile(3167,3489)) < 10)){
            return true;

        }
        return false;
    }
}
