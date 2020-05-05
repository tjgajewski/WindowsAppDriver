<<<<<<< HEAD:src/main/java/Infrastructure/automationapi/patterns/InvokePattern.java
package infrastructure.automationapi.patterns;
=======
package Infrastructure.Automation.Patterns;
>>>>>>> d7414fd4d5a34900b45cff03152dc67eb4d53d0e:src/main/java/Infrastructure/Automation/Patterns/InvokePattern.java

import Infrastructure.Utils.Library;
import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import java.util.HashMap;

public class InvokePattern {

    private HashMap<String, Function> methods;
    private Pointer interfacePointer;

    public InvokePattern(Library library) {
        this.methods = library.getMethods();
        this.interfacePointer = library.getInstancePointer().getValue();
    }

    public InvokePattern(HashMap<String, Function> methods, PointerByReference pointerByReference) {
        this.methods = methods;
        this.interfacePointer = pointerByReference.getValue();
    }

    public void invoke(){
        methods.get("Invoke").invokeInt(new Object[]{interfacePointer});
    }
}
