package scripts.goldfarmscript.constants;

/**
 * Created by Jet on 04/11/2016.
 */
public class AccountStatus {


    public static Status currentScript = Status.COMBAT;
    public static String status = "Starting..";
    //Store value of when bot should change from cows to chickens
    public static int cowLevelChange;
    //stores a low value of when should start training defence
    public static int strengthToDefenceChange;
    //stores low value of when should change from defence back to strength
    public static int defenceToStrengthChange;

    public static int amountOfCoinsAccountHas;

    public static boolean tradeActive = false;
    public static boolean tradeRequestRecieved = false;
    public static boolean itemsSold = false;

    public enum Status{
        COMBAT,TRADING,WOODCUTTING,MINING,TANNING,CHOCOLATEGRINDER,POTATOPICKER,SOFTCLAY

    }






}
