package Application.DriverFactory;

import Infrastructure.Automation.IUIAutomation;
import Infrastructure.Utils.Ole;
import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.WTypes;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.PointerByReference;

public class DriverBuilder {

    Guid.GUID clsid = new Guid.GUID("{FF48DBA4-60EF-4201-AA87-54103EEF594E}");
    Guid.IID iid = new Guid.IID("{30CBE57D-D9D0-452A-AB13-7AC5AC4825EE}");
    Guid.REFIID refiid = new Guid.REFIID(iid);
    IUIAutomation iuiAutomation;


    public void build() {
        Ole ole = new Ole(clsid, iid);
        PointerByReference pointerByReference = new PointerByReference();
        COMUtils.checkRC(ole.getUnknown().QueryInterface(refiid, pointerByReference));
        iuiAutomation = new IUIAutomation(refiid, pointerByReference);

    }

    public Unknown getRootElement(Pointer interfacePointer, Pointer[] table){
        PointerByReference rootPointer = new PointerByReference();
        Function f = Function.getFunction(table[5], Function.ALT_CONVENTION);
        f.invoke(new Object[]{interfacePointer, rootPointer});
        Unknown unknownRoot = new Unknown(rootPointer.getValue());
        WinNT.HRESULT hresult = unknownRoot.QueryInterface(refiid, rootPointer);
        COMUtils.checkRC(hresult);
        return unknownRoot;
    }

}
