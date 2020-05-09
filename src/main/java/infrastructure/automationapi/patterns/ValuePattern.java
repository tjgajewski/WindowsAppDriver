package infrastructure.automationapi.patterns;

import infrastructure.automationapi.IUIAutomationElement;
import infrastructure.utils.FunctionLibraries;
import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.OleAuto;
import com.sun.jna.platform.win32.WTypes;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.utils.FunctionLibrary;

import java.util.HashMap;

public class ValuePattern {

    private HashMap<String, Function> methods;
    private Pointer interfacePointer;
    private static final int valuePatternID = 10002;
    private static final int isValuePatternAvailID = 30043;

    public ValuePattern(IUIAutomationElement element) {
        PointerByReference pointerToPatternClassInstanceByReference = element.getCurrentPattern(valuePatternID);
        FunctionLibrary library = FunctionLibraries.load().loadIuiAutomationValueLibrary(pointerToPatternClassInstanceByReference);
        this.methods = library.getMethods();
        this.interfacePointer = library.getPointerToInstance();
    }

    public static boolean isAvailableForElement(IUIAutomationElement element) {
        return element.getCurrentPropertyValue(isValuePatternAvailID).booleanValue();
    }

    public void setValue(String value) {
        WTypes.BSTR valueToSet = OleAuto.INSTANCE.SysAllocString(value);
        methods.get("SetValue").invokeInt(new Object[]{interfacePointer, valueToSet});
    }

    public String getValue() {
        PointerByReference pointerByReference = new PointerByReference();
        methods.get("GetValue").invokeInt(new Object[]{interfacePointer, pointerByReference});
        return pointerByReference.getValue().getWideString(0);
    }
}
