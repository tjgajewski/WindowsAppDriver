package infrastructure;//package infrastructure;

public class ElementNotAvailableException extends WindowsDriverException {
    public ElementNotAvailableException(){
        super("Error Code: 0x"+ERROR_CODE);

    }

    public ElementNotAvailableException(String attribute, String attributeValue) {
        super("Unable to find an element using the '" + attribute + "(" + attributeValue + ")' search conditions.");
    }

    public ElementNotAvailableException(int code, String attribute, String attributeValue) {
        super("Unable to find an element using the '" + attribute + "(" + attributeValue + ")' search conditions.");
    }

    public ElementNotAvailableException(String message){
        super(message);
    }

    public ElementNotAvailableException(Throwable cause) {
        super(cause);
    }

    private static final int ERROR_CODE = 0x80040201;
    private static final String errorCodeDescription = "Indicates that a method was called on a virtualized element, or on an element " +
            "that no longer exists, usually because it has been destroyed.";
}

