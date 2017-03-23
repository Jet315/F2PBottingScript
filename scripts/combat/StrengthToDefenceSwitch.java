package scripts.goldfarmscript.scripts.combat;

import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.utils.Utils;

/**
 * Created by Jet on 15/11/2016.
 */
public class StrengthToDefenceSwitch extends Node{
    @Override
    public void execute() {
        AccountStatus.strengthToDefenceChange = 1;
        if(Combat.selectIndex(3)){
            return;

        }else{
            AccountStatus.status = "Switching to defence XP";
            Combat.selectIndex(3);
            General.sleep(500,1800);
            Utils.performRandomTaskHC();
            General.sleep(500,1800);
            Inventory.open();
        }
    }

    @Override
    public boolean validate() {
        if(AccountStatus.strengthToDefenceChange == Skills.getCurrentLevel(Skills.SKILLS.STRENGTH)){
            return true;
        }
        //Check current combat status and level
        return false;
    }
}
