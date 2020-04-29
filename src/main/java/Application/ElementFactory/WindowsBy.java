package Application.ElementFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;

public class WindowsBy {

    private int automationId = 30011;
    private int name = 30005;
    private int className = 30012;
    private int localControlType = 30004;

    private int attribute;

    public int getAttribute() {
        return attribute;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    private String attributeValue;

    public String getAccessTypeName() {
        return accessTypeName;
    }

    private String accessTypeName;

    public WindowsBy (By by){
        String[] locator = by.toString().split(":",2);
        getElementByAttributesSeleniumBy(locator[0],locator[1].trim());
    }

    public WindowsBy (String stringBy){
        String[] locator = stringBy.split(":",2);
        getElementByAttributesSeleniumBy(locator[0],locator[1].trim());
    }

    public WindowsBy (String attribute, String attributeValue){
        getElementByAttributes(attribute, attributeValue);
    }

    public void getElementByAttributesSeleniumBy(String attribute, String attributeValue){
        switch (attribute) {
            case "By.id":
                this.attribute = automationId;
                break;
            case "By.name":
                this.attribute = name;
                break;
            case "By.className":
                this.attribute = className;
                break;
            case "By.tagName":
                this.attribute = localControlType;
                break;
            default:
                throw new WebDriverException(attribute + " is an unspoorted property for finding an element");
        }
        this.attributeValue = attributeValue;
    }

    public void getElementByAttributes(String attribute, String attributeValue){
        switch (attribute.toLowerCase()) {
            case "id":
            case "automationid":
                this.attribute = automationId;
                break;
            case "name":
                this.attribute = name;
                break;
            case "classname":
                this.attribute = className;
                break;
            case "tagname":
            case "localizedcontroltype":
            case "tag":
                this.attribute = localControlType;
                break;
            default:
                throw new WebDriverException(attribute + " is an unspoorted property for finding an element");
        }
        this.attributeValue = attributeValue;
    }

}
