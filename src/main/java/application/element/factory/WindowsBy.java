package application.element.factory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;

public class WindowsBy {
    private int automationId = 30011;
    private int name = 30005;
    private int className = 30012;
    private int localControlType = 30004;
    private String attribute;
    private int attributeIndex;
    private String attributeValue;

    public WindowsBy (By by){
        String[] locator = by.toString().split(":",2);
        setAttributeIndexByByMethod(locator[0],locator[1].trim());
    }

    public WindowsBy (String stringBy){
        String[] locator = stringBy.split(":",2);
        setAttributeIndexByByMethod(locator[0],locator[1].trim());
    }

    public WindowsBy (String attributeIndex, String attributeValue){
        setAttributeIndexByAttribute(attributeIndex, attributeValue);
    }

    public String getAttribute() {
        return attribute;
    }

    public int getAttributeIndex() {
        return attributeIndex;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeIndexByByMethod(String attribute, String attributeValue){
        this.attribute=attribute;
        switch (attribute) {
            case "By.id":
                this.attributeIndex = automationId;
                break;
            case "By.name":
                this.attributeIndex = name;
                break;
            case "By.className":
                this.attributeIndex = className;
                break;
            case "By.tagName":
                this.attributeIndex = localControlType;
                break;
            default:
                throw new WebDriverException(attribute + " is an unspoorted property for finding an element");
        }
        this.attributeValue = attributeValue;
    }

    public void setAttributeIndexByAttribute(String attribute, String attributeValue){
        switch (attribute.toLowerCase()) {
            case "id":
            case "automationid":
                this.attributeIndex = automationId;
                break;
            case "name":
                this.attributeIndex = name;
                break;
            case "classname":
                this.attributeIndex = className;
                break;
            case "tagname":
            case "localizedcontroltype":
            case "tag":
                this.attributeIndex = localControlType;
                break;
            default:
                throw new WebDriverException(attribute + " is an unspoorted property for finding an element");
        }
        this.attributeValue = attributeValue;
    }

}