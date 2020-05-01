package infrastructure.utils;

import com.sun.jna.Pointer;

public class PointerHelpers {

    public static Pointer[] readMethodsToPointerArray(Pointer tablePointer, int size){
        Pointer[] table = new Pointer[size];
        tablePointer.read(0, table, 0, table.length);
        return table;
    }
}
