package scripts.goldfarmscript.scripts.trade;

import org.tribot.api.General;
import org.tribot.api2007.WorldHopper;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.api.clanchat.ClanChat;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.constants.Constants;


/**
 * Created by Jet on 19/01/2017.
 */
public class HopWorlds extends Node{
    @Override
    public void execute() {
        AccountStatus.status = "(trade) Changing to required world";
        General.sleep(300,800);
        ClanChat.openTab();
        General.sleep(600,3400);
        WorldHopper.changeWorld(ClanChat.getPlayerWorld(Constants.clanChatOwner));
        General.sleep(1200,2200);
        AccountStatus.status = "(trade) Done - Waiting for incomming trade";
    }

    @Override
    public boolean validate() {
        if(WorldHopper.getWorld() != ClanChat.getPlayerWorld(Constants.clanChatOwner)){
            return true;
        }
        return false;
    }
}
