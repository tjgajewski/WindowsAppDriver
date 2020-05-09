package infrastructure.automationapi.patterns;

import infrastructure.automationapi.IUIAutomationElement;
import infrastructure.utils.FunctionLibraries;
import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.utils.FunctionLibrary;

import java.util.HashMap;

public class SelectItemPattern {

    private HashMap<String, Function> methods;
    private Pointer pointerToPatternClassInstance;
    private static final int selectItemPatternID = 10010;
    private static final int isSelectItemPatternAvailID = 30036;

    public SelectItemPattern(IUIAutomationElement element) {
        PointerByReference pointerToPatternClassInstanceByReference = element.getCurrentPattern(selectItemPatternID);
        FunctionLibrary library = FunctionLibraries.load().iuiAutomationSelectItemLibrary(pointerToPatternClassInstanceByReference);
        this.methods = library.getMethods();
        this.pointerToPatternClassInstance = library.getPointerToInstance();
    }

    public static boolean isAvailableForElement(IUIAutomationElement element) {
        return element.getCurrentPropertyValue(isSelectItemPatternAvailID).booleanValue();
    }

    public void select(){
        methods.get("Select").invokeInt(new Object[]{pointerToPatternClassInstance});
    }
}
