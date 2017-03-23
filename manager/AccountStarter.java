package scripts.goldfarmscript.manager;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import scripts.goldfarmscript.api.clanchat.ClanChat;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.constants.Constants;
import scripts.goldfarmscript.utils.Utils;
import scripts.webwalker_logic.WebWalker;


/**
 * Created by Jet on 04/11/2016.
 */
public class AccountStarter {

    public void start(){
        General.useAntiBanCompliance(true);
        ClanChat.openTab();
        General.sleep(250, 500);
        ClanChat.join(Constants.clanChatOwner);
        General.sleep(250, 500);
        Inventory.open();

        if(Utils.getPlayersLevel() <= 10){

            AccountStatus.currentScript = AccountStatus.Status.COMBAT;
            AccountStatus.status = "Starting combat phase";
            Utils.performRandomTaskHC();
            AccountStatus.cowLevelChange = General.random(8,13);
            AccountStatus.strengthToDefenceChange = General.random(7,11);
            AccountStatus.defenceToStrengthChange = General.random(8,15);
        }else {
            moneyIdentifier();
            NewSkillPicker n = new NewSkillPicker();
            n.startNewSkill();

        }

    }
    public void moneyIdentifier(){
        //The script is a bot farming script (IE started on a level 3 account until banned, and repeated)
        //It is not designed to be stopped/started however I have produced some basic code to cater for this
        //Errors may occur however when doing this.
        AccountStatus.status = "This script is NOT designed to be stopped/started - Read output for more info";
        General.println("The bot must now go to the nearest bank to log the amount of gold it has on it, it uses this data to decide \n what skill to do next. This script is not designed to be stopped/started often");
        General.sleep(100,300);
        WebWalking.walkToBank();
        General.sleep(2000,3800);
        if(Banking.isInBank()){
            Banking.openBank();
        }else{
            WebWalker.walkToBank();
            General.println("An error may have occured - Please start the script in the nearrest bank");
            General.sleep(1200,3200);
            Banking.openBank();
        }

        if(Inventory.getAll().length > 0) {
            if (Inventory.getAll().length == 1 && Inventory.getCount("Coins") > 0) {
                General.sleep(50,120);
            } else {
                Banking.depositAll();
            }
        }
        General.sleep(800,3200);
        Banking.withdraw(99999999,"Coins");
        General.println("The script is identifying how many coins the bank has in it to be able to correctly choose the next skill with the right amount of recourse");
        General.sleep(500,1200);
        AccountStatus.amountOfCoinsAccountHas = Inventory.getCount("coins");
        General.sleep(1200,1800);
        if(Inventory.getAll().length>0) {
            Banking.depositAll();
        }
        General.random(500,1200);
        Banking.close();
    }
}
