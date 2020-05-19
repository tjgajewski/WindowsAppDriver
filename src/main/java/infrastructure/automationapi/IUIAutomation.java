package infrastructure.automationapi;

import application.element.factory.WindowsBy;
import infrastructure.utils.FunctionLibraries;
import com.sun.jna.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.utils.FunctionLibrary;

import java.util.HashMap;

public class IUIAutomation {

    private Pointer pointerToInterface;
    private HashMap<String, Function> methods;

    
    public IUIAutomation(){
        FunctionLibrary library = FunctionLibraries.load().iuiAutomationLibrary();
        this.pointerToInterface = library.getPointerToInstance();
        this.methods = library.getMethods();
    }

    public PointerByReference getRootElement() {
        PointerByReference rootPointer = new PointerByReference();
        methods.get("GetRootElement").invokeInt(new Object[]{pointerToInterface, rootPointer});
        return rootPointer;
    }

    public PointerByReference createPropertyCondition(int propertyTypeId, String propertyValue) {
        Variant.VARIANT.ByValue value = new Variant.VARIANT.ByValue();
        WTypes.BSTR sysAllocated = OleAuto.INSTANCE.SysAllocString(propertyValue);
        value.setValue(Variant.VT_BSTR, sysAllocated);
        PointerByReference condition = new PointerByReference();
        int status = methods.get("CreatePropertyCondition").invokeInt(new Object[]{pointerToInterface, propertyTypeId, value, condition});
        COMUtils.SUCCEEDED(status);
        OleAuto.INSTANCE.SysFreeString(sysAllocated);
        return condition;
    }

    public PointerByReference createPropertyCondition(WindowsBy windowsBy) {
        int propertyTypeId = windowsBy.getAttributeIndex();
        String propertyValue = windowsBy.getAttributeValue();
        Variant.VARIANT.ByValue value = new Variant.VARIANT.ByValue();
        WTypes.BSTR sysAllocated = OleAuto.INSTANCE.SysAllocString(propertyValue);
        value.setValue(Variant.VT_BSTR, sysAllocated);
        PointerByReference condition = new PointerByReference();
        int status = methods.get("CreatePropertyCondition").invokeInt(new Object[]{pointerToInterface, propertyTypeId, value, condition});
        COMUtils.SUCCEEDED(status);
        OleAuto.INSTANCE.SysFreeString(sysAllocated);
        return condition;
    }

    public PointerByReference createPropertyCondition(int propertyTypeId, int propertyValue) {
        Variant.VARIANT.ByValue value = new Variant.VARIANT.ByValue();
        value.setValue(Variant.VT_INT, propertyValue);
        PointerByReference condition = new PointerByReference();
        methods.get("CreatePropertyCondition").invokeInt(new Object[]{pointerToInterface, propertyTypeId, value, condition});
        return condition;
    }

    public PointerByReference createAndCondition(PointerByReference condition1, PointerByReference condition2) {
        PointerByReference pointerByReference = new PointerByReference();
        int status = methods.get("CreateAndCondition").invokeInt(new Object[]{pointerToInterface, condition1.getValue(), condition2.getValue(), pointerByReference});
        return pointerByReference;
    }

    
    
}
