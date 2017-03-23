package scripts.goldfarmscript.manager;
import org.tribot.api.General;
import  org.tribot.api2007.Banking;
import org.tribot.api2007.WebWalking;
import scripts.goldfarmscript.constants.AccountStatus;

/**
 * Created by Jet on 14/11/2016.
 */
public class Bank {


    public void depositItems(int itemsNotToBank[]){
        AccountStatus.status = "Depositing Items";

        if(!Banking.isInBank()){ // If bot is not in bank, walk to bank
            WebWalking.walkToBank();
            General.sleep(500,1200); //Walk to the bank and wait a little bit (about a second)
        }
        Banking.openBankBooth(); //Open banker
        General.sleep(500,1200);
        Banking.depositAllExcept(itemsNotToBank);  //Deposit items except those that the bot shouldn't
        General.sleep(50,500);
        Banking.close(); //Close bank

    }
}
