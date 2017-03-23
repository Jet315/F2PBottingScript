package scripts.goldfarmscript.scripts.combat;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Combat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.utils.Utils;

/**
 * Created by Jet on 15/11/2016.
 */
public class KillChickens extends Node{
    @Override
    public void execute() { //TODO: Add hover over next chicken method
        AccountStatus.status = "Killing chickens";

        RSNPC[] chickens = NPCs.findNearest("Chicken");
        //Pass chickens array to this method which finds the closest chicken, then calls the killChicken method which attacks it
        closestChickenToAttack(chickens);


        Utils.performRandomTaskLC();
        General.random(1600, 2400);
    }



    @Override
    public boolean validate() {
        //Check if player is <= the cowLevelChange - If so is lower then the lv needed to kill cows
        if (Utils.getPlayersLevel() <= AccountStatus.cowLevelChange && !Player.getRSPlayer().isMoving() && !Player.getRSPlayer().isInCombat()) {
            if(Player.getPosition().distanceTo(new RSTile(3235,3295)) <=10){
                return true;
            }

        }

        return false;
    }


    public void killChicken(RSNPC chicken){

        if (DynamicClicking.clickRSNPC(chicken, "Attack") && chicken.isOnScreen() && !chicken.isInCombat()) { //Attack it, just ensure it's on screen again as it could have wondered and not attacked as a player may have attacked it
            General.sleep(2400,5200);

                Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    return waitCondition();
                }
                //public boolean active() { This one doesn't seem to work very well, as if it is running to the chicken it will stick click places
                 //   return Combat.getAttackingEntities().length > 0;
                //}
            }, General.random(2200, 4200));
        }
    }

    public void closestChickenToAttack(RSNPC[] chickens) {
        for (int i = 0; i < chickens.length; i++) { //Has to be chicken valid in array to move to next step, so no need to check length of array
                if(chickens[i].isInCombat()){ //Check if first one is in combat, if it is continue... etc
                    continue;
                }
                if(chickens[i].isOnScreen()){ //If on screen - Attack method!
                    killChicken(chickens[i]);
                    break; //Break from loop to gather a new set of chickens, right?
                }else { //Seems like the bot has been wondering a bit far... Let's turn the camera to the correct area
                    //For some reason, even if it's on the screen it still seems to turn.. I think the API method that returns this must be for a really small screen?
                    //Just to decrease the amount it turns:
                    int shouldTurn = General.random(1, 5);
                    if (shouldTurn == 1) {
                        Camera.turnToTile(new RSTile(3230 + Utils.randomMediumNumber(), 3298 + Utils.randomSmallNumber()));
                        killChicken(chickens[i]); //Then attack the chicken!
                        break; //Break from loop to gather a new set of chickens, right?
                 }
                    continue;
                }

        }


    }

    public boolean waitCondition(){ //Okay, so I need a way so when I have clicked the chicken, it doesn't go clicking other chickens while running or even attacking the current chicken...
                                    // I'll prob get insta banned if it does this, so I need to check if the player is either running or in combat, and return a boolean value based of that
        if(Player.getRSPlayer().isInCombat() || Player.getRSPlayer().isMoving() || Combat.getAttackingEntities().length > 0 ){
            return false;
        }
        return true;

    }


}
