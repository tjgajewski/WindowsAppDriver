package infrastructure;

public class WindowsDriverException extends RuntimeException{
    private final int errorCode;
    public WindowsDriverException(final String message) {
        super(message);
        this.errorCode = 0;
    }

    public WindowsDriverException(final int errorCode) {
        super("Error code: 0x"+errorCode);
        this.errorCode = errorCode;
    }

    public WindowsDriverException(final Throwable cause) {
        super(cause);
        this.errorCode = 0;
    }

    public WindowsDriverException(final String message, final Throwable cause){
        super(message, cause);
        this.errorCode = 0;
    }
}
