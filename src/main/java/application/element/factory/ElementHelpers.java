package application.element.factory;

import application.driver.DriverHelpers;
import application.driver.factory.WindowsDriver;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.automationapi.IUIAutomation;
import infrastructure.automationapi.IUIAutomationElement;
import infrastructure.automationapi.IUIAutomationTreeWalker;
import org.openqa.selenium.By;

import java.util.List;

public class ElementHelpers extends WindowsDriver {

    public static IUIAutomationElement getIUIAutomationElement(By by, IUIAutomation iuiAutomation, IUIAutomationElement frameElement, WindowsDriver driver){
        WindowsBy windowsBy = new WindowsBy(by);
        return getIUIAutomationElement(windowsBy, iuiAutomation, frameElement, driver);
    }

    public static IUIAutomationElement getIUIAutomationElement(WindowsBy windowsBy, IUIAutomation iuiAutomation, IUIAutomationElement frameElement, WindowsDriver driver){

        IUIAutomationElement element;
        if(windowsBy.getAttribute().equalsIgnoreCase("By.cssSelector")){
            List<By> byList = DriverHelpers.splitCssSelectorBy(windowsBy);
            PointerByReference propertyCondition = iuiAutomation.createMultipleConditions(byList);
            element = frameElement.findFirst(propertyCondition, windowsBy);
        }
        else if(windowsBy.getAttribute().equalsIgnoreCase("By.xpath")){
            element =  DriverHelpers.returnXpathElement(windowsBy, iuiAutomation, frameElement, driver);
        }
        else{
            PointerByReference propertyCondition = iuiAutomation.createPropertyCondition(windowsBy.getAttributeIndex(), windowsBy.getAttributeValue().toString());
            element = frameElement.findFirst(propertyCondition, windowsBy);
        }
        return element;
    }
}
