package infrastructure.automationapi.patterns;

import infrastructure.utils.Library;
import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.OleAuto;
import com.sun.jna.platform.win32.WTypes;
import com.sun.jna.ptr.PointerByReference;

import java.util.HashMap;

public class ValuePattern {

    private HashMap<String, Function> methods;
    private Pointer interfacePointer;

    public ValuePattern(Library library) {
        this.methods = library.getMethods();
        this.interfacePointer = library.getInstancePointer().getValue();
    }

    public ValuePattern(HashMap<String, Function> methods, PointerByReference pointerByReference) {
        this.methods = methods;
        this.interfacePointer = pointerByReference.getValue();
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
