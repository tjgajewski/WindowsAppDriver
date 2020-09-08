package infrastructure.automationapi;

import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.utils.FunctionLibraries;
import infrastructure.utils.FunctionLibrary;

import java.util.HashMap;

public class IUIAutomationTreeWalker {


    static Guid.IID IID = new Guid.IID(
            "{4042C624-389C-4AFC-A630-9DF854A541FC}");
    private HashMap<String, Function> methods;
    private Pointer pointerToTreeWalker;
//    private static final String GUID = "{4042C624-389C-4AFC-A630-9DF854A541FC}";

    public IUIAutomationTreeWalker(PointerByReference pointerToTreeWalkerByReference) {
        FunctionLibrary library = FunctionLibraries.load().iuiAutomationTreeWalkerLibrary(pointerToTreeWalkerByReference);
        this.methods = library.getMethods();
        this.pointerToTreeWalker = library.getPointerToInstance();
    }

    public IUIAutomationElement getNextSiblingElement(IUIAutomationElement element){
        PointerByReference pointerToNextSiblingByReference = new PointerByReference();
        methods.get("getNextSiblingElement").invokeInt(new Object[]{pointerToTreeWalker, element.getPointerToElement(), pointerToNextSiblingByReference});
        return new IUIAutomationElement(pointerToNextSiblingByReference);
    }

    public IUIAutomationElement getParentElement(IUIAutomationElement element){
        PointerByReference pointerToParentByReference = new PointerByReference();
        methods.get("getParentElement").invokeInt(new Object[]{pointerToTreeWalker, element.getPointerToElement(), pointerToParentByReference});
        return new IUIAutomationElement(pointerToParentByReference);
    }

    public IUIAutomationElement getFirstChildElement(IUIAutomationElement element){
        PointerByReference pointerToFirstChildByReference = new PointerByReference();
        methods.get("getFirstChildElement").invokeInt(new Object[]{pointerToTreeWalker, element.getPointerToElement(), pointerToFirstChildByReference});
        return new IUIAutomationElement(pointerToFirstChildByReference);
    }

    public IUIAutomationElement getLastChildElement(IUIAutomationElement element){
        PointerByReference pointerToLastChildByReference = new PointerByReference();
        methods.get("getLastChildElement").invokeInt(new Object[]{pointerToTreeWalker, element.getPointerToElement(), pointerToLastChildByReference});
        return new IUIAutomationElement(pointerToLastChildByReference);
    }

    public IUIAutomationElement getPreviousSiblingElement(IUIAutomationElement element){
        PointerByReference pointerToPreviousSiblingByReference = new PointerByReference();
        methods.get("getPreviousSiblingElement").invokeInt(new Object[]{pointerToTreeWalker, element.getPointerToElement(), pointerToPreviousSiblingByReference});
        return new IUIAutomationElement(pointerToPreviousSiblingByReference);
    }
}
