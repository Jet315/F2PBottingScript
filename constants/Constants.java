package scripts.goldfarmscript.constants;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

/**
 * Created by Jet on 20/12/2016.
 */
public class Constants {
    //Varibles that might need altering
    public static String clanChatOwner = "TheRSMinepro";
    public static int priceOfChoclateBar = 145;
    public static int priceOfClay = 160;
    public static int priceOfBucket = 180;

    public static String[] fullListOfSellItems = {"Cowhide","Potato","Chocolate dust","Chocolate bar","Clay","Soft clay","Bucket","Logs","Oak logs","Willow logs","Yew logs"};
    // IF ACCOUNT IS NOT > 18 hours old - It cannot sell certain items (anti bot feature)
    public static String[] limitedListOfSellItems = {"Potato","Chocolate dust","Chocolate bar","Clay","Soft clay","Bucket","Logs","Willow logs","Yew logs"};

    //List of axes
    public static String[] axes = {"Iron axe", "Steel axe", "Mithril axe","Adamant axe","Rune axe"};

    //Area of Potato Farm and Potato Farm Bank
    public static RSArea potatoArea = new RSArea(new RSTile[] { new RSTile(3140, 3290, 0), new RSTile(3139, 3282, 0), new RSTile(3139, 3275, 0), new RSTile(3138, 3273, 0), new RSTile(3141, 3268, 0), new RSTile(3151, 3268, 0), new RSTile(3154, 3269, 0), new RSTile(3156, 3272, 0), new RSTile(3157, 3274, 0), new RSTile(3157, 3282, 0), new RSTile(3157, 3288, 0), new RSTile(3153, 3291, 0) });
    public static RSArea potatoBank = new RSArea(new RSTile[] { new RSTile(3090, 3245, 0), new RSTile(3094, 3245, 0), new RSTile(3094, 3246, 0), new RSTile(3095, 3246, 0), new RSTile(3095, 3240, 0), new RSTile(3094, 3241, 0), new RSTile(3093, 3240, 0), new RSTile(3092, 3240, 0), new RSTile(3091, 3242, 0) });

    //Area of location in the middle of the grand exchange
    public static RSArea grandExchangeArea = new RSArea(new RSTile[] { new RSTile(3167, 3492, 0), new RSTile(3168, 3491, 0), new RSTile(3168, 3487, 0), new RSTile(3167, 3486, 0), new RSTile(3162, 3486, 0), new RSTile(3161, 3487, 0), new RSTile(3161, 3491, 0), new RSTile(3162, 3493, 0) });
    //Grand exchange interface Ids:
    public final static int MASTER = 465;
    //Collect button:
    public final static int COLLECT_CHILD = 6;
    public final static int COLLECT_COMPONENT = 0;

    //Buy icon button:
    public final static int BUY_CHILD = 7;
    public final static int BUY_COMPONENT = 26;

    //Item icons (The item it needs to click to buy)
    public final static int MASTER_ITEM = 162;
    public final static int ITEM_CHILD = 38;
    public final static int ITEM_COMPONENT = 0;



    //Making clay interface Ids:
    public final static int MASTER_CLAY = 309;
    public final static int CLAY_CHILD = 5;

}
