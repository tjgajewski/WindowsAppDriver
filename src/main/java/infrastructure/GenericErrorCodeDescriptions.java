package infrastructure;

import infrastructure.WindowsDriverException;

public class GenericErrorCodeDescriptions {
    public static String getErrorCodeDescription(int errorCode){
        String description;
        switch (errorCode){
            case 80040201:
                description = "UIA_E_ELEMENTNOTAVAILABLE - Indicates that a method was called on a virtualized element, " +
                                "or on an element that no longer exists, usually because it has been destroyed.";
                break;
            case 80040200:
                description = "UIA_E_ELEMENTNOTENABLED - Indicates that a method that requires an enabled element, " +
                                "such as Select or Expand, " + "was called on an element that was disabled.";
                break;
            case 80131509:
                description = "UIA_E_INVALIDOPERATION - Indicates that the method attempted an operation " +
                                "that was not valid.";
                break;
            case 80040202:
                description = "UIA_E_NOCLICKABLEPOINT - Indicates that the GetClickablePoint method was called on " +
                                "an element that has no clickable point.";
                break;
            case 80040204:
                description = "UIA_E_NOTSUPPORTED - Indicates that the provider explicitly does not support the " +
                                "specified property or control pattern. UI Automation will return this error code to the " +
                                "caller without attempting to provide a default value or falling back to another provider.";
                break;
            case 80040203:
                description = "UIA_E_PROXYASSEMBLYNOTLOADED - Indicates that a problem occurred when loading an " +
                                "assembly that contains a client-side (proxy) provider.";
                break;
            case 80131505:
                description = "UIA_E_TIMEOUT - Indicates that the time allotted for a process or operation has expired.";
                break;
            default:
                description = "No descrpition available for error code: 0x"+errorCode+
                        " go to https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-error-codes?redirectedfrom=MSDN " +
                        "or https://knowledge.broadcom.com/external/article?legacyId=TECH12638 for more information.";
        }
        return description;
    }
}