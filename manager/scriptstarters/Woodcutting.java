package scripts.goldfarmscript.manager.scriptstarters;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.WebWalking;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.manager.grandexchange.GrandExchangeBuyer;
import scripts.goldfarmscript.scripts.woodcut.WoodcuttingUtils;

/**
 * Created by Jet on 22/01/2017.
 */
public class Woodcutting {

    public void woodCutterStarter(){
        WoodcuttingUtils utils = new WoodcuttingUtils();
        //See if the bot has woodcutted before (Level is one) if not, buy starter items
        if(utils.getWoodcutLevel() == 1){
            GrandExchangeBuyer b = new GrandExchangeBuyer();
            //See if account has enough for a axe
            if(AccountStatus.amountOfCoinsAccountHas < 200){
                AccountStatus.currentScript = AccountStatus.Status.POTATOPICKER;
                return;
            }
            //Bit weird if multiple accounts always buy the exact same axe, so some will just buy an iron axe, some an iron + steel, and some iron,steel and mithril
            int howManyAxesToBuy = General.random(1,3);
            if(howManyAxesToBuy == 1){
                String[] ironAxe = {"Iron axe"};
                b.grandExchangeBuy(ironAxe);
            }else if(howManyAxesToBuy == 2){
                String[] ironAndSteel = {"Iron axe","Steel axe"};
                b.grandExchangeBuy(ironAndSteel);
            }else if(howManyAxesToBuy == 3 && AccountStatus.amountOfCoinsAccountHas > 1000){
                String[] ironSteelAndMithril = {"Iron axe","Steel axe","Mithril axe"};
                b.grandExchangeBuy(ironSteelAndMithril);
            }else{
                String[] ironAndSteel = {"Iron axe","Steel axe"};
                b.grandExchangeBuy(ironAndSteel);
            }
            General.sleep(300,1200);
            //Close GE if window state is not null (Returns null if no window open)
            if(!(GrandExchange.getWindowState()==null)) {
                GrandExchange.close();
            }

            General.sleep(1200,1800);
            Banking.openBank();
            General.sleep(600,2800);
            if(!Banking.isBankScreenOpen()){
                WebWalking.walkToBank();
                General.sleep(3200,7200);
                Banking.openBank();
            }
            General.sleep(900,2800);
            Banking.depositAllExcept(utils.highestAxe());
            General.sleep(500,1200);


        }



    }
}
