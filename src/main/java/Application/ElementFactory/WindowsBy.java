package Application.ElementFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;

public class WindowsBy {

    private int automationId = 30011;
    private int name = 30005;
    private int className = 30012;
    private int localControlType = 30004;

    private int accessType;

    public int getAccessType() {
        return accessType;
    }

    public String getAccessName() {
        return accessName;
    }

    private String accessName;

    public WindowsBy (By by){
        String[] locator = by.toString().split(":",2);
        getElementByAttributesSeleniumBy(locator[0],locator[1].trim());
    }

    public WindowsBy (String stringBy){
        String[] locator = stringBy.split(":",2);
        getElementByAttributesSeleniumBy(locator[0],locator[1].trim());
    }

    public WindowsBy (String accessType, String accessName){
        getElementByAttributes(accessType,accessName);
    }

    public void getElementByAttributesSeleniumBy(String accessType, String accessName){
        switch (accessType) {
            case "By.id":
                this.accessType = automationId;
                break;
            case "By.name":
                this.accessType = name;
                break;
            case "By.className":
                this.accessType = className;
                break;
            case "By.tagName":
                this.accessType = localControlType;
                break;
            default:
                throw new WebDriverException(accessType + " is an unspoorted property for finding an element");
        }
        this.accessName = accessName;
    }

    public void getElementByAttributes(String accessType, String accessName){
        switch (accessType.toLowerCase()) {
            case "id":
            case "automationid":
                this.accessType = automationId;
                break;
            case "name":
                this.accessType = name;
                break;
            case "classname":
                this.accessType = className;
                break;
            case "tagname":
            case "localizedcontroltype":
            case "tag":
                this.accessType = localControlType;
                break;
            default:
                throw new WebDriverException(accessType + " is an unspoorted property for finding an element");
        }
        this.accessName = accessName;
    }

}
