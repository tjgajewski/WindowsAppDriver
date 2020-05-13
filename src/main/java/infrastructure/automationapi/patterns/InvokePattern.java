package infrastructure.automationapi.patterns;

import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.automationapi.IUIAutomationElement;
import infrastructure.utils.FunctionLibraries;
import infrastructure.utils.FunctionLibrary;

import java.util.HashMap;

public class InvokePattern {

    private HashMap<String, Function> methods;
    private Pointer pointerToPatternClassInstance;
    private static final int invokePatternID = 10000;
    private static final int isInvokePatternAvailID = 30031;

    public InvokePattern(IUIAutomationElement element) {
        PointerByReference pointerToPatternClassInstanceByReference = element.getCurrentPattern(invokePatternID);
        FunctionLibrary library = FunctionLibraries.load().iuiAutomationInvokeLibrary(pointerToPatternClassInstanceByReference);
        this.methods = library.getMethods();
        this.pointerToPatternClassInstance = library.getPointerToInstance();
    }

    public static Boolean isAvailableForElement(IUIAutomationElement element){
        return element.getCurrentPropertyValue(isInvokePatternAvailID).booleanValue();
    }

    public void invoke(){
        methods.get("Invoke").invokeInt(new Object[]{pointerToPatternClassInstance});
    }
}
