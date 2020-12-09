package infrastructure.automationapi;

import application.element.factory.WindowsBy;
import com.sun.jna.platform.win32.COM.Unknown;
import infrastructure.utils.FunctionLibraries;
import com.sun.jna.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.utils.FunctionLibrary;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.List;

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

    public PointerByReference createMultipleConditions(List<By> byList){
        WindowsBy windowsBy1 = new WindowsBy(byList.get(0));
        PointerByReference baseCondition = createPropertyCondition(windowsBy1);
        for(int i = 1; i < byList.size(); i++){
            WindowsBy windowsBy2 = new WindowsBy(byList.get(i));
            PointerByReference propertyCondition = createAndCondition(baseCondition, createPropertyCondition(windowsBy2));
            baseCondition = propertyCondition;
        }
        return baseCondition;
    }

    public IUIAutomationTreeWalker getTreeWalker(){
        PointerByReference pointerToWalker = new PointerByReference();
        getControlViewWalker(pointerToWalker);
        Unknown conditionA = new Unknown(pointerToWalker.getValue());
        PointerByReference pointerToNewWalker= new PointerByReference();
        methods.get("CreateTreeWalker").invokeInt(new Object[]{pointerToInterface, conditionA, pointerToNewWalker});
        conditionA.QueryInterface(new Guid.REFIID(IUIAutomationTreeWalker.IID), pointerToNewWalker);
        IUIAutomationTreeWalker walker = new IUIAutomationTreeWalker(pointerToNewWalker);
        return walker;
    }

    public void getControlViewWalker(PointerByReference pointerToWalker){
        methods.get("GetControlViewWalker").invokeInt(new Object[]{pointerToInterface, pointerToWalker});
    }

    public void getContentViewWalker(PointerByReference pointerToWalker){
        methods.get("GetContentViewWalker").invokeInt(new Object[]{pointerToInterface, pointerToWalker});
    }

    public void elementFromPoint(WinDef.POINT point, PointerByReference pointerToElement){
        methods.get("ElementFromPoint").invokeInt(new Object[]{pointerToInterface, point, pointerToElement});
    }

    public void getFocusedElement(PointerByReference pointerToElement){
        methods.get("GetFocusedElement").invokeInt(new Object[]{pointerToInterface, pointerToElement});
    }


    public void createTrueCondition(PointerByReference pointerToCondition){
        methods.get("CreateTrueCondition").invokeInt(new Object[]{pointerToInterface, pointerToCondition});
    }

}
