package infrastructure.automationapi;

import application.element.factory.WindowsBy;
import infrastructure.utils.FunctionLibraries;
import application.element.factory.WindowsProperty;
import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Variant;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.utils.FunctionLibrary;
import org.openqa.selenium.NoSuchElementException;

import java.util.HashMap;

public class IUIAutomationElement {
    
    private HashMap<String, Function> methods;
    private Pointer pointerToElement;

    public IUIAutomationElement(PointerByReference pointerToElementByReference) {
        FunctionLibrary library = FunctionLibraries.load().iuiAutomationElementLibrary(pointerToElementByReference);
        this.methods = library.getMethods();
        this.pointerToElement = library.getPointerToInstance();
    }
    
    public void setFocus() {
        methods.get("SetFocus").invokeInt(new Object[]{pointerToElement});
    }

    public IUIAutomationElement findFirst(PointerByReference conditionRef, WindowsBy by) {
        Pointer condition = conditionRef.getValue();
        PointerByReference pointerToElementByReference = new PointerByReference();
        methods.get("FindFirst").invokeInt(new Object[]{pointerToElement, 4, condition, pointerToElementByReference});
        if(pointerToElementByReference.getValue() == null){
            throw new NoSuchElementException("Unable to find element with locator " + by.getAttribute() + ": " +by.getAttributeValue());
        }
        return new IUIAutomationElement(pointerToElementByReference);
    }
    public IUIAutomationElementArray findAll(PointerByReference conditionRef, WindowsBy by)  {
        Pointer condition = conditionRef.getValue();
        PointerByReference IUIAutomationElementArrayPbr = new PointerByReference();
        methods.get("FindAll").invokeInt(new Object[]{pointerToElement, 4, condition,  IUIAutomationElementArrayPbr});
        if (IUIAutomationElementArrayPbr.getValue() == null) {
            throw new NoSuchElementException("Unable to find elements with locator " + by.getAttribute() + ": " + by.getAttributeValue());
        }
        IUIAutomationElementArray returnArray = new IUIAutomationElementArray(IUIAutomationElementArrayPbr);
        return returnArray;

    }

    public PointerByReference getCurrentPattern(int patternId){
        PointerByReference pointerByReference = new PointerByReference();
        methods.get("GetCurrentPattern").invokeInt(new Object[]{pointerToElement, patternId, pointerByReference});
        return pointerByReference;
    }

    public Variant.VARIANT.ByReference getCurrentPropertyValue(int propertyId) {
        Variant.VARIANT.ByReference propertyValue = new Variant.VARIANT.ByReference();
        methods.get("GetCurrentPropertyValue").invokeInt(new Object[]{pointerToElement, propertyId, propertyValue});
        return propertyValue;
    }

    public Variant.VARIANT.ByReference getCurrentPropertyValue(String propertyName) {
        int propertyId = WindowsProperty.getPropertyIndex(propertyName);
        Variant.VARIANT.ByReference propertyValue = new Variant.VARIANT.ByReference();
        methods.get("GetCurrentPropertyValue").invokeInt(new Object[]{pointerToElement, propertyId, propertyValue});
        return propertyValue;

    }
    public WinDef.RECT getCurrentBoundingRectangle () {
        WinDef.RECT retVal = new WinDef.RECT();
        methods.get("GetCurrentBoundingRectangle").invokeInt(new Object[]{pointerToElement,retVal});
        return retVal;
    }
    public WinDef.POINT getClickablePoint () {
        WinDef.POINT.ByReference pbr = new WinDef.POINT.ByReference();
        WinDef.BOOLByReference br = new WinDef.BOOLByReference();
        methods.get("GetClickablePoint").invokeInt(new Object[]{pointerToElement,pbr, br});
        return new  WinDef.POINT(pbr.x,pbr.y);
    }
}
