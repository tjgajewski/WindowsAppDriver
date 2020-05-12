package infrastructure.utils;

import com.sun.jna.Function;
import com.sun.jna.Pointer;

import java.util.HashMap;

public class FunctionLibrary {

    private HashMap<String, Function> methods;
    private Pointer pointerToInstance;

    public FunctionLibrary(HashMap<String, Function> methods, Pointer pointerToInstance){
        this.methods = methods;
        this.pointerToInstance = pointerToInstance;
    }

    public HashMap<String, Function> getMethods(){
        return methods;
    }

    public Pointer getPointerToInstance(){
        return pointerToInstance;
    }
}
