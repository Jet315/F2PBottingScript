package scripts.goldfarmscript.scripts.trade;

import org.tribot.api.General;
import org.tribot.api2007.Players;
import org.tribot.api2007.Trading;
import org.tribot.api2007.WorldHopper;
import org.tribot.api2007.types.RSPlayer;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.constants.Constants;
import scripts.goldfarmscript.utils.Utils;


/**
 * Created by Jet on 19/01/2017.
 */
public class TradeRequest extends Node{
    @Override
    public void execute() {
        AccountStatus.status = "(trade) In trade";
        RSPlayer[] findClanChatOwner = Players.findNearest(Constants.clanChatOwner);
        //Check if the owner is arround
        if(findClanChatOwner.length > 0) {
            General.sleep(500,700);
            //Try to click trade with him
            findClanChatOwner[0].click("trade");
            General.sleep(1800,2500);
            //Bot can miss click, if it has return, and it should retrade shortly
            if(Trading.getWindowState() != Trading.WINDOW_STATE.FIRST_WINDOW){
                General.println("Bot missclicked trade option - Will re trade shortly");
                return;
            }
            General.sleep(1200,3200);
            Trading.offer(9999999, "Coins");
            General.sleep(1200, 2200);
            Trading.accept();
            General.sleep(1200, 2200);
            if(Trading.getWindowState() != Trading.WINDOW_STATE.SECOND_WINDOW){
                General.println("Error occurred while on second trading screen, please wait for a re trade");
                return;
            }

            Trading.accept();
            General.sleep(2400, 3500);
            AccountStatus.status = "(trade) trade successful - Resuming script";
            General.println("trade Successful - Bot script resuming (Hopping worlds)");
            //Resetting values
            //May as well set the bot to do PotatoPicker, as currently that is all that he can do (Change possibly in future)
            AccountStatus.currentScript = AccountStatus.Status.POTATOPICKER;
            AccountStatus.amountOfCoinsAccountHas = 0;
            AccountStatus.tradeActive = false;
            AccountStatus.tradeRequestRecieved = false;
            AccountStatus.itemsSold = false;

            WorldHopper.changeWorld(Utils.worldGeneratorFree());
            General.sleep(1400,3200);
        }else{
            return;
        }

    }

    @Override
    public boolean validate() {
        if(AccountStatus.tradeRequestRecieved){
            return true;
        }
        return false;
    }
}
