package Application.ElementFactory;

import org.openqa.selenium.WebDriverException;

public class WindowsProperty {

    public static int getPropertyIndex(String attribute){
        int propertyIndex = 0;
        switch (attribute.toLowerCase()){
            case "text":
                propertyIndex = 30045;
                break;
            case "id":
            case "automationid":
                propertyIndex = 30011;
                break;
            case "name":
                propertyIndex = 30005;
                break;
            case "classname":
                propertyIndex = 30012;
                break;
            case "tagname":
            case "localizedcontroltype":
            case "tag":
                propertyIndex = 30004;
                break;
            case "isenabled":
                propertyIndex = 30010;
                break;
            case "size":
                propertyIndex = 30167;
                break;
            default:
                throw new WebDriverException(attribute + " is an unspoorted property type");
        }
        return propertyIndex;
    }
}
