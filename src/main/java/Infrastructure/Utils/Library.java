package Infrastructure.Utils;

import com.sun.jna.Function;
import com.sun.jna.ptr.PointerByReference;

import java.util.HashMap;

public class Library {

    public HashMap<String, Function> getMethods() {
        return methods;
    }

    public PointerByReference getInstancePointer() {
        return instancePointer;
    }

    private HashMap<String, Function> methods;
    private  PointerByReference instancePointer;

    public Library(HashMap<String, Function> methods, PointerByReference instancePointer){
        this.methods = methods;
        this.instancePointer = instancePointer;
    }
}
