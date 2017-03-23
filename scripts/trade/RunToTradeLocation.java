package scripts.goldfarmscript.scripts.trade;

import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.webwalker_logic.WebWalker;

/**
 * Created by Jet on 19/01/2017.
 */
public class RunToTradeLocation extends Node{
    @Override
    public void execute() {
        //Varrock - Possibly add more locations in the future, not needed for now however
        AccountStatus.status = "(trade) Running to required location, and waiting for trade";
        WebWalker.walkTo(new RSTile(3181 + General.random(-1,+2),3425 + General.random(-3,2)));
        General.sleep(3200,6400);

    }

    @Override
    public boolean validate() {
        if(Player.getPosition().getPosition().distanceTo(new RSTile(3181,3425)) > 7){
            return true;
        }
        return false;
    }
}
