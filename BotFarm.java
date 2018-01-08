package scripts.goldfarmscript;

import org.tribot.api.Timing;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.Painting;

import java.awt.*;

import org.tribot.api.General;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Starting;
import scripts.goldfarmscript.constants.AccountStatus;
import scripts.goldfarmscript.constants.Constants;
import scripts.goldfarmscript.manager.AccountStarter;
import scripts.goldfarmscript.scripts.Scripts;

/**
 * Created by Jet on 03/11/2016.
 */
@ScriptManifest(authors = { "Lugia1" }, category = "Botfarm", name = "AdvancedBotfarmScript", version = 1.00, description = "BotFarm")
public class BotFarm extends Script implements MessageListening07,Painting,Starting {

    /**
    *Stores the start time, in milliseconds
    **/
    public static final long startTime = System.currentTimeMillis();
    /**
    *Stores the standard font used for the text interface
    **/
    Font font = new Font("Verdana", Font.BOLD, 14);


    @Override
    public void run() {
        while (true) {
            /**
            *This script was made over multiple months
            **/
            //TODO: if variable deciding if account is banned - change account (MySQL database)

            Scripts s = new Scripts();
            //Check if trade is active, if so loop the trade node framework, else loop the normal - I do different loops as else it will go into the trade loop and wont know what loop to go back to
            if(AccountStatus.tradeActive){
                s.loopTrade();
                //May as well sleep for a bit longer to prevent high CPU usage. No need to loop through quickly
                General.sleep(300,850);
            }else {

                s.loop();

            }
            //If during the loop the process does not sleep, it will loop through using all the processing power
            //By sleeping here, it prevents that
            General.sleep(10, 30);
        }

    }
    //Start methods are called using onStart method - called at the start
    @Override
    public void onStart() {
        AccountStarter ac = new AccountStarter();
        ac.start();
    }


    @Override
    public void serverMessageReceived(String s) {

    }

    @Override
    public void playerMessageReceived(String s, String s1) {

    }

    @Override
    public void duelRequestReceived(String s, String s1) {

    }

    @Override
    public void clanMessageReceived(String player, String message) {
        //Check if player is the owner
        //In future possibly add location support. For now tho there is no point as just me using the script
        if(player.equalsIgnoreCase(Constants.clanChatOwner)){
            AccountStatus.tradeActive = true;

            General.println("Getting ready to start trade with " + player);

        }
    }

    @Override
    public void tradeRequestReceived(String player) {
        //When trade requst is recieved set a boolean to true and do task framework for that, otherwise it will run and trade would not have completed
        if(player.equalsIgnoreCase(Constants.clanChatOwner)){
            AccountStatus.tradeRequestRecieved = true;
        }
    }

    @Override
    public void personalMessageReceived(String s, String s1) {

    }

    @Override
    public void onPaint(Graphics g) {
        g.setFont(font);
        g.setColor(new Color(44, 44, 44));
        long timeRan = System.currentTimeMillis() - startTime;
        g.drawString("Runtime " + Timing.msToString(timeRan), 220, 390);
        g.drawString("Current Script Running:" + AccountStatus.currentScript, 220, 405);
        g.drawString("Status: " + AccountStatus.status, 220, 420);
    }


}

// TODO: Overtime add more scripts, ideas bellow
/*
Jug Filling script
WoodcuttingUtils script
Mining script
Fishing script

 */



// Rough plan
// Go to GE and sell 
// Then it will start a new script
// A method should start before it runs the actual script to check if has correct items in bank, if not buys the first lower level items (All the way up to addy)
// After killing cows should buy some crappy gear so not bot like ( Random helmet, random body, random legs)
// If it then hit's level 41 - calls GE to sell items - if money is enough to buy rune version, buy it - else continue with addy
//Methodoligy:
// 1 in 100 chance of chaning scripts (Not with combat phase)
// Add a complicated camera method (Takes the object needs to look at) looks up, and down, and up, etc
