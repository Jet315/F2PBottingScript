package scripts.goldfarmscript.scripts.trade;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import scripts.goldfarmscript.BotFarm;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.constants.Constants;
import scripts.goldfarmscript.manager.grandexchange.GrandExchangeSeller;
import scripts.goldfarmscript.utils.Utils;

/**
 * Created by Jet on 19/01/2017.
 */
public class PrepareForTrade extends Node{
    @Override
    public void execute() {
        //Sell items, get gold, head to area
        AccountStatus.status = "(trade) Preparing for trade (Going to GE to sell bank)";
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
            AccountStatus.status = "(trade) As account is > 18 hours old - Selling all items";
            g.grandExchangeSell(Constants.fullListOfSellItems);
        }else {

            AccountStatus.status = "(trade) As account is < 18 hours old - Selling a limited item list";
            g.grandExchangeSell(Constants.limitedListOfSellItems);
        }

        General.sleep(300,800);

        Banking.openBank();
        General.sleep(500,1200);
        //Withdraw random ammount for each bot
        Banking.withdraw(32423432 +General.random(-2312,34343),"Coins");
        General.sleep(500,1800);
        Banking.close();

        AccountStatus.itemsSold = true;
    }

    @Override
    public boolean validate() {
        if(!AccountStatus.itemsSold){
            return true;
        }
        return false;
    }
}
