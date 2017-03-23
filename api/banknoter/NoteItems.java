package scripts.goldfarmscript.api.banknoter;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterfaceChild;

/**
 * Created by Jet on 28/11/2016.
*/
public class NoteItems {
    private static final int BANK_MASTER_ID = 12, WITHDRAW_AS_ITEM_ID = 21, WITHDRAW_AS_NOTED_ID = 23;

    public static boolean isNotedOn() {
        if (Banking.isBankScreenOpen()) {
            RSInterfaceChild itemInterfaceChild = Interfaces.get(BANK_MASTER_ID, WITHDRAW_AS_ITEM_ID);
            if (itemInterfaceChild != null) {
                return itemInterfaceChild.getTextureID() == 812;
            }
        }
        return false;
    }

    public static boolean setNoted(boolean noted) {
        if (Banking.isBankScreenOpen()) {
            if (noted && !isNotedOn()) {
                RSInterfaceChild notedInterfaceChild = Interfaces.get(BANK_MASTER_ID, WITHDRAW_AS_NOTED_ID);
                if (notedInterfaceChild != null && notedInterfaceChild.click("Note")) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(10, 30);
                            return isNotedOn();
                        }
                    }, General.random(800, 1200));
                    return isNotedOn();
                }
            } else if (!noted && isNotedOn()) {
                RSInterfaceChild itemInterfaceChild = Interfaces.get(BANK_MASTER_ID, WITHDRAW_AS_ITEM_ID);
                if (itemInterfaceChild != null && itemInterfaceChild.click("Item")) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(10, 30);
                            return !isNotedOn();
                        }
                    }, General.random(800, 1200));
                    return !isNotedOn();
                }
            }
        }
        return false;
    }
}