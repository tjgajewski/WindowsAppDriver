package infrastructure.automationapi.patterns;

import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.automationapi.IUIAutomationElement;
import infrastructure.utils.FunctionLibraries;
import infrastructure.utils.FunctionLibrary;

import java.util.HashMap;

public class TogglePattern {

    private HashMap<String, Function> methods;
    private Pointer pointerToPatternClassInstance;
    private static final int togglePatternID = 10015;
    private static final int isTogglePatternAvailID = 30041;

    public TogglePattern(IUIAutomationElement element) {
        PointerByReference pointerToPatternClassInstanceByReference = element.getCurrentPattern(togglePatternID);
        FunctionLibrary library = FunctionLibraries.load().iuiAutomationLibraryTogglePatternMethods(pointerToPatternClassInstanceByReference);
        this.methods = library.getMethods();
        this.pointerToPatternClassInstance = library.getPointerToInstance();
    }

    public static Boolean isAvailableForElement(IUIAutomationElement element){
        return element.getCurrentPropertyValue(isTogglePatternAvailID).booleanValue();
    }

    public void toggle(){
        methods.get("Toggle").invokeInt(new Object[]{pointerToPatternClassInstance});
    }
}
