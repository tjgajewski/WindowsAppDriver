package Infrastructure.Utils;

import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.ptr.PointerByReference;

import java.util.HashMap;

public class LibraryBuilder {
    Guid.GUID iuiautomationClisid = new Guid.GUID("{FF48DBA4-60EF-4201-AA87-54103EEF594E}");
    Guid.IID iuiautomationIid = new Guid.IID("{30CBE57D-D9D0-452A-AB13-7AC5AC4825EE}");
    Guid.REFIID iuiautomationRefiid = new Guid.REFIID(iuiautomationIid);

    Guid.GUID iuiautomationElementClisid = new Guid.GUID("{FF48DBA4-60EF-4201-AA87-54103EEF594E}");
    Guid.IID iuiautomationElementIid = new Guid.IID("{D22108AA-8AC5-49A5-837B-37BBB3D7591E}");
    Guid.REFIID iuiautomationElementRefiid = new Guid.REFIID(iuiautomationElementIid);

    Ole ole;

    public LibraryBuilder(){
        ole = new Ole(iuiautomationClisid, iuiautomationIid);
    }

    public Library loadIuiAutomationLibrary(){
        PointerByReference pointerByReference = new PointerByReference();
        COMUtils.checkRC(ole.getUnknown().QueryInterface(iuiautomationRefiid, pointerByReference));
        Pointer interfacePointer = pointerByReference.getValue();
        Pointer[] methodsArray = PointerHelpers.readMethodsToPointerArray(interfacePointer.getPointer(0), 58);
        HashMap<String, Function> methods = new HashMap<>();
        methods.put("CompareElements", Function.getFunction(methodsArray[3], Function.ALT_CONVENTION));
        methods.put("GetRootElement",Function.getFunction(methodsArray[5], Function.ALT_CONVENTION));
        methods.put("GetFocusedElement",Function.getFunction(methodsArray[5], Function.ALT_CONVENTION));
        methods.put("GetElementFromHandle",Function.getFunction(methodsArray[6], Function.ALT_CONVENTION));
        methods.put("ElementFromPoint",Function.getFunction(methodsArray[7], Function.ALT_CONVENTION));
        methods.put("CreateCacheRequest",Function.getFunction(methodsArray[20], Function.ALT_CONVENTION));
        methods.put("CreatePropertyCondition",Function.getFunction(methodsArray[23], Function.ALT_CONVENTION));
        methods.put("CreateAndCondition",Function.getFunction(methodsArray[25], Function.ALT_CONVENTION));
        methods.put("CreateOrCondition",Function.getFunction(methodsArray[28], Function.ALT_CONVENTION));
        methods.put("CreateTrueCondition",Function.getFunction(methodsArray[21], Function.ALT_CONVENTION));
        methods.put("CreateFalseCondition",Function.getFunction(methodsArray[22], Function.ALT_CONVENTION));
        methods.put("CreateNotCondition",Function.getFunction(methodsArray[31], Function.ALT_CONVENTION));
        methods.put("GetPatternProgrammaticName",Function.getFunction(methodsArray[50], Function.ALT_CONVENTION));
        methods.put("CreateTreeWalker",Function.getFunction(methodsArray[13], Function.ALT_CONVENTION));
        methods.put("GetControlViewWalker",Function.getFunction(methodsArray[14], Function.ALT_CONVENTION));
        return new Library(methods, pointerByReference);
    }

    public Library loadIuiAutomationElementLibrary(PointerByReference pointerByReference){
        Unknown unknownRoot = new Unknown(pointerByReference.getValue());
        COMUtils.checkRC(unknownRoot.QueryInterface(iuiautomationElementRefiid, pointerByReference));
        Pointer interfacePointer = pointerByReference.getValue();
        Pointer[] methodsArray = PointerHelpers.readMethodsToPointerArray(interfacePointer.getPointer(0), 58);
        HashMap<String, Function> methods = new HashMap<>();
        methods.put("SetFocus", Function.getFunction(methodsArray[3], Function.ALT_CONVENTION));
        methods.put("GetCurrentRuntimeId", Function.getFunction(methodsArray[4], Function.ALT_CONVENTION));
        methods.put("FindFirst", Function.getFunction(methodsArray[5], Function.ALT_CONVENTION));
        methods.put("FindAll", Function.getFunction(methodsArray[6], Function.ALT_CONVENTION));
        methods.put("FindFirstBuildCache", Function.getFunction(methodsArray[7], Function.ALT_CONVENTION));
        methods.put("FindAllBuildCache", Function.getFunction(methodsArray[8], Function.ALT_CONVENTION));
        methods.put("CreatePropertyCondition", Function.getFunction(methodsArray[23], Function.ALT_CONVENTION));
        methods.put("BuildUpdatedCache", Function.getFunction(methodsArray[9], Function.ALT_CONVENTION));
        methods.put("GetCurrentPropertyValue", Function.getFunction(methodsArray[10], Function.ALT_CONVENTION));
        methods.put("GetCurrentPattern", Function.getFunction(methodsArray[16], Function.ALT_CONVENTION));
        methods.put("GetCurrentProcessId", Function.getFunction(methodsArray[20], Function.ALT_CONVENTION));
        methods.put("GetCurrentControlType", Function.getFunction(methodsArray[21], Function.ALT_CONVENTION));
        methods.put("GetCurrentLocalizedControlType", Function.getFunction(methodsArray[22], Function.ALT_CONVENTION));
        methods.put("GetCurrentName", Function.getFunction(methodsArray[23], Function.ALT_CONVENTION));
        methods.put("GetCurrentAcceleratorKey", Function.getFunction(methodsArray[24], Function.ALT_CONVENTION));
        methods.put("GetCurrentIsEnabled", Function.getFunction(methodsArray[28], Function.ALT_CONVENTION));
        methods.put("GetCurrentIsOffscreen", Function.getFunction(methodsArray[24], Function.ALT_CONVENTION));
        methods.put("GetCurrentAutomationId", Function.getFunction(methodsArray[29], Function.ALT_CONVENTION));
        return new Library(methods, pointerByReference);
    }
    HashMap<String, Function> iuiAutomationInvokeMethods;
    public Library loadIuiAutomationInvokeLibrary(PointerByReference pointerByReference){

        if(iuiAutomationInvokeMethods == null) {
            iuiAutomationInvokeMethods  = new HashMap<>();
            Pointer interfacePointer = pointerByReference.getValue();
            Pointer[] methodsArray = PointerHelpers.readMethodsToPointerArray(interfacePointer.getPointer(0), 4);
            iuiAutomationInvokeMethods.put("Invoke", Function.getFunction(methodsArray[3], Function.ALT_CONVENTION));
        }
        return new Library(iuiAutomationInvokeMethods, pointerByReference);
    }
    HashMap<String, Function> iuiAutomationValueMethods;
    public Library loadIuiAutomationValueLibrary(PointerByReference pointerByReference){

        if(iuiAutomationValueMethods == null) {
            iuiAutomationValueMethods  = new HashMap<>();
            Pointer interfacePointer = pointerByReference.getValue();
            Pointer[] methodsArray = PointerHelpers.readMethodsToPointerArray(interfacePointer.getPointer(0), 6);
            iuiAutomationValueMethods.put("SetValue", Function.getFunction(methodsArray[3], Function.ALT_CONVENTION));
            iuiAutomationValueMethods.put("GetValue", Function.getFunction(methodsArray[4], Function.ALT_CONVENTION));
            iuiAutomationValueMethods.put("GetCurrentIsReadOnly", Function.getFunction(methodsArray[5], Function.ALT_CONVENTION));
        }
        return new Library(iuiAutomationValueMethods, pointerByReference);
    }
    HashMap<String, Function> iuiAutomationSelectMethods;
    public Library loadIuIAutomationSelectItemLibrary(PointerByReference pointerByReference) {
        if(iuiAutomationSelectMethods == null){
            iuiAutomationSelectMethods = new HashMap<>();
            Pointer interfacePointer = pointerByReference.getValue();
            Pointer[] methodsArray = PointerHelpers.readMethodsToPointerArray(interfacePointer.getPointer(0), 6);
            iuiAutomationSelectMethods.put("Select", Function.getFunction(methodsArray[3], Function.ALT_CONVENTION));
            iuiAutomationSelectMethods.put("CurrentIsSelected", Function.getFunction(methodsArray[4], Function.ALT_CONVENTION));
        }
        return new Library(iuiAutomationSelectMethods, pointerByReference);
    }
}
