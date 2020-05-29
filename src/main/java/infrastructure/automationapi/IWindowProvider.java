package infrastructure.automationapi;

import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.utils.FunctionLibraries;
import infrastructure.utils.FunctionLibrary;

import java.util.HashMap;

public class IWindowProvider {
    private HashMap<String, Function> methods;
    private Pointer pointerToInterface;

    public IWindowProvider(PointerByReference pointerToWindowProviderByReference) {
        FunctionLibrary library = FunctionLibraries.load().iWindowProviderLibrary(pointerToWindowProviderByReference);
        this.methods = library.getMethods();
        this.pointerToInterface = library.getPointerToInstance();
    }

    /*
    0 - Normal
    1 - Maximized
    2 - Minimized
     */
    public void setWindowVisualState(Integer state){
        System.out.println(methods.get("SetVisualState").invokeInt(new Object[]{pointerToInterface, state}));
        methods.get("SetVisualState").invokeInt(new Object[]{pointerToInterface, state});
        System.out.println(methods.get("SetVisualState").invokeInt(new Object[]{pointerToInterface, state}));
    }
}
