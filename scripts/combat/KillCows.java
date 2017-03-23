package scripts.goldfarmscript.scripts.combat;


import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;

import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.utils.Utils;


/**
 * Created by Jet on 27/11/2016.
 */
public class KillCows extends Node {
    @Override
    public void execute() {
        AccountStatus.status = "Killing cows";
        RSNPC cows[] = NPCs.findNearest("cow");
        RSNPC calfs[] = NPCs.findNearest("cow calf");

        closestCow(cows,calfs);
        General.random(50, 600);

        Utils.performRandomTaskLC();
        General.random(1400, 2800);

            }





    @Override
    public boolean validate() {
        //Check if player is <= the cowLevelChange - If so is lower then the lv needed to kill cows
        if (Utils.getPlayersLevel() > AccountStatus.cowLevelChange && !Player.getRSPlayer().isMoving() && !Player.getRSPlayer().isInCombat() && !(Inventory.isFull())) {
            if(Player.getRSPlayer().getPosition().distanceTo(new RSTile(3031,3303)) <=15){
                return true;
            }

        }

        return false;
    }

    public void killCow(RSNPC cow){

        if (DynamicClicking.clickRSNPC(cow, "Attack") && cow.isOnScreen() && !cow.isInCombat()) { //Attack it, just ensure it's on screen again as it could have wondered and not attacked as a player may have attacked it
            General.sleep(3400,6200);

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

    public void closestCow(RSNPC[] cows,RSNPC[] calfs) {
        for (int i = 0; i < cows.length; i++) { //Has to be cow valid in array to move to next step, so no need to check length of array
            if (!cows[i].isOnScreen() || cows[i].isInCombat()) { //Check if  cow is in combat or not on screen
                if (calfs.length > i) { //Has to be calf in array
                    if (calfs[i].isInCombat()) {
                        continue; //If it is continue
                    } else {
                        //Else, the nearest cow is in combat but the calf isn't so let's try to attack it
                        if (calfs[i].isOnScreen()) {
                            //It is on screen, so let's attack it
                            killCow(calfs[i]);
                            break;
                        } else {
                            //Else make sure player is not miles away
                            if (Player.getPosition().distanceTo(calfs[i]) > 13) {
                                AccountStatus.status = "Walking to cows";
                                //Player is miles away from the cow/area so let's walk to it
                                Walking.walkTo(new RSTile(3031 + General.random(-5, 5), 3304 + General.random(-3, 3)));
                                break; //Best to break, as the time it takes the bot to walk there bad idea using current array of animals
                            } else {
                                //Else player is next to cow, but can't see it, so let's just turn our camera to it for now, possible improve this in the future
                                Camera.turnToTile(calfs[i]);
                                killCow(calfs[i]);
                                break;
                            }
                        }


                    }
                }
            }
            //Okay so if it's here cow is not in combat
            if (cows[i].isOnScreen()) {
                killCow(cows[i]);
                break; //Break from loop to gather a new set of cows
            } else {
                if (Player.getPosition().distanceTo(cows[i]) > 13) {
                    AccountStatus.status = "Walking to cows";
                    //Player is miles away from the cow/area so let's walk to it
                    Walking.walkTo(new RSTile(3031 + General.random(-5, 5), 3304 + General.random(-3, 3)));
                    continue;
                } else {
                    //Else player is next to cow, but can't see it, so let's just turn our camera to it for now, possible improve this in the future
                    Camera.turnToTile(cows[i]);
                    killCow(cows[i]);
                    break;
                }
            }
        }
    }



        //Add cows here? - If calfs has nothing in array then nothing will run




    public boolean waitCondition(){ //Okay, so I need a way so when I have clicked the chicken, it doesn't go clicking other chickens while running or even attacking the current chicken...
        // I'll prob get insta banned if it does this, so I need to check if the player is either running or in combat, and return a boolean value based of that
        if(Player.getRSPlayer().isInCombat() || Player.getRSPlayer().isMoving() || Combat.getAttackingEntities().length > 0 ){
            return false;
        }
        return true;

    }

}
