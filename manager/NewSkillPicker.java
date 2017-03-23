package scripts.goldfarmscript.manager;

import org.tribot.api.General;
import scripts.goldfarmscript.constants.AccountStatus;

/**
 * Created by Jet on 28/11/2016.
 */
public class NewSkillPicker {

    public void startNewSkill(){
        ScriptStarterAndFinisher s = new ScriptStarterAndFinisher();
       int random = General.random(2,5);
        switch (random){
            case 1:
                AccountStatus.currentScript = AccountStatus.Status.MINING;
                AccountStatus.status = "Starting MINING script";
                s.startScript();
                break;
            case 2:
                AccountStatus.currentScript = AccountStatus.Status.WOODCUTTING;
                AccountStatus.status = "Starting WOODCUTTING script";
                s.startScript();
                break;
            case 3:
                AccountStatus.currentScript = AccountStatus.Status.SOFTCLAY;
                AccountStatus.status = "Starting SOFTCLAY script";
                s.startScript();
                break;
            case 4:
                AccountStatus.currentScript = AccountStatus.Status.CHOCOLATEGRINDER;
                AccountStatus.status = "Starting CHOCLATEGRINDER script";
                s.startScript();
                break;
            case 5:
                AccountStatus.currentScript = AccountStatus.Status.POTATOPICKER;
                AccountStatus.status = "Starting POTATOPICKER script";
                s.startScript();
                break;
            case 6:
                //Need at least 18 hour ingame time to do this one really
                AccountStatus.currentScript = AccountStatus.Status.TANNING;
                AccountStatus.status = "Starting TANNING script";
                s.startScript();
                break;
            default:
                AccountStatus.currentScript = AccountStatus.Status.TANNING;
                AccountStatus.status = "Starting TANNING script";
                General.println("Default occurred - NewSkillPicker class - Error");
                s.startScript();
                break;
        }


    }

    // IE if 50 is passed through, 1/50 chance
    public void shouldChangeSkill(int chanceOfChanging){
        if(General.random(1,chanceOfChanging) == 1){
            AccountStatus.status = "Switching scripts - Taking a small break";
            General.sleep(1500,18500);
            ScriptStarterAndFinisher s = new ScriptStarterAndFinisher();
            s.finishScript();
        }



    }

}
