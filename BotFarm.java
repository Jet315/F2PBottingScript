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

    public static final long startTime = System.currentTimeMillis();
    Font font = new Font("Verdana", Font.BOLD, 14);



    //When calling method to run a set of node scripts then put them it / remove old ones

    //Generate script object once as it will be used a ton..

    @Override
    public void run() {
        while (true) {
            //This script was made over many months, during these months I have changed ways I do tasks which is why I do thing differently in different classes

            //if variable deciding if account is banned - change account (MySQL database)



            //Have to create a new object each time I loop - Saves previous data otherwise which messes up the script real bad
            Scripts s = new Scripts();
            //Check if trade is active, if so loop the trade node framework, else loop the normal - I do different loops as else it will go into the trade loop and wont know what loop to go back to
            if(AccountStatus.tradeActive){
                s.loopTrade();
                //May as well sleep for a bit longer to prevent high CPU usage. No need to loop through quickly
                General.sleep(300,850);
            }else {

                s.loop();

            }
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
//TODO: Potatofarm script - when at edge of field it walks to the field, no need fix this

//TODO : Currently I don't handle null pointer exceoptions very well. I need a method that I can call that will pretty much restart the bot (Sell all it's items, etc)

//TODO: Change way it walks to GE - Walk to west bank, then custom path 2 GE
//TODO: Add a money making way like potatos so bot can start off with bit of money - Then create a method which is executed after WalkToGE in combat to make starter money, this also needs to be changed in scriptstarterandfinisher class as if say it doesn't have enough money to start softclay script, it will auto pick potato so change this to pick the method I created
//TODO: add method in Combat.WALKTOGE to buy random clothes?
//TODO: Add better antiban, (Better stat checks, generate reaction times + Bank methods?)

// TODO: Change start of script so can train multiple spots (Make more advanced as time goes by)

// TODO: Add auto muling (Trading) framework
// TODO: Add auto account (MySQL database - server pumping in accounts to it)

// TODO: Overtime add more scripts, ideas bellow
/*
Jug Filling script
WoodcuttingUtils script
Mining script
Fishing script

 */

//
//
//
//
//
//
//
// Possible TODO:s
// TODO: Possible with taus bot manager I can get a mule to auto give iron amr and scimi
// TODO: Possible add random walking option after open gate in combat script (When opening chicken door)



// Random planning
// Go to GE and sell (A SCRIPT TO AUTO SELL EVERYTHING VALUE IN BANK)
// Then it will start a new script
// A method should start before it runs the actual script to check if has correct items in bank, if not buys the first lower level items (All the way up to addy)
// After killing cows should buy some crappy gear so not bot like ( Random helmet, random body, random legs)
// If it then hit's level 41 - calls GE to sell items - if money is enough to buy rune version, buy it - else continue with addy
//Methodoligy:
// 1 in 100 chance of chaning scripts (Not with combat phase)
// Add a complicated camera method (Takes the object needs to look at) looks up, and down, and up, etc
