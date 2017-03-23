package scripts.goldfarmscript.scripts;

import org.tribot.api.General;
import scripts.goldfarmscript.api.Node;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.scripts.trade.HopWorlds;
import scripts.goldfarmscript.scripts.trade.PrepareForTrade;
import scripts.goldfarmscript.scripts.trade.RunToTradeLocation;
import scripts.goldfarmscript.scripts.trade.TradeRequest;
import scripts.goldfarmscript.scripts.chocolate.Bank;
import scripts.goldfarmscript.scripts.chocolate.GrindChocolate;
import scripts.goldfarmscript.scripts.combat.*;
import scripts.goldfarmscript.scripts.potato.PickPotatos;
import scripts.goldfarmscript.scripts.potato.WalkToBank;
import scripts.goldfarmscript.scripts.potato.WalkToFields;
import scripts.goldfarmscript.scripts.softclay.ClayIntoSoftClay;
import scripts.goldfarmscript.scripts.softclay.FillWaterBuckets;
import scripts.goldfarmscript.scripts.woodcut.framework.ChopTrees;
import scripts.goldfarmscript.scripts.woodcut.framework.WalkToTrees;
import scripts.goldfarmscript.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jet on 06/11/2016.
 */
public class Scripts {
    private final ArrayList<Node> nodes = new ArrayList<>();

    public void loop() {
        //TODO: Change to switch soon, prob neater
        if(AccountStatus.currentScript == AccountStatus.Status.COMBAT){
            if(Utils.getPlayersLevel() <= AccountStatus.cowLevelChange) {
                Collections.addAll(nodes, new CombatStart(), new GoToChickens(), new KillChickens(), new StrengthToDefenceSwitch(), new DefenceBackToStrengthSwitch(), new ChickenRoom());
            }else {
                Collections.addAll(nodes, new WalkToGE(),new GoToCows(),new KillCows(), new PickupCowHides());
            }

                for (final Node node : nodes) {
                    if (node.validate()) {
                        node.execute();
                        General.sleep(30,70);	//time in between executing nodes
                    }
                }
                return;
            }else if(AccountStatus.currentScript == AccountStatus.Status.POTATOPICKER){

            Collections.addAll(nodes, new WalkToFields(), new WalkToBank(), new PickPotatos());

            for (final Node node : nodes){
                if (node.validate()) {
                    node.execute();
                    General.sleep(30,70);	//time in between executing nodes
                }
            }
            return;

            }else if(AccountStatus.currentScript == AccountStatus.Status.CHOCOLATEGRINDER){
            Collections.addAll(nodes, new Bank(), new scripts.goldfarmscript.scripts.chocolate.WalkToBank(), new GrindChocolate());

            for (final Node node : nodes){
                if (node.validate()) {
                    node.execute();
                    General.sleep(30,70);	//time in between executing nodes
                }
            }
            return;

        }else if(AccountStatus.currentScript == AccountStatus.Status.SOFTCLAY){
            Collections.addAll(nodes,new scripts.goldfarmscript.scripts.softclay.WalkToBank(), new scripts.goldfarmscript.scripts.softclay.Bank(), new ClayIntoSoftClay(),new FillWaterBuckets());
            for (final Node node : nodes){
                if (node.validate()) {
                    node.execute();
                    General.sleep(30,70);	//time in between executing nodes
                }
            }
            return;
        }else if(AccountStatus.currentScript == AccountStatus.Status.WOODCUTTING){
            Collections.addAll(nodes, new scripts.goldfarmscript.scripts.woodcut.framework.Bank(), new ChopTrees(),new WalkToTrees());
            for (final Node node : nodes){
                if (node.validate()) {
                    node.execute();
                    General.sleep(30,70);	//time in between executing nodes
                }
            }
            return;
        }
        }

    //Using seperating looping method for trading, so it doesn't mess up the other scripts (IE, wont know what script to go back, where it's at etc)
    public void loopTrade(){
        Collections.addAll(nodes,new PrepareForTrade(), new RunToTradeLocation(), new HopWorlds(),new TradeRequest());
        for (final Node node : nodes){
            if (node.validate()) {
                node.execute();
                General.sleep(30,70);	//time in between executing nodes
            }
        }
        return;

    }





}
