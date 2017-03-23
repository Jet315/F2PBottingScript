package scripts.goldfarmscript.utils;

import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.*;

import java.util.Random;

/**
 * Created by Jet on 04/11/2016.
 */
public class Utils {

    public static int getPlayersLevel(){

        return Player.getRSPlayer().getCombatLevel();
    }

    public static void performRandomTaskHC(){

        Random ran = new Random();

        int y = ran.nextInt(10);
                ABCUtil a = new ABCUtil();
                switch(y){
                    case 1:
                        a.rightClick();
                        break;
                    case 2:
                        a.checkXP();
                        General.sleep(500,2200);
                        Inventory.open();
                        break;
                    case 3:
                        if(a.shouldRotateCamera()) {
                            a.rotateCamera();
                        }
                        break;
                    case 4:
                        a.moveMouse();
                        break;
                    case 5:
                        a.pickupMouse();
                        break;
                    case 6:
                        General.sleep(500,800);
                        break;
                    case 7:
                        General.sleep(900,1300);
                        break;
                    default:
                        a.rotateCamera();
                        break;

                }




    }

    public static void performRandomTaskLC(){

        Random ran = new Random();

        int y = ran.nextInt(7);
            ABCUtil a = new ABCUtil();
            switch(y){
                case 1:
                    a.rightClick();
                    break;
                case 2:
                    a.moveMouse();
                    General.sleep(100,2200);
                    Inventory.open();
                    break;
                case 3:
                        a.rotateCamera();

                    break;
                case 4:
                    General.sleep(100,800);
                    break;
                case 5:
                    if(a.shouldExamineEntity()) {
                        a.examineEntity();
                    }
                    break;
                case 6:
                    if(a.shouldCheckXP()) {
                        a.checkXP();
                    }
                    General.sleep(500,800);
                    break;
                case 7:
                    if(a.generateRunActivation() >= Game.getRunEnergy()){
                    a.run();

                    }
                    General.sleep(50,500);
                    break;
                default:
                    General.sleep(100,1300);
                    break;



        }


    }
    /**
     * Two Camera Methods from Final Calibur & WastedBro
     */
    public static void turnToTile(Positionable tile) {
        int optimalAngle = adjustAngleToTile(tile);
        int optimalRotation = Camera.getTileAngle(tile);

        if(Math.abs(optimalAngle - Camera.getCameraAngle()) > 15)
            Camera.setCameraRotation(optimalAngle + General.random(-12, 12));

        if(Math.abs(optimalRotation - Camera.getCameraRotation()) > 30)
            Camera.setCameraRotation(optimalRotation + General.random(-30, 30));
    }
    public static int adjustAngleToTile(Positionable tile) {
        //Distance from player to object - Used in calculating the optimal angle.
        //Objects that are farther away require the camera to be turned to a lower angle to increase viewing distance.
        int distance = Player.getPosition().distanceTo(tile);

        //The angle is calculated by taking the max height (100, optimal for very close objects),
        //and subtracting an arbitrary number (I chose 6 degrees) for every tile that it is away.
        int angle = 100 - (distance * 6);

        return angle;
    }


    public static void performRandomTaskVLC(){

        Random ran = new Random();

        int y = ran.nextInt(10);
        ABCUtil a = new ABCUtil();

        switch(y){
            case 1:
                if(a.shouldRightClick()) {
                    a.rightClick();
                }
                break;
            case 2:
                a.moveMouse();
                General.sleep(100,2200);
                Inventory.open();
                break;
            case 3:
                if(a.shouldRotateCamera()) {
                    a.rotateCamera();
                }
                General.sleep(100,800);
                break;
            case 4:
                General.sleep(10,800);
                break;
            case 5:
                if(a.shouldExamineEntity()) {
                    a.examineEntity();
                }
                break;
            case 6:
                General.sleep(20,500);
                break;
            case 7:
                General.sleep(50,1300);
                break;
            default:
                General.sleep(10,400);
                break;



        }


    }

    public static void toggleRun(){
        ABCUtil a = new ABCUtil();
        a.run();
    }


    public static int randomMediumNumber(){

        Random ran = new Random();

        int x = ran.nextInt(7);
        return x;

    }
    public static void rotateCamera(){
            Camera.setCameraRotation(General.random(10,90));


    }
    public static int randomSmallNumber(){

        Random ran = new Random();
        //returns number between 0 and 1
        int x = ran.nextInt(2);
        return x;

    }
    /**
     * Gets your quest points.
     * (Code from Encoded - Thanks)
     *
     * @return amount of quest points
     */
    public static int getQuestPoints() {
        return Game.getSetting(101);
    }

    public static int worldGeneratorFree(){
        int worlds[] = {301,308,316,326,335,382,383,384,393,394};


        return worlds[General.random(1,10)];


    }
}
