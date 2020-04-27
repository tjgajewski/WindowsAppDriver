package Application.ElementFactory;

import org.openqa.selenium.WebDriverException;

public class WindowsProperty {

    public static int getPropertyId(String attribute){
        int propertyId = 0;
        switch (attribute.toLowerCase()){
            case "text":
                propertyId = 30045;
                break;
            case "id":
            case "automationid":
                propertyId = 30011;
                break;
            case "name":
                propertyId = 30005;
                break;
            case "classname":
                propertyId = 30012;
                break;
            case "tagname":
            case "localizedcontroltype":
            case "tag":
                propertyId = 30004;
                break;
            case "isenabled":
                propertyId = 30010;
                break;
            case "size":
                propertyId = 30167;
                break;
            default:
                throw new WebDriverException(attribute + " is an unspoorted property type");
        }
        return propertyId;
    }
}
