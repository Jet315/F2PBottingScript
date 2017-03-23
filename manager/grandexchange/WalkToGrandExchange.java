package scripts.goldfarmscript.manager.grandexchange;

import org.tribot.api2007.Player;
import scripts.goldfarmscript.constants.Constants;

/**
 * Created by Jet on 27/01/2017.
 */
public class WalkToGrandExchange {

    public void walkToGrandExchange(){
        //Check if already next to the grand exchange
        if(Constants.grandExchangeArea.contains(Player.getPosition())){
            return;
        }else{
            //Player is not at grand exchange, so we need to go there
            //(External web walking API I'm using)
            scripts.webwalker_logic.WebWalker.walkTo((Constants.grandExchangeArea.getRandomTile()));
        }


    }
}
