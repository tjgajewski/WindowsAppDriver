package application.actions.factory;

import application.driver.factory.WindowsDriver;
import application.element.factory.WindowsBy;
import application.element.factory.WindowsElement;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.automationapi.IUIAutomationElement;
import infrastructure.robotapi.Robo;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WindowsActions implements WindowsActionsInterface {

    private WindowsDriver driver;
    private WindowsElement element;
    public WindowsActions (WindowsDriver driver){
        this.driver = driver;
    }

    @Override
    public void mouseMoveTo(Map<String, ?> parameters, HashMap<String, By> generatedElements) {
        By by = generatedElements.get(parameters.get("element"));
        element = new WindowsElement(by, driver.getIuiAutomation(), driver.getWindowElement());
        element.moveToElement();
    }

    @Override
    public void mouseClick(Map<String, ?> parameters, HashMap<String, By> generatedElements) {
        int clickIndex = (Integer) parameters.get("button");
        if(clickIndex == 0) {
            Robo.getRoboInstance().leftClick();
        }
        else if(clickIndex == 2){
            Robo.getRoboInstance().rightClick();
        }
        Robo.getRoboInstance().returnMouseAfterMove();
    }

    @Override
    public void sendKeysToActiveElement(Map<String, ?> parameters, HashMap<String, By> generatedElements) {
        CharSequence[] text = (CharSequence[]) parameters.get("value");
        element.sendKeys(text);
        Robo.getRoboInstance().returnMouseAfterMove();
    }

//    @Override
//    public void doubleClick(Map<String, ?> parameters, HashMap<String, By> generatedElements){
//        By by = generatedElements.get(parameters.get("element"));
//        element = new WindowsElement(by, driver.getIuiAutomation(), driver.getWindowElement());
//        Actions action = new Actions(driver);
//        action.doubleClick(element);
//    }

}
