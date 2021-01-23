package application.actions.factory;

import application.driver.factory.WindowsDriver;
import application.element.factory.WindowsBy;
import application.element.factory.WindowsElement;
import com.sun.jna.platform.win32.OaIdl;
import com.sun.jna.platform.win32.Variant;
import com.sun.jna.platform.win32.WTypes;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.automationapi.IUIAutomationElement;
import infrastructure.robotapi.Robo;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;

import java.util.*;

public class WindowsActions implements WindowsActionsInterface {

    private WindowsDriver driver;
    private WindowsElement element;
    public WindowsActions (WindowsDriver driver){
        this.driver = driver;
    }

    @Override
    public void mouseMoveTo(Map<String, ?> parameters) {
        element = findByRuntimeID(parameters.get("element").toString());
        element.moveToElement();
    }

    @Override
    public void mouseClick(Map<String, ?> parameters) {
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
    public void sendKeysToActiveElement(Map<String, ?> parameters) {
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

    private WindowsElement findByRuntimeID(String id){
        ArrayList<String> list = new ArrayList<>(Arrays.asList(id.substring(1, id.length() - 1).split(", ")));
        OaIdl.SAFEARRAY sbr = OaIdl.SAFEARRAY.createSafeArray(new WTypes.VARTYPE(Variant.VT_INT),4);
        int i=0;
        for(String v:list){
            Integer zzz = Integer.parseInt(v,16);
            sbr.putElement(zzz,i);
            i++;
        }
        Variant.VARIANT.ByReference varRef = new Variant.VARIANT.ByReference();
        varRef.setValue(sbr);
        IUIAutomationElement elem = driver.getWindowElement().findFirst(driver.getIuiAutomation().createPropertyCondition(30000,varRef),new WindowsBy("runtimeid",id));
        return new WindowsElement(elem, driver);
    }

}
