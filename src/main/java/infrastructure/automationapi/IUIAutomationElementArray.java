package infrastructure.automationapi;

import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
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
        IntByReference length = new IntByReference();
        methods.get("length").invokeInt(new Object[]{pointerToElementArray,length});
        int lengthInt = length.getValue();
        return lengthInt;
    }

   public IUIAutomationElement getElement(int index){
        PointerByReference pointerToElementByReference = new PointerByReference();
        methods.get("getElement").invokeInt(new Object[]{pointerToElementArray,index, pointerToElementByReference});
        return new IUIAutomationElement(pointerToElementByReference);
    }
}
