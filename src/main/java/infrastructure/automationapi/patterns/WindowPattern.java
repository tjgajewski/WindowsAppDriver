package infrastructure.automationapi.patterns;

import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.automationapi.IUIAutomationElement;
import infrastructure.automationapi.WindowVisualState;
import infrastructure.utils.FunctionLibraries;
import infrastructure.utils.FunctionLibrary;
import java.util.HashMap;

public class WindowPattern {
    private final HashMap<String, Function> methods;
    private final Pointer pointerToInterface;
    private static final int WindowItemPatternID = 10009;

    public WindowPattern(IUIAutomationElement element) {
        PointerByReference pointerToPatternClassInstanceByReference = element.getCurrentPattern(WindowItemPatternID);
        FunctionLibrary library = FunctionLibraries.load().iuiAutomationWindowPatternLibrary(pointerToPatternClassInstanceByReference);
        this.methods = library.getMethods();
        this.pointerToInterface = library.getPointerToInstance();
    }

    public void setWindowVisualState(WindowVisualState state){
        methods.get("SetVisualState").invokeInt((new Object[]{pointerToInterface, state.getDesiredStateIntValue()}));
    }

    public void close(){
        methods.get("Close").invoke(new Object[]{pointerToInterface});
    }
}
