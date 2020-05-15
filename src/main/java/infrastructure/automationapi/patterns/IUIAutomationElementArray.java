package infrastructure.automationapi.patterns;

import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.automationapi.IUIAutomationElement;
import infrastructure.utils.FunctionLibraries;
import infrastructure.utils.FunctionLibrary;

import java.util.HashMap;

public class IUIAutomationElementArray {

    private HashMap<String, Function> methods;
    private Pointer pointerToElementArray;

    public IUIAutomationElementArray(PointerByReference pointerToElementArrayByReference) {
        FunctionLibrary library = FunctionLibraries.load().iuiAutomationElementArrayLibrary(pointerToElementArrayByReference);
        this.methods = library.getMethods();
        this.pointerToElementArray = library.getPointerToInstance();
    }

    public int getLength(){
        WinDef.UINT length = new WinDef.UINT();
        methods.get("length").invokeInt(new Object[]{pointerToElementArray,length});
        int lengthInt = length.intValue();
        return lengthInt;
    }

   public IUIAutomationElement getElement(int index){
        PointerByReference pointerToElementByReference = new PointerByReference();
        methods.get("getElement").invokeInt(new Object[]{pointerToElementArray,index, pointerToElementByReference});
        return new IUIAutomationElement(pointerToElementByReference);
    }
}
