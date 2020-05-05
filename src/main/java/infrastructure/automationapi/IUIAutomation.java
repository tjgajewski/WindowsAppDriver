package infrastructure.automationapi;

import infrastructure.utils.Library;
import com.sun.jna.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

import java.util.HashMap;

public class IUIAutomation {

    private Pointer interfacePointer;
    private HashMap<String, Function> methods;
    
    public IUIAutomation(Library library){
        this.methods = library.getMethods();
        this.interfacePointer = library.getInstancePointer().getValue();
    }
    
    public void QueryInterface(Guid.REFIID byValue, PointerByReference pointerByReference) {

    }
    
    public void AddRef() {

    }

    public void Release() {

    }

    public void compareElements(Pointer element1, Pointer element2, IntByReference same) {

    }

    public PointerByReference getRootElement() {
        PointerByReference rootPointer = new PointerByReference();
        int status = methods.get("GetRootElement").invokeInt(new Object[]{interfacePointer, rootPointer});
        return rootPointer;
    }

    public void getFocusedElement(PointerByReference element) {

    }

    public void getElementFromHandle(WinDef.HWND hwnd, PointerByReference element) {

    }

    public void elementFromPoint(WinDef.POINT pt, PointerByReference element) {

    }

    public void createCacheRequest(PointerByReference request) {

    }

    public PointerByReference createPropertyCondition(int propertyTypeId, String propertyValue) {
        Variant.VARIANT.ByValue value = new Variant.VARIANT.ByValue();
        WTypes.BSTR sysAllocated = OleAuto.INSTANCE.SysAllocString(propertyValue);
        value.setValue(Variant.VT_BSTR, sysAllocated);
        PointerByReference condition = new PointerByReference();
        int status = methods.get("CreatePropertyCondition").invokeInt(new Object[]{interfacePointer, propertyTypeId, value, condition});
        COMUtils.SUCCEEDED(status);
        OleAuto.INSTANCE.SysFreeString(sysAllocated);
        return condition;
    }

    public PointerByReference createPropertyCondition(int propertyTypeId, int propertyValue) {
        Variant.VARIANT.ByValue value = new Variant.VARIANT.ByValue();
        value.setValue(Variant.VT_INT, propertyValue);
        PointerByReference condition = new PointerByReference();
        methods.get("CreatePropertyCondition").invokeInt(new Object[]{interfacePointer, propertyTypeId, value, condition});
        return condition;
    }

    public PointerByReference createAndCondition(PointerByReference condition1, PointerByReference condition2) {
        PointerByReference pointerByReference = new PointerByReference();
        int status = methods.get("CreateAndCondition").invokeInt(new Object[]{interfacePointer, condition1.getValue(), condition2.getValue(), pointerByReference});
        return pointerByReference;
    }

    public void createOrCondition(Pointer condition1, Pointer condition2, PointerByReference condition) {

    }

    public void createTrueCondition(PointerByReference condition) {

    }

    public void createFalseCondition(PointerByReference condition) {

    }

    public void createNotCondition(Pointer condition, PointerByReference retval) {

    }

    public void getPatternProgrammaticName(int patternId, PointerByReference retval) {

    }

    public void createTreeWalker(PointerByReference condition, PointerByReference walker) {

    }

    public void getControlViewWalker(PointerByReference walker) {

    }
/*
    public void addAutomationEventHandler(IntByReference eventId, TreeScope scope, Pointer element, PointerByReference cacheRequest, PointerByReference handler) {

    }

 */

    public void removeAutomationEventHandler(IntByReference eventId, PointerByReference element, PointerByReference handler) {

    }

    public void pollForPotentialSupportedProperties(Pointer element, /* SAFEARRAY */ PointerByReference ids, /* SAFEARRAY */ PointerByReference names) {

    }

    public void pollForPotentialSupportedPatterns(Pointer element, /* SAFEARRAY */ PointerByReference ids, /* SAFEARRAY */ PointerByReference names) {

    }
    
    
}
