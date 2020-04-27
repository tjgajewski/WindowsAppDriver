package Infrastructure.Automation;

import Application.ElementFactory.WindowsProperty;
import Infrastructure.Utils.Library;
import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.Variant;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

import java.util.HashMap;

public class IUIAutomationElement {
    
    private HashMap<String, Function> methods;
    private Pointer interfacePointer;

    public IUIAutomationElement(Library library) {
        this.methods = library.getMethods();
        this.interfacePointer = library.getInstancePointer().getValue();
    }

    public IUIAutomationElement(HashMap<String, Function> methods, PointerByReference pointerByReference) {
        this.methods = methods;
        this.interfacePointer = pointerByReference.getValue();
    }
    
    public void setFocus() {
        methods.get("SetFocus").invokeInt(new Object[]{interfacePointer});
    }

    public void getCurrentRuntimeId (PointerByReference runtimeId) {
       
    }

    public IUIAutomationElement findFirst(PointerByReference conditionRef) {
        Pointer condition = conditionRef.getValue();
        PointerByReference elementPointer = new PointerByReference();
        int statusCode = methods.get("FindFirst").invokeInt(new Object[]{interfacePointer, 4, condition, elementPointer});
        COMUtils.SUCCEEDED(statusCode);
        return new IUIAutomationElement(methods, elementPointer);
    }

    public void findAll(Pointer condition, PointerByReference sr) {
        
    }

    public PointerByReference getCurrentPattern(int patternId){
        PointerByReference pointerByReference = new PointerByReference();
        methods.get("GetCurrentPattern").invokeInt(new Object[]{interfacePointer, patternId, pointerByReference});
        return pointerByReference;
    }

    public void findFirstBuildCache(int scope, Pointer condition, Pointer cacheRequest, PointerByReference found) {
    }

    public void findAllBuildCache (int scope, Pointer condition, Pointer cacheRequest, PointerByReference found) {
    }

    public void buildUpdatedCache (Pointer cacheRequest, PointerByReference updatedElement) {
    }

    public String getCurrentPropertyValue(int propertyId) {
        Variant.VARIANT.ByReference propertyValue = new Variant.VARIANT.ByReference();
        methods.get("GetCurrentPropertyValue").invokeInt(new Object[]{interfacePointer, propertyId, propertyValue});
        return propertyValue.getValue().toString();
    }

    public Object getCurrentPropertyValue(String propertyName) {
        int propertyId = WindowsProperty.getPropertyId(propertyName);
        Variant.VARIANT.ByReference propertyValue = new Variant.VARIANT.ByReference();
        methods.get("GetCurrentPropertyValue").invokeInt(new Object[]{interfacePointer, propertyId, propertyValue});
        return propertyValue.getValue();
    }

    public void getCurrentProcessId (IntByReference retVal) {

    }

    public void getCurrentControlType(IntByReference ipr) {

    }

    public void getCurrentLocalizedControlType (PointerByReference retVal) {

    }

    public void getCurrentName(PointerByReference sr) {

    }

    public void getCurrentAcceleratorKey (PointerByReference retVal) {

    }

    public void getCurrentIsEnabled (WinDef.BOOLByReference retVal) {

    }

    public void getCurrentAutomationId (PointerByReference retVal) {

    }

    public void getCurrentClassName(PointerByReference sr) {

    }

    public void getCurrentIsOffscreen (WinDef.BOOLByReference retVal) {

    }

}
