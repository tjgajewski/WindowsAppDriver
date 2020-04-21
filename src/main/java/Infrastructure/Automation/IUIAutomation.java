package Infrastructure.Automation;

import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.Variant;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

public class IUIAutomation {

    private Pointer interfacePointer;
    private Guid.REFIID refiid;
    private Pointer[] table;
    
    public IUIAutomation(Guid.REFIID refiid, PointerByReference pointerByReference){
        this.refiid = refiid;
        interfacePointer = pointerByReference.getValue();
        Pointer tablePointer = interfacePointer.getPointer(0);
        Pointer[] table = new Pointer[53];
        tablePointer.read(0, table, 0, table.length);
    }
    
    public void QueryInterface(Guid.REFIID byValue, PointerByReference pointerByReference) {

    }
    
    public void AddRef() {

    }

    public void Release() {

    }

    public void compareElements(Pointer element1, Pointer element2, IntByReference same) {

    }

    public void getRootElement(PointerByReference root) {
        PointerByReference rootPointer = new PointerByReference();
        Function f = Function.getFunction(table[5], Function.ALT_CONVENTION);
        f.invoke(new Object[]{interfacePointer, rootPointer});
        Unknown unknownRoot = new Unknown(rootPointer.getValue());
        WinNT.HRESULT hresult = unknownRoot.QueryInterface(refiid, rootPointer);
        COMUtils.checkRC(hresult);
    }

    public void getFocusedElement(PointerByReference element) {

    }

    public void getElementFromHandle(WinDef.HWND hwnd, PointerByReference element) {

    }

    public void elementFromPoint(WinDef.POINT pt, PointerByReference element) {

    }

    public void createCacheRequest(PointerByReference request) {

    }

    public void createPropertyCondition(int propertyId, Variant.VARIANT.ByValue value, PointerByReference condition) {

    }

    public void createAndCondition(Pointer condition1, Pointer condition2, PointerByReference condition) {

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
