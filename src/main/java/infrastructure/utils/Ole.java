package infrastructure.utils;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.WTypes;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.PointerByReference;

public class Ole {

    private Unknown unknown;
    private Ole32 ole32;

    public Ole(Guid.GUID clsid, Guid.IID iid) {
        ole32 = Ole32.INSTANCE;
        ole32.CoInitializeEx(Pointer.NULL, Ole32.COINIT_APARTMENTTHREADED);
        PointerByReference pointerByReference = new PointerByReference();
        WinNT.HRESULT hresult = ole32.CoCreateInstance(clsid, null, WTypes.CLSCTX_SERVER, iid, pointerByReference);
        COMUtils.checkRC(hresult);
        unknown =  new Unknown(pointerByReference.getValue());
    }


    public Unknown getUnknown() {
        return unknown;
    }

    public Ole32 getOle32() {
        return ole32;
    }
}
