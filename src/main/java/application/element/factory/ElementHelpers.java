package application.element.factory;

import application.driver.DriverHelpers;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.automationapi.IUIAutomation;
import infrastructure.automationapi.IUIAutomationElement;
import org.openqa.selenium.By;

import java.util.List;

public class ElementHelpers {

    public static IUIAutomationElement getIUIAutomationElement(By by, IUIAutomation iuiAutomation, IUIAutomationElement frameElement){
        WindowsBy windowsBy = new WindowsBy(by);
        IUIAutomationElement element;
        if(windowsBy.getAttribute().equalsIgnoreCase("By.cssSelector")){
            List<By> byList = DriverHelpers.splitCssSelectorBy(by);
            PointerByReference propertyCondition = iuiAutomation.createMultipleConditions(byList);
            element = frameElement.findFirst(propertyCondition, windowsBy);
        }
        else{
            PointerByReference propertyCondition = iuiAutomation.createPropertyCondition(windowsBy.getAttributeIndex(), windowsBy.getAttributeValue());
            element = frameElement.findFirst(propertyCondition, windowsBy);
        }
        return element;
    }
}
