package infrastructure;//package infrastructure;

public class ElementNotFoundException extends WindowsDriverException {
    public ElementNotFoundException(String attribute, String attributeValue) {
        super("Unable to find an element using the '" + attribute + "(" + attributeValue + ")' search conditions.");
    }

    public ElementNotFoundException(String message){
        super(message);
    }

    public ElementNotFoundException(Throwable cause) {
        super(cause);
    }
}

