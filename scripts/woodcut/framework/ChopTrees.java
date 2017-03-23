package scripts.goldfarmscript.scripts.woodcut.framework;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.scripts.woodcut.WoodcuttingUtils;
import scripts.goldfarmscript.utils.Utils;

/**
 * Created by Jet on 22/01/2017.
 */
public class ChopTrees extends Node {
    @Override
    public void execute() {
        AccountStatus.status = "Finding tree to chop";
        final RSObject[] tree = Objects.findNearest(11, WoodcuttingUtils.currentTree());
        General.sleep(50, 250);
        if (tree.length == 0) {
            //No nearby trees, sleep then return
            General.sleep(80, 320);
            return;
        }
        //Far oak tree
        if (WoodcuttingUtils.currentTree() == "Oak") {
            if (tree[0].getPosition() == new RSTile(3166, 3413)) {
                General.sleep(500, 1300);
                Walking.walkTo(new RSTile(3166 + General.random(-2, 1), 3412 + General.random(-1, 2)));
                General.sleep(300, 800);
            }
        }
        if (!tree[0].isOnScreen()) {
            Utils.turnToTile(tree[0]);
            General.sleep(50, 180);
        }
        if (!(tree[0] == null)) {
            if (Player.getAnimation() == -1) {
                if (DynamicClicking.clickRSObject(tree[0], "Chop down")) {
                    AccountStatus.status = "Chopping Tree";
                    General.sleep(2200,4400);
                    //TODO: Random chance of hovering over next tree

                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            return waitCondition();
                        }

                    }, General.random(2200, 4800)); //Higher timeout incase we are walking
                }

                Utils.performRandomTaskVLC();

            }
        }
    }


    @Override
    public boolean validate() {
        if (!(Inventory.isFull()) && WoodcuttingUtils.isInTreeLocation()) {
            return true;
        }
        return false;
    }

    public boolean waitCondition() { //Okay, so I need a way so when I have clicked the chicken, it doesn't go clicking other chickens while running or even attacking the current chicken...
        // I'll prob get insta banned if it does this, so I need to check if the player is either running or in combat, and return a boolean value based of that
        if (!Player.getRSPlayer().isMoving() && Player.getAnimation() > -1) {
            return false;
        }
        return true;
    }
}
