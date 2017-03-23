package scripts.goldfarmscript.manager;

import org.tribot.api.General;
import org.tribot.api2007.*;
import scripts.goldfarmscript.BotFarm;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.constants.Constants;
import scripts.goldfarmscript.manager.grandexchange.GrandExchangeSeller;
import scripts.goldfarmscript.manager.scriptstarters.ChocolateGrinder;
import scripts.goldfarmscript.manager.scriptstarters.PotatoPicker;
import scripts.goldfarmscript.manager.scriptstarters.SoftClay;
import scripts.goldfarmscript.manager.scriptstarters.Woodcutting;
import scripts.goldfarmscript.utils.Utils;

/**
 * Created by Jet on 20/12/2016.
 */
public class ScriptStarterAndFinisher {


    //In this method, walks to GE - Sells items
    public void finishScript(){
        AccountStatus.status = "Walking to GE to sell items";
        if(Banking.isBankScreenOpen()){
            Banking.close();
        }
        GrandExchangeSeller g = new GrandExchangeSeller();
        //Get time ran, if > 18 hours the bot is able to sell all items at GE
        long startTime = BotFarm.startTime;
        long timeRan = System.currentTimeMillis() - startTime;
        int timeRanInHours = (int) (timeRan / (60*60*1000));

        //Check if account is > 18 old or has 7 Quest points (Can sell if has 7 QP)
        if(timeRanInHours >= 18 || Utils.getQuestPoints() >= 7){
            AccountStatus.status = "As account is > 18 hours old - Selling all items";
            g.grandExchangeSell(Constants.fullListOfSellItems);
        }else {

            AccountStatus.status = "As account is < 18 hours old - Selling a limited item list";
            g.grandExchangeSell(Constants.limitedListOfSellItems);
        }
        GrandExchange.close();
        AccountStatus.status = "Picking new skill";
        NewSkillPicker n = new NewSkillPicker();
        n.startNewSkill();
    }


    //TODO: When I create more scripts that need no requirments, change the bits in the starter classes when it auto picks potato picker
    //In this method if needed, will walk to GE to buy items
    //Make sure contains code to check if can buy items
    public void startScript(){

        AccountStatus.status = "Getting starter items for new script";

        //Switch statement to determind what the current script is, then the script will buy the items.
        //From the previous task it has done, money will be in inventory so must bank before starting script
        switch (AccountStatus.currentScript) {
            case POTATOPICKER:
                AccountStatus.status = "No required items for POTATOPICKER - Proceeding to start script";
                PotatoPicker potato = new PotatoPicker();
                potato.potatoPickerStarter();
                break;

            case CHOCOLATEGRINDER:
                AccountStatus.status = "Getting required items for CHOCOLATEGRINDER";
                ChocolateGrinder chocolate = new ChocolateGrinder();
                chocolate.chocolateGrinderStarter();
                break;

            case SOFTCLAY:
                AccountStatus.status = "Getting required items for SOFTCLAY";
                SoftClay clay = new SoftClay();
                clay.softClayStater();
                break;
            case WOODCUTTING:
                AccountStatus.status = "Getting required items for WOODCUTTING";
                Woodcutting woodcut = new Woodcutting();
                woodcut.woodCutterStarter();
                break;
            default:
                AccountStatus.status ="Error occurred - Look in logs for more detail";
                General.println("Switch Default occurred - manger.NewSkillPicker.startScript");
                General.println("Script that this occured on was " + AccountStatus.currentScript);
                General.sleep(5000,20000);
                General.println("Bot unsure what to do - Starting POTATOPICKER script");
                AccountStatus.currentScript = AccountStatus.Status.POTATOPICKER;
                break;

        }
    }


}
