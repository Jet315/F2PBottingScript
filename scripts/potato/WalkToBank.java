package scripts.goldfarmscript.scripts.potato;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.constants.Constants;
import scripts.goldfarmscript.manager.NewSkillPicker;
import scripts.goldfarmscript.utils.Utils;

/**
 * Created by Jet on 04/12/2016.
 */
public class WalkToBank extends Node{
    private final int CLOSED_GATE_ID = 1558;
    @Override
    public void execute() {
        AccountStatus.status = "Walking to bank";

        if(Player.getPosition().distanceTo(new RSTile(3146,3268)) < 12 && !(Player.getPosition().distanceTo(new RSTile(3167,3490)) < 5)){
            scripts.webwalker_logic.WebWalker.walkTo(new RSTile(3167 + General.random(-2,2), 3277 + General.random(-2,3)));
            General.sleep(2400,4200);
        }
        if(Player.getPosition().distanceTo(new RSTile(3146,3273)) < 9){
            scripts.webwalker_logic.WebWalker.walkTo(new RSTile(3147 + General.random(-1,2),3282 + General.random(-2,2)));
            General.sleep(1400,3200);
        }

        General.sleep(200,3200);
        if(Player.getPosition().distanceTo(new RSTile(3146,3282)) < 15){
            scripts.webwalker_logic.WebWalker.walkTo(new RSTile(3145+ General.random(-2,2),3289 + General.random(-1,2)));
            General.sleep(1200,2800);
        }
            RSObject[] gate = Objects.findNearest(10, CLOSED_GATE_ID);
            if(gate.length > 0){
                if(!gate[0].isOnScreen()) Utils.turnToTile(gate[0]);
                General.sleep(500,1800);
                DynamicClicking.clickRSObject(gate[0], "Open");
                General.sleep(200,1800);
            }
        scripts.webwalker_logic.WebWalker.walkTo(new RSTile(3094 + General.random(-2,1),3244 + General.random(-3,2)));
            General.sleep(1800,3200);
            if(!Banking.isInBank()){
                //Walk to bank tile
                WebWalking.walkTo(Constants.potatoBank.getRandomTile());


            }
            General.sleep(800,3200);
            Banking.openBank();
            General.sleep(80,800);
            Banking.depositAll();
            General.sleep(100,1200);
            RSItem[] numberOfPotatos = Banking.find("Potato");
            AccountStatus.status = "Deciding whether to change skill";
            Banking.close();
            //Slightly better calculated way of changing skill, rather than just 1 in 5 chance

            //.length returns 0 if no potatos, 1 if potatos
            if(numberOfPotatos.length > 0){
                if(numberOfPotatos[0].getStack() <= 75){
                    NewSkillPicker n = new NewSkillPicker();
                    //1 in 7 chance of switching
                    n.shouldChangeSkill(7);
                }else if(numberOfPotatos[0].getStack() > 75 && numberOfPotatos[0].getStack() < 150){
                    NewSkillPicker n = new NewSkillPicker();
                    //1 in 4 chance of switching
                    n.shouldChangeSkill(4);
                }else{
                    NewSkillPicker n = new NewSkillPicker();
                    //1 in 3 chance of switching
                    n.shouldChangeSkill(3);
                }
            }else {
                General.sleep(100, 3200);
                NewSkillPicker n = new NewSkillPicker();
                //1 in 8 chance of switching
                n.shouldChangeSkill(8);
            }


        Utils.performRandomTaskLC();
        //Bank all items including equipment
    }

    @Override
    public boolean validate() {
        if(Inventory.isFull()){
            return true;
        }
        return false;
    }
}
