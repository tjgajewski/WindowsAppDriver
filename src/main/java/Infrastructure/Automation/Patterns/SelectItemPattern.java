package Infrastructure.Automation.Patterns;

import Infrastructure.Utils.Library;
import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import java.util.HashMap;

public class SelectItemPattern {

    private HashMap<String, Function> methods;
    private Pointer interfacePointer;

    public SelectItemPattern(Library library) {
        this.methods = library.getMethods();
        this.interfacePointer = library.getInstancePointer().getValue();
    }

    public SelectItemPattern(HashMap<String, Function> methods, PointerByReference pointerByReference) {
        this.methods = methods;
        this.interfacePointer = pointerByReference.getValue();
    }

    public void select(){
        methods.get("Select").invokeInt(new Object[]{interfacePointer});
    }
}
