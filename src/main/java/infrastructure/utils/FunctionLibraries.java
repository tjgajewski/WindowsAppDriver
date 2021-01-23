package infrastructure.utils;

import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.ptr.PointerByReference;

import java.util.HashMap;

public class FunctionLibraries {

    private static FunctionLibraries functionLibraries;
    public static FunctionLibraries load(){
        if(functionLibraries == null){
            functionLibraries = new FunctionLibraries();
        }
        return functionLibraries;
    }
    private FunctionLibraries(){};

    private Guid.IID iuiautomationElementIid = new Guid.IID("{D22108AA-8AC5-49A5-837B-37BBB3D7591E}");
    private Guid.REFIID iuiautomationElementRefiid = new Guid.REFIID(iuiautomationElementIid);
    private Guid.IID iuiautomationIid = new Guid.IID("{30CBE57D-D9D0-452A-AB13-7AC5AC4825EE}");
    private Guid.GUID iuiautomationClisid = new Guid.GUID("{FF48DBA4-60EF-4201-AA87-54103EEF594E}");
    private Guid.REFIID iuiautomationRefiid = new Guid.REFIID(iuiautomationIid);

    private HashMap<String, Function> iuiAutomationFunctions;
    private Pointer iuiAutomationInterfacePointer;
    public FunctionLibrary iuiAutomationLibrary(){
        if(iuiAutomationFunctions == null || iuiAutomationInterfacePointer == null) {
            iuiAutomationFunctions = new HashMap<>();
            PointerByReference pointerByReference = new PointerByReference();
            Ole ole = new Ole(iuiautomationClisid, iuiautomationIid);
            COMUtils.checkRC(ole.getUnknown().QueryInterface(iuiautomationRefiid, pointerByReference));
            this.iuiAutomationInterfacePointer = pointerByReference.getValue();
            Pointer[] methodsArray = PointerHelpers.readMethodsToPointerArray(iuiAutomationInterfacePointer.getPointer(0), 58);
            iuiAutomationFunctions.put("CompareElements", Function.getFunction(methodsArray[3], Function.ALT_CONVENTION));
            iuiAutomationFunctions.put("GetRootElement", Function.getFunction(methodsArray[5], Function.ALT_CONVENTION));
            iuiAutomationFunctions.put("GetFocusedElement", Function.getFunction(methodsArray[8], Function.ALT_CONVENTION));
            iuiAutomationFunctions.put("GetElementFromHandle", Function.getFunction(methodsArray[6], Function.ALT_CONVENTION));
            iuiAutomationFunctions.put("ElementFromPoint", Function.getFunction(methodsArray[7], Function.ALT_CONVENTION));
            iuiAutomationFunctions.put("CreateTreeWalker", Function.getFunction(methodsArray[13], Function.ALT_CONVENTION));
            iuiAutomationFunctions.put("GetControlViewWalker", Function.getFunction(methodsArray[14], Function.ALT_CONVENTION));
            iuiAutomationFunctions.put("GetContentViewWalker", Function.getFunction(methodsArray[15], Function.ALT_CONVENTION));
            iuiAutomationFunctions.put("CreateCacheRequest", Function.getFunction(methodsArray[20], Function.ALT_CONVENTION));
            iuiAutomationFunctions.put("CreatePropertyCondition", Function.getFunction(methodsArray[23], Function.ALT_CONVENTION));
            iuiAutomationFunctions.put("CreatePropertyConditionEx", Function.getFunction(methodsArray[24], Function.ALT_CONVENTION));
            iuiAutomationFunctions.put("CreateAndCondition", Function.getFunction(methodsArray[25], Function.ALT_CONVENTION));
            iuiAutomationFunctions.put("CreateOrCondition", Function.getFunction(methodsArray[28], Function.ALT_CONVENTION));
            iuiAutomationFunctions.put("CreateTrueCondition", Function.getFunction(methodsArray[21], Function.ALT_CONVENTION));
            iuiAutomationFunctions.put("CreateFalseCondition", Function.getFunction(methodsArray[22], Function.ALT_CONVENTION));
            iuiAutomationFunctions.put("CreateNotCondition", Function.getFunction(methodsArray[31], Function.ALT_CONVENTION));
            iuiAutomationFunctions.put("GetPatternProgrammaticName", Function.getFunction(methodsArray[50], Function.ALT_CONVENTION));
        }
        return new FunctionLibrary(iuiAutomationFunctions, iuiAutomationInterfacePointer);
    }




    private HashMap<String, Function> iuiAutomationElementMethods;
    public FunctionLibrary iuiAutomationElementLibrary(PointerByReference pointerToElementByReference){
        Pointer pointerToElement = pointerToElementByReference.getValue();
        if(iuiAutomationElementMethods == null) {
            iuiAutomationElementMethods = new HashMap<>();
            Unknown unknownRoot = new Unknown(pointerToElement);
            COMUtils.checkRC(unknownRoot.QueryInterface(iuiautomationElementRefiid, pointerToElementByReference));
            Pointer[] methodsArray = PointerHelpers.readMethodsToPointerArray(pointerToElement.getPointer(0), 90);
            iuiAutomationElementMethods.put("SetFocus", Function.getFunction(methodsArray[3], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("GetCurrentRuntimeId", Function.getFunction(methodsArray[4], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("FindFirst", Function.getFunction(methodsArray[5], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("FindAll", Function.getFunction(methodsArray[6], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("FindFirstBuildCache", Function.getFunction(methodsArray[7], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("FindAllBuildCache", Function.getFunction(methodsArray[8], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("CreatePropertyCondition", Function.getFunction(methodsArray[23], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("BuildUpdatedCache", Function.getFunction(methodsArray[9], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("GetCurrentPropertyValue", Function.getFunction(methodsArray[10], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("GetCurrentPattern", Function.getFunction(methodsArray[16], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("GetCurrentProcessId", Function.getFunction(methodsArray[20], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("GetCurrentControlType", Function.getFunction(methodsArray[21], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("GetCurrentLocalizedControlType", Function.getFunction(methodsArray[22], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("GetCurrentName", Function.getFunction(methodsArray[23], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("GetCurrentAcceleratorKey", Function.getFunction(methodsArray[24], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("GetCurrentIsEnabled", Function.getFunction(methodsArray[28], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("GetCurrentIsOffscreen", Function.getFunction(methodsArray[24], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("GetCurrentAutomationId", Function.getFunction(methodsArray[29], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("GetCurrentBoundingRectangle", Function.getFunction(methodsArray[43], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("GetClickablePoint", Function.getFunction(methodsArray[84], Function.ALT_CONVENTION));
            iuiAutomationElementMethods.put("CurrentNativeWindowHandle", Function.getFunction(methodsArray[36], Function.ALT_CONVENTION));
        }
        return new FunctionLibrary(iuiAutomationElementMethods, pointerToElement);
    }



    private HashMap<String, Function> iuiAutomationInvokeMethods;
    public FunctionLibrary iuiAutomationInvokeLibrary(PointerByReference pointerToPatternClassInstanceByReference){
        Pointer pointerToPatternClassInstance = pointerToPatternClassInstanceByReference.getValue();
        if(iuiAutomationInvokeMethods == null) {
            iuiAutomationInvokeMethods  = new HashMap<>();
            Pointer[] methodsArray = PointerHelpers.readMethodsToPointerArray(pointerToPatternClassInstance.getPointer(0), 4);
            iuiAutomationInvokeMethods.put("Invoke", Function.getFunction(methodsArray[3], Function.ALT_CONVENTION));
        }
        return new FunctionLibrary(iuiAutomationInvokeMethods, pointerToPatternClassInstance);
    }




    private HashMap<String, Function> iuiAutomationValueMethods;
    public FunctionLibrary loadIuiAutomationValueLibrary(PointerByReference pointerToPatternClassInstanceByReference){
        Pointer pointerToPatternClassInstance = pointerToPatternClassInstanceByReference.getValue();
        if(iuiAutomationValueMethods == null) {
            iuiAutomationValueMethods  = new HashMap<>();
            Pointer[] methodsArray = PointerHelpers.readMethodsToPointerArray(pointerToPatternClassInstance.getPointer(0), 6);
            iuiAutomationValueMethods.put("SetValue", Function.getFunction(methodsArray[3], Function.ALT_CONVENTION));
            iuiAutomationValueMethods.put("GetValue", Function.getFunction(methodsArray[4], Function.ALT_CONVENTION));
            iuiAutomationValueMethods.put("GetCurrentIsReadOnly", Function.getFunction(methodsArray[5], Function.ALT_CONVENTION));
        }
        return new FunctionLibrary(iuiAutomationValueMethods, pointerToPatternClassInstance);
    }



    private HashMap<String, Function> iuiAutomationSelectMethods;
    public FunctionLibrary iuiAutomationSelectItemLibrary(PointerByReference pointerToPatternClassInstanceByReference) {
        Pointer pointerToPatternClassInstance = pointerToPatternClassInstanceByReference.getValue();
        if(iuiAutomationSelectMethods == null){
            iuiAutomationSelectMethods = new HashMap<>();
            Pointer[] methodsArray = PointerHelpers.readMethodsToPointerArray(pointerToPatternClassInstance.getPointer(0), 6);
            iuiAutomationSelectMethods.put("Select", Function.getFunction(methodsArray[3], Function.ALT_CONVENTION));
            iuiAutomationSelectMethods.put("CurrentIsSelected", Function.getFunction(methodsArray[4], Function.ALT_CONVENTION));
        }
        return new FunctionLibrary(iuiAutomationSelectMethods, pointerToPatternClassInstance);
    }

    private HashMap<String, Function> iuiAutomationElementArrayMethods;
    public FunctionLibrary iuiAutomationElementArrayLibrary(PointerByReference pointerToClassInstanceByReference) {
        Pointer pointerToClassInstance = pointerToClassInstanceByReference.getValue();
        if(iuiAutomationElementArrayMethods == null){
            iuiAutomationElementArrayMethods = new HashMap<>();
            Pointer[] methodsArray = PointerHelpers.readMethodsToPointerArray(pointerToClassInstance.getPointer(0), 5);
            iuiAutomationElementArrayMethods.put("length", Function.getFunction(methodsArray[3], Function.ALT_CONVENTION));
            iuiAutomationElementArrayMethods.put("getElement", Function.getFunction(methodsArray[4], Function.ALT_CONVENTION));
        }
        return new FunctionLibrary(iuiAutomationElementArrayMethods, pointerToClassInstance);
    }

    private HashMap<String, Function> iuiAutomationWindowPatternMethods;
    public FunctionLibrary iuiAutomationWindowPatternLibrary(PointerByReference pointerToPatternClassInstanceByReference) {
        Pointer pointerToPatternClassInstance = pointerToPatternClassInstanceByReference.getValue();
        if(iuiAutomationWindowPatternMethods == null){
            iuiAutomationWindowPatternMethods = new HashMap<>();
            Pointer[] methodsArray = PointerHelpers.readMethodsToPointerArray(pointerToPatternClassInstance.getPointer(0), 6);
            iuiAutomationWindowPatternMethods.put("Close", Function.getFunction(methodsArray[3], Function.ALT_CONVENTION));
            iuiAutomationWindowPatternMethods.put("SetVisualState", Function.getFunction(methodsArray[5], Function.ALT_CONVENTION));
        }
        return new FunctionLibrary(iuiAutomationWindowPatternMethods, pointerToPatternClassInstance);
    }

    private HashMap<String, Function> iuiAutomationTreeWalkerMethods;
    public FunctionLibrary iuiAutomationTreeWalkerLibrary(PointerByReference pointerToTreeWalkerByRefference) {
        Pointer pointerToTreeWalker = pointerToTreeWalkerByRefference.getValue();
        if(iuiAutomationTreeWalkerMethods == null){
            iuiAutomationTreeWalkerMethods= new HashMap<>();
            Ole ole = new Ole(iuiautomationClisid, iuiautomationIid);
            COMUtils.checkRC(ole.getUnknown().QueryInterface(iuiautomationRefiid, pointerToTreeWalkerByRefference));
            Pointer[] methodsArray = PointerHelpers.readMethodsToPointerArray(pointerToTreeWalker.getPointer(0), 16);
            iuiAutomationTreeWalkerMethods.put("getParentElement", Function.getFunction(methodsArray[3], Function.ALT_CONVENTION));
            iuiAutomationTreeWalkerMethods.put("getFirstChildElement", Function.getFunction(methodsArray[4], Function.ALT_CONVENTION));
            iuiAutomationTreeWalkerMethods.put("getLastChildElement", Function.getFunction(methodsArray[5], Function.ALT_CONVENTION));
            iuiAutomationTreeWalkerMethods.put("getNextSiblingElement", Function.getFunction(methodsArray[6], Function.ALT_CONVENTION));
            iuiAutomationTreeWalkerMethods.put("getPreviousSiblingElement", Function.getFunction(methodsArray[7], Function.ALT_CONVENTION));
            iuiAutomationTreeWalkerMethods.put("normalizeElement", Function.getFunction(methodsArray[8], Function.ALT_CONVENTION));
            iuiAutomationTreeWalkerMethods.put("getParentElementBuildCache", Function.getFunction(methodsArray[9], Function.ALT_CONVENTION));
            iuiAutomationTreeWalkerMethods.put("getFirstChildElementBuildCache", Function.getFunction(methodsArray[10], Function.ALT_CONVENTION));
            iuiAutomationTreeWalkerMethods.put("getLastChildElementBuildCache", Function.getFunction(methodsArray[11], Function.ALT_CONVENTION));
            iuiAutomationTreeWalkerMethods.put("getNextSiblingElementBuildCache", Function.getFunction(methodsArray[12], Function.ALT_CONVENTION));
            iuiAutomationTreeWalkerMethods.put("getPreviousSiblingElementBuildCache", Function.getFunction(methodsArray[13], Function.ALT_CONVENTION));
            iuiAutomationTreeWalkerMethods.put("NormalizeElementBuildCache", Function.getFunction(methodsArray[14], Function.ALT_CONVENTION));
            iuiAutomationTreeWalkerMethods.put("condition", Function.getFunction(methodsArray[15], Function.ALT_CONVENTION));
        }
        return new FunctionLibrary(iuiAutomationTreeWalkerMethods, pointerToTreeWalker);
    }
    private HashMap<String, Function> iuiAutomationToggleMethods;
    public FunctionLibrary iuiAutomationLibraryTogglePatternMethods(PointerByReference pointerToPatternClassInstanceByReference) {
        Pointer pointerToPatternClassInstance = pointerToPatternClassInstanceByReference.getValue();
        if(iuiAutomationToggleMethods == null) {
            iuiAutomationToggleMethods  = new HashMap<>();
            Pointer[] methodsArray = PointerHelpers.readMethodsToPointerArray(pointerToPatternClassInstance.getPointer(0), 4);
            iuiAutomationToggleMethods.put("Toggle", Function.getFunction(methodsArray[3], Function.ALT_CONVENTION));
        }
        return new FunctionLibrary(iuiAutomationToggleMethods, pointerToPatternClassInstance);
    }
}
