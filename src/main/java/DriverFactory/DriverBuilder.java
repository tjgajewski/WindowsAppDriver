package DriverFactory;

import DriverFactory.Access.ConnectionInvocationHandler;
import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.WTypes;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.PointerByReference;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DriverBuilder {

    Unknown unknown;
    Unknown root;
    Ole32 ole32;


    public void build() {
        //InvocationHandler invocationHandler = new ConnectionInvocationHandler();
        //Proxy.newProxyInstance(Ole32.INSTANCE.getClass(), );
        ole32 = Ole32.INSTANCE;
        ole32.CoInitializeEx(Pointer.NULL, Ole32.COINIT_APARTMENTTHREADED);
        PointerByReference pointerByReference = new PointerByReference();
        Guid.GUID clsid = new Guid.GUID("{FF48DBA4-60EF-4201-AA87-54103EEF594E}");
        Guid.IID iid = new Guid.IID("{30CBE57D-D9D0-452A-AB13-7AC5AC4825EE}");
        Guid.REFIID refiid = new Guid.REFIID(iid);
        WinNT.HRESULT hresult = ole32.CoCreateInstance(clsid, null, WTypes.CLSCTX_SERVER, iid, pointerByReference);
        COMUtils.checkRC(hresult);
        unknown =  new Unknown(pointerByReference.getValue());
        PointerByReference pointerByReference1 = new PointerByReference();
        WinNT.HRESULT hresult1 = unknown.QueryInterface(new Guid.REFIID(iid), pointerByReference1);
        COMUtils.checkRC(hresult1);
        Pointer interfacePointer = pointerByReference1.getValue();
        Pointer tablePointer = interfacePointer.getPointer(0);
        Pointer[] table = new Pointer[53];
        PointerByReference rootPointer = new PointerByReference();
        Function f = Function.getFunction(table[5], Function.ALT_CONVENTION);
        f.invokeInt(new Object[]{interfacePointer, rootPointer});
        Unknown unknownRoot = new Unknown(rootPointer.getValue());
        WinNT.HRESULT hresult2 = unknownRoot.QueryInterface(refiid, rootPointer);

    }


    private static Class<?>[] getInterfaces(final Object target) {
        Class<?> base = target.getClass();
        final Set<Class<?>> interfaces = new HashSet<>();
        if (base.isInterface()) {
            interfaces.add(base);
        }
        while (base != null && !Object.class.equals(base)) {
            interfaces.addAll(Arrays.asList(base.getInterfaces()));
            base = base.getSuperclass();
        }
        return interfaces.toArray(new Class[0]);
    }
}
