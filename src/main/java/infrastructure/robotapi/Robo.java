package infrastructure.robotapi;

import org.openqa.selenium.Point;

import java.awt.*;
import java.awt.event.InputEvent;

public class Robo {

    static Robo robo;
    public static Robo getRoboInstance(){
        if(robo == null){
            robo = new Robo();
        }
        return robo;
    }

    private Robo(){
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    Robot robot;

    public void leftClick(){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private Point previousLocation;
    public void mouseMove(Point point){
        int x = (int) MouseInfo.getPointerInfo().getLocation().getX();
        int y = (int) MouseInfo.getPointerInfo().getLocation().getY();
        if(x!=point.getX()||y!=point.getY()) {
            previousLocation = new Point(x, y);
        }
        robot.mouseMove(point.getX(), point.getY());
    }

    public void rightClick(){
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    public void returnMouseAfterMove(){
        robot.mouseMove(previousLocation.getX(), previousLocation.getY());
    }
}
